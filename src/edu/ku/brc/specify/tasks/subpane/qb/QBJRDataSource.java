/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.qb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.log4j.Logger;

import edu.ku.brc.dbsupport.CustomQueryIFace;
import edu.ku.brc.dbsupport.CustomQueryListener;
import edu.ku.brc.dbsupport.JPAQuery;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.util.Pair;

/**
 * @author timbo
 *
 * @code_status Alpha
 * 
 * Provides data by executing an hql statement for a QueryBuilder query.
 *
 */
public class QBJRDataSource extends QBJRDataSourceBase implements CustomQueryListener
{
    protected static final Logger log = Logger.getLogger(QBJRDataSource.class);
    protected boolean firstRow = true;
    
    /**
     * hql that produces the data.
     */
    protected final String hql;
    /**
     * name-value list of parameters for the hql query.
     */
    protected final List<Pair<String, Object>> params;
    
    /**
     * sort to be applied AFTER data is retrieved.
     */
    protected final List<SortElement> sort;
    
    /**
     * true if data has been pre-processed (i.e. sorted).
     */
    protected boolean processed = false;
    
    protected AtomicBoolean processing = new AtomicBoolean(true);
    
    /**
     * stores processed data;
     */
    protected Vector<Vector<Object>> cache = null;
    
    /**
     * stores size of the data set.
     */
    protected final AtomicInteger resultSetSize = new AtomicInteger();
    /**
     * column values for the current record.
     */
    protected Object rowVals = null;
    /**
     * iterator of records in the data source.
     */
    protected final AtomicReference<Iterator<?>> rows = new AtomicReference<Iterator<?>>(null);
    
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    @Override
    public Object getFieldValue(JRField arg0) throws JRException
    {
        //XXX - what if user-defined 'resultsetsize' field exists???
    	if (arg0.getName().equalsIgnoreCase("resultsetsize"))
        {
        	return String.valueOf(resultSetSize);  //currently returned as a string for convenience.
        }
    	
    	boolean logIt = rows.get() == null || processing.get();
        if (logIt)
        {
            log.debug(this + " waiting for rows...");
        }
        //XXX Bad Code Alert!
        while (processing.get() || rows.get() == null) { 
            /*wait till done executing the query. (forever and ever??)
             * Do we know that exectionDone and executionError will be called on different threads?
             * */
        }
        if (logIt)
        {
            log.debug("... " + this + " got rows");
        }
        int fldIdx = getFldIdx(arg0.getName());
        return getFieldValue(fldIdx, arg0.getName(), arg0.getClass());
    }
    
