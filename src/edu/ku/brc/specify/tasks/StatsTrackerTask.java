package edu.ku.brc.specify.tasks;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.af.ui.forms.FormDataObjIFace;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.util.Pair;

/**
 * This class sends usage stats.
 * 
 * @author rods
 * 
 * @code_status Complete
 */
public class StatsTrackerTask extends edu.ku.brc.af.tasks.StatsTrackerTask
{
    private static final Logger log = Logger.getLogger(StatsTrackerTask.class);
    
    private final static String DATABASE     = "Database";
    private final static String resourceName = "CollStats";

    private boolean                      hasChanged          = false;
    private JProgressBar                 progress;
    private Hashtable<Class<?>, Boolean> tablesHash          = new Hashtable<Class<?>, Boolean>();
    private Vector<Pair<String, String>> queries             = new Vector<Pair<String,String>>();
    private Collection                   collection          = null;  
    
    /**
     * Constructor.
     */
    public StatsTrackerTask()
    {
        super();
        
        CommandDispatcher.register(DATABASE, this);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.StatsTrackerTask#initialize()
     */
    @Override
    public void initialize()
    {
        super.initialize();
        
        Element rootElement = AppContextMgr.getInstance().getResourceAsDOM(resourceName);
        if (rootElement != null)
        {
            List<?> rows = rootElement.selectNodes("/statistics/tables/table"); //$NON-NLS-1$
            for (Object obj : rows)
            {
                Element statElement     = (Element)obj;
                String  tableClassName  = XMLHelper.getAttr(statElement, "class", null);
                if (StringUtils.isNotEmpty(tableClassName))
                {
                    Class<?> cls = null;
                    try
                    {
                        cls = Class.forName(tableClassName);
                        tablesHash.put(cls, true);
                        
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        } else
        {
            log.error("Couldn't find resource ["+resourceName+"]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.StatsTrackerTask#createClosingFrame()
     */
    @Override
    protected void showClosingFrame()
    {
        ImageIcon img = IconManager.getIcon("SpecifySplash");
        
        CellConstraints    cc = new CellConstraints();
        PanelBuilder pb = new PanelBuilder(new FormLayout("f:p:g,150px", "f:p:g,2px,p"));
        pb.setDefaultDialogBorder();
        
        JLabel lbl = new JLabel(img);
        pb.add(lbl, cc.xyw(1, 1, 2));
        lbl = UIHelper.createI18NLabel("SPECIFY_SHUTDOWN", SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(18.0f));
        pb.add(lbl, cc.xy(1, 3));
        
        progress = new JProgressBar(0, 100);
        pb.add(progress, cc.xy(2, 3));
        
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setContentPane(pb.getPanel());
        frame.pack();
        UIHelper.centerAndShow(frame);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.StatsTrackerTask#completed()
     */
    @Override
    protected void completed()
    {
        collection = null;
        queries.clear();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.StatsTrackerTask#starting()
     */
    @Override
    protected boolean starting()
    {
        if (collection == null)
        {
            collection = AppContextMgr.getInstance().getClassObject(Collection.class);
            queries.clear();
            
            // Need to do this now before all the Cached Objects change
            // that the QueryAdjusterForDomain uses.
            Element rootElement = AppContextMgr.getInstance().getResourceAsDOM(resourceName);
            if (rootElement != null)
            {
                List<?> rows = rootElement.selectNodes("/statistics/stats/stat"); //$NON-NLS-1$
                for (Object obj : rows)
                {
                    Element statElement = (Element)obj;
                    String  statsName   = XMLHelper.getAttr(statElement, "name", null);
                    if (StringUtils.isNotEmpty(statsName))
                    {
                        String sqlStr = QueryAdjusterForDomain.getInstance().adjustSQL(statElement.getText());
                        queries.add(new Pair<String, String>(statsName, sqlStr));
                    }
                }
            }
            return true;
        }
        
        return false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.StatsTrackerTask#getPCLForWorker()
     */
    @Override
    protected PropertyChangeListener getPCLForWorker()
    {
        return new PropertyChangeListener() {
            public  void propertyChange(final PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) 
                {
                    if (progress != null) progress.setValue((Integer)evt.getNewValue());
                }
            }
        };
    }
    
    /**
     * Collection Statistics about the Collection (synchronously).
     */
    @Override
    protected Vector<NameValuePair> collectExtraStats()
    {
        Vector<NameValuePair> stats = new Vector<NameValuePair>();
        if (hasChanged)
        {
            if (progress != null) progress.setIndeterminate(true);
            if (queries.size() > 0)
            {
                int    count = 0;
                double total = queries.size();
                for (Pair<String, String> p : queries)
                {
                    String  statsName   = p.first;
                    if (StringUtils.isNotEmpty(statsName))
                    {
                        count++;
                        addStat(statsName, stats, p.second);
                        if (progress != null) progress.setIndeterminate(false);
                        worker.setProgressValue((int)(100.0 * (count / total)));
                    }
                }
                worker.setProgressValue(100);
                
            } else
            {
                log.error("Couldn't find resource ["+resourceName+"]"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        
        // Gather Collection Counts;
        stats.add(new NameValuePair("Collection_number",  fixParam(collection.getRegNumber()))); //$NON-NLS-1$
        stats.add(new NameValuePair("Collection_website", fixParam(collection.getWebSiteURI()))); //$NON-NLS-1$
        stats.add(new NameValuePair("Collection_portal",  fixParam(collection.getWebPortalURI()))); //$NON-NLS-1$

        return stats;
    }
    
    /**
     * @param cmdActionArg
     */
    private void checkTableType(final CommandAction cmdActionArg)
    {
        if (cmdActionArg.getData() instanceof FormDataObjIFace)
        {
            FormDataObjIFace data = (FormDataObjIFace)cmdActionArg.getData();
            if (tablesHash != null && tablesHash.get(data.getClass()) != null)
            {
                hasChanged = true;
            }
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#doCommand(edu.ku.brc.ui.CommandAction)
     */
    @Override
    public void doCommand(final CommandAction cmdActionArg)
    {
        super.doCommand(cmdActionArg);
        
        if (cmdActionArg.isType(DATABASE))
        {
            checkTableType(cmdActionArg);
        }
    }
}
