/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.ku.brc.specify.tasks.subpane;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.ExpressResultsTableInfo;
import edu.ku.brc.af.core.ExpressSearchResults;
import edu.ku.brc.af.core.ServiceInfo;
import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.dbsupport.RecordSetItemIFace;
import edu.ku.brc.specify.ui.db.ResultSetTableModelDM;
import edu.ku.brc.ui.CloseButton;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.GradiantButton;
import edu.ku.brc.ui.GradiantLabel;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.TriangleButton;
import edu.ku.brc.ui.UIHelper;

/**
 * This is a single set of of results and is derived from a query where all the record numbers where
 * supplied as an "in" clause.
 *
 * 
 * @code_status Beta
 * 
 * @author rods
 *
 */
public abstract class ExpressTableResultsBase extends JPanel implements Comparable<ExpressTableResultsBase>
{
	private static final Logger log = Logger.getLogger(ExpressTableResultsBase.class);

    protected static final Cursor handCursor    = new Cursor(Cursor.HAND_CURSOR);
    protected static final Cursor defCursor     = new Cursor(Cursor.DEFAULT_CURSOR);

    protected ExpressSearchResultsPaneIFace esrPane;
    protected JTable                   table;
    protected JPanel                   tablePane;
    protected TriangleButton           expandBtn;
    protected GradiantButton           showTopNumEntriesBtn;
    protected int                      rowCount       = 0;
    protected boolean                  showingAllRows = false;
    protected boolean                  hasResults     = false;

    protected JPanel                   morePanel      = null;
    protected Color                    bannerColor    = new Color(30, 144, 255);    // XXX PREF
    protected int                      topNumEntries  = 7;
    protected ExpressSearchResults     results;
    protected ExpressResultsTableInfo  tableInfo;