    @SuppressWarnings("unchecked")
    protected Object getFieldValue(final int fldIdx, final String fldName, final Class<?> fldClass)
    {
        if (fldIdx < 0)
            return null;
        int processIdx = recordIdsIncluded ? fldIdx-1 : fldIdx;
        if (processIdx == -1)
        {
           if (fldClass.equals(String.class))
           {
               return String.format(UIRegistry.getResourceString("QBJRDS_UNKNOWN_FIELD"), fldName);
           }
           log.error("field not found: " + fldName);
           return null;
        }
        
        if (!processed)
        {
            return processValue(processIdx, columnInfo.get(processIdx).processValue(((Object[] )rowVals)[fldIdx]));
        }
        //else processing already done
        return ((Vector<Object> )rowVals).get(processIdx);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.qb.QBJRDataSourceBase#getNext()
     */
    @Override
    public boolean getNext() 
    {
        boolean logIt = rows.get() == null || processing.get();
        if (logIt)
        {
            log.debug(this + " waiting for rows...");
        }
        //XXX Bad Code Alert!
        while (rows.get() == null || processing.get()) { 
            /*wait till done executing the query. (forever and ever??)
             * Do we know that exectionDone and executionError will be called on different threads?
             * */
        }
        if (logIt)
        {
            log.debug("... " + this + " got rows");
        }
        
        return doGetNext();
    }

    protected boolean doGetNext()
    {
        if (rows.get().hasNext())
        {
            if (!firstRow)
            {
                rows.get().remove();
            }
            else
            {
                firstRow = false;
            }
            
            Object nextRow = rows.get().next();
            if (Object[].class.isAssignableFrom(nextRow.getClass()))
            {
                rowVals = nextRow;
            }
            else if (Vector.class.isAssignableFrom(nextRow.getClass()))
            {
                rowVals = nextRow;
            }
            else
            {
                //if only one column...
                rowVals = new Object[1];
                ((Object[] )rowVals)[0] = nextRow;
            }
            return true;
        }
        rowVals = null;
        return false;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.qb.QBJRDataSourceBase#getRepeaterRowVals()
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Object[] getRepeaterRowVals()
    {
        if (Vector.class.isAssignableFrom(rowVals.getClass()))
        {
            Object[] result = new Object[((Vector<Object>) rowVals).size()];
            for (int r = 0; r < ((Vector<Object> )rowVals).size(); r++)
            {
                result[r] = ((Vector<Object> )rowVals).get(r);
            }
            return result;
        }
        return (Object[] )rowVals;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.CustomQueryListener#exectionDone(edu.ku.brc.dbsupport.CustomQueryIFace)
     */
    //@Override
    public void exectionDone(CustomQueryIFace customQuery)
    {
        resultSetSize.set(((JPAQuery)customQuery).getDataObjects().size()); 
        rows.set(((JPAQuery)customQuery).getDataObjects().iterator());
        //cache rows and sort
        if (sort != null && sort.size() > 0)
        {
            cache = new Vector<Vector<Object>>(resultSetSize.get());
            while (doGetNext())
            {
                Vector<Object> row = new Vector<Object>(((Object[] )rowVals).length);
                for (int fldIdx = recordIdsIncluded ? 1 : 0; fldIdx < ((Object[] )rowVals).length; fldIdx++)
                {
                    row.add(getFieldValue(fldIdx, "nyx", String.class));
                }
                cache.add(row);
            }
            Collections.sort(cache, new ResultRowComparator(sort));
            processed = true;
            rows.set(cache.iterator());
        }
        processing.set(false);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.CustomQueryListener#executionError(edu.ku.brc.dbsupport.CustomQueryIFace)
     */
    @Override
    public void executionError(CustomQueryIFace customQuery)
    {
        rows.set(new ArrayList<Object>().iterator());
    }

    /**
     * @param hql
     * @param params
     * @param columnInfo
     * @param recordIdsIncluded
     */
    public QBJRDataSource(final String hql, final List<Pair<String, Object>> params, final List<SortElement> sort, 
                          final List<ERTICaptionInfoQB> columnInfo,
                          final boolean recordIdsIncluded)
    {
        super(columnInfo, recordIdsIncluded, null);
        this.hql = hql;
        this.params = params;
        this.sort = sort;
        startDataAcquisition();
    }
    
    /**
     * @param hql
     * @param params
     * @param columnInfo
     * @param recordIdsIncluded
     * @param repeatCount - number of repeats for each record
     */
    public QBJRDataSource(final String hql, final List<Pair<String, Object>> params, final List<SortElement> sort,
                          final List<ERTICaptionInfoQB> columnInfo,
                          final boolean recordIdsIncluded, final Object repeats)
    {
        super(columnInfo, recordIdsIncluded, repeats);
        this.hql = hql;
        this.params = params;
        this.sort = sort;
        startDataAcquisition();
    }    
    
    /**
     * 
     */
    protected void startDataAcquisition()
    {
        JPAQuery q = new JPAQuery(hql, this);
        q.setParams(params);
        q.start();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.qb.QBJRDataSourceBase#getRecordId()
     */
    @Override
    public Object getRecordId()
    {
        //XXX what if processed???? does this EVER get called??
        if (!recordIdsIncluded || processed)
        {
            return super.getRecordId();
        }
        return ((Object[] )rowVals)[0];
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.qb.QBJRDataSourceBase#getFldIdx(java.lang.String)
     */
    @Override
    protected int getFldIdx(String fldName)
    {
        int result = super.getFldIdx(fldName);
        return recordIdsIncluded ? result + 1 : result;
    }

    
}