    /**
     * Constructor of a results "table" which is really a panel
     * @param esrPane the parent
     * @param tableInfo the info describing the results
     * @param installServices indicates whether services should be installed
     */
    public ExpressTableResultsBase(final ExpressSearchResultsPaneIFace esrPane,
                                   final ExpressSearchResults          results,
                                   final boolean                       installServices)
    {
        super(new BorderLayout());

        this.esrPane     = esrPane;
        this.results     = results;
        this.tableInfo   = results.getTableInfo();
        this.bannerColor = tableInfo.getColor();

        table = new JTable();
        table.setShowVerticalLines(false);
        table.setRowSelectionAllowed(true);
        setBackground(table.getBackground());

        GradiantLabel vl = new GradiantLabel(tableInfo.getTitle(), SwingConstants.LEFT);
        vl.setForeground(bannerColor);
        vl.setTextColor(Color.WHITE);

        expandBtn = new TriangleButton();
        expandBtn.setToolTipText(getResourceString("CollapseTBL"));
        expandBtn.setForeground(bannerColor);
        expandBtn.setTextColor(Color.WHITE);

        showTopNumEntriesBtn = new GradiantButton(String.format(getResourceString("ShowTopEntries"), new Object[] {topNumEntries}));
        showTopNumEntriesBtn.setForeground(bannerColor);
        showTopNumEntriesBtn.setTextColor(Color.WHITE);
        showTopNumEntriesBtn.setVisible(false);
        showTopNumEntriesBtn.setCursor(handCursor);

        List<ServiceInfo> services = installServices ? ContextMgr.checkForServices(Integer.parseInt(tableInfo.getTableId())) :
                                                       new ArrayList<ServiceInfo>();

        //System.out.println("["+tableInfo.getTableId()+"]["+services.size()+"]");
        StringBuffer colDef = new StringBuffer("p,0px,p:g,0px,p,0px,p,0px,");
        colDef.append(UIHelper.createDuplicateJGoodiesDef("p", "0px", services.size())); // add additional col defs for services

        FormLayout      formLayout = new FormLayout(colDef.toString(), "f:p:g");
        PanelBuilder    builder    = new PanelBuilder(formLayout);
        CellConstraints cc         = new CellConstraints();

        int col = 1;
        builder.add(expandBtn, cc.xy(col,1));
        col += 2;

        builder.add(vl, cc.xy(col,1));
        col += 2;

        builder.add(showTopNumEntriesBtn, cc.xy(col,1));
        col += 2;

        // install the btns on the banner with available services
        for (ServiceInfo serviceInfo : services)
        {
            GradiantButton btn = new GradiantButton(serviceInfo.getIcon(IconManager.IconSize.Std16)); // XXX PREF
            btn.setToolTipText(getResourceString(serviceInfo.getTooltip()));
            btn.setForeground(bannerColor);
            builder.add(btn, cc.xy(col,1));

            btn.addActionListener(new ESTableAction(serviceInfo.getCommandAction(), table, tableInfo));

            col += 2;

        }

        CloseButton closeBtn = new CloseButton();
        closeBtn.setToolTipText(getResourceString("ESCloseTable"));
        closeBtn.setForeground(bannerColor);
        closeBtn.setCloseColor(new Color(255,255,255, 90));
        builder.add(closeBtn, cc.xy(col,1));
        col += 2;

        add(builder.getPanel(), BorderLayout.NORTH);

        tablePane = new JPanel(new BorderLayout());
        tablePane.setLayout(new BorderLayout());
        tablePane.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePane.add(table, BorderLayout.CENTER);

        add(tablePane, BorderLayout.CENTER);

        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                boolean isExpanded = !expandBtn.isDown();

                expandBtn.setDown(isExpanded);
                expandBtn.setToolTipText(isExpanded ? getResourceString("CollapseTBL") : getResourceString("ExpandTBL"));

                tablePane.setVisible(isExpanded);

                if (!showingAllRows && morePanel != null)
                {
                    morePanel.setVisible(isExpanded);
                }
                invalidate();
                doLayout();
                esrPane.revalidateScroll();
            }
        });

        showTopNumEntriesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                morePanel.setVisible(true);
                showTopNumEntriesBtn.setVisible(false);
                showingAllRows = false;
                setDisplayRows(rowCount, topNumEntries);

                // If it is collapsed then expand it
                if (!expandBtn.isDown())
                {
                    tablePane.setVisible(true);
                    expandBtn.setDown(true);
                }

                // Make sure the layout is updated
                invalidate();
                doLayout();
                esrPane.revalidateScroll();
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        removeMe();
                    }
                  });

            }
        });
    }

    /**
     * Returns the ExpressSearchResults object.
     * @return the ExpressSearchResults object.
     */
    public ExpressSearchResults getResults()
    {
        return results;
    }

    /**
     * Returns true if there were results.
     * @return true if there were results.
     */
    public boolean hasResults()
    {
        return hasResults;
    }
    
    /**
     * Cleans up references to other objects.
     */
    public void cleanUp()
    {
        if (results != null)
        {
            results.cleanUp();
        }
        esrPane   = null;
        results   = null;
        tableInfo = null;
    }

    /**
     * Sets all the Columns to be center justified this COULD be set up in the table info.
     *
     */
    protected void configColumns()
    {
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel tableColModel = table.getColumnModel();
        for (int i=0;i<tableColModel.getColumnCount();i++)
        {
            tableColModel.getColumn(i).setCellRenderer(renderer);
        }
    }

    /**
     * Builds the "more" panel.
     *
     */
    protected void buildMorePanel()
    {
        FormLayout      formLayout = new FormLayout("15px,0px,p", "p");
        PanelBuilder    builder    = new PanelBuilder(formLayout);
        CellConstraints cc         = new CellConstraints();

        JButton btn = new JButton(String.format(getResourceString("MoreEntries"), new Object[] {(rowCount - topNumEntries)}));//(rowCount - topNumEntries)+" more...");
        btn.setCursor(handCursor);

        btn.setBorderPainted(false);
        builder.add(new JLabel(" "), cc.xy(1,1));
        builder.add(btn, cc.xy(3,1));

        morePanel = builder.getPanel();
        Color bgColor = table.getBackground();
        bgColor = new Color(Math.max(bgColor.getRed()-10, 0), Math.max(bgColor.getGreen()-10, 0), Math.max(bgColor.getBlue()-10, 0));

        Color fgColor = new Color(Math.min(bannerColor.getRed()+10, 255), Math.min(bannerColor.getGreen()+10, 255), Math.min(bannerColor.getBlue()+10, 255));
        morePanel.setBackground(bgColor);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        add(builder.getPanel(), BorderLayout.SOUTH);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                morePanel.setVisible(false);
                showTopNumEntriesBtn.setVisible(true);
                showingAllRows = true;
                setDisplayRows(rowCount, Integer.MAX_VALUE);
                esrPane.revalidateScroll();
            }
        });

    }

    /**
     * Asks parent to remove this table.
     */
    protected void removeMe()
    {
        esrPane.removeTable(this);
    }

    /**
     * Creates an array of indexes.
     * @param rows the number of rows to be displayed
     * @return an array of indexes
     */
    protected int[] createIndexesArray(final int rows)
    {
        int[] indexes = new int[rows];
        for (int i=0;i<rows;i++)
        {
            indexes[i] = i;
        }
        return indexes;
    }

    /**
     * Display the 'n' number of rows up to topNumEntries.
     *
     * @param numRows the desired number of rows
     * @param maxNum the maximum number of rows
     */
    protected void setDisplayRows(final int numRows, final int maxNum)
    {
        int rows = Math.min(numRows, maxNum);
        ResultSetTableModelDM rsm = (ResultSetTableModelDM)table.getModel();
        rsm.initializeDisplayIndexes();
        rsm.addDisplayIndexes(createIndexesArray(rows));
    }

    /**
     * Returns the JTable that holds the results.
     * @return the JTable that holds the results
     */
    public JTable getTable()
    {
        return table;
    }

    /**
     * Return a recordset for the selected items.
     * @param returnAll indicates whether all the records should be returned if nothing was selected
     * @return a recordset for the selected items
     */
    public RecordSetIFace getRecordSet(final boolean returnAll)
    {
        log.debug("Indexes: "+table.getSelectedRows().length+" Index["+tableInfo.getTableId()+"]");
        for (int v : table.getSelectedRows())
        {
        	log.debug("["+v+"]");
        }

        boolean doReturnAll = returnAll;
        int[] rows = table.getSelectedRows();
        if (returnAll || rows.length == 0)
        {
            table.selectAll();
            rows = table.getSelectedRows();
            table.clearSelection();
            doReturnAll = true;
        }
        RecordSetIFace rs = getRecordSet(rows, tableInfo.getRecordSetColumnInx(), doReturnAll);

        if (doReturnAll)
        {
            table.clearSelection();
        }

        // Now we use the actual Table Id from the table info
        // to set it correctly in the RecordSet
        rs.setDbTableId(Integer.parseInt(tableInfo.getTableId()));
        
        return rs;
    }
    
    /**
     * Returns a list of recordIds.
     * @param returnAll indicates whether all the records should be returned if nothing was selected
     * @return a list of recordIds
     */
    public List<Integer> getListOfIds(final boolean returnAll)
    {
        List<Integer> list = new ArrayList<Integer>();
        RecordSetIFace rs = getRecordSet(returnAll);
        if (rs != null)
        {
            for (RecordSetItemIFace rsi : rs.getItems())
            {
                list.add(rsi.getRecordId());
            }
        }
        return list;
    }

    /**
     * Returns a RecordSet object from the table.
     * @param rows selected row indexes
     * @param column the column to get the indexes from
     * @param returnAll indicates whether all the records should be returned if nothing was selected
     * @return Returns a RecordSet object from the table
     */
    public abstract RecordSetIFace getRecordSet(final int[] rows, final int column, final boolean returnAll);

    
    /**
     * Comparable interface method.
     * @param obj the objec to compare to
     * @return 0 if equals
     */
    public int compareTo(ExpressTableResultsBase obj)
    {
        return results.getTableInfo().getTitle().compareTo(obj.getResults().getTableInfo().getTitle());
    }
    
    //--------------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------------

    /**
     * 
     */
    class ESTableAction implements ActionListener
    {
        protected CommandAction           cmd;
        protected RecordSetIFace          recordSet;
        protected JTable                  estTable;
        protected ExpressResultsTableInfo estTableInfo;

        public ESTableAction(final CommandAction cmd,
                             final JTable estTable,
                             final ExpressResultsTableInfo estTableInfo)
        {
            this.cmd          = cmd;
            this.estTable     = estTable;
            this.estTableInfo = estTableInfo;
        }

        public void actionPerformed(ActionEvent e)
        {
            cmd.setData(getRecordSet(false));
            CommandDispatcher.dispatch(cmd);

            // always reset the consumed flag and set the data to null
            // so the command can be used again
            cmd.setConsumed(false);
            cmd.setData(null);
        }
    }

}
