/* Filename:    $RCSfile: FormFormView.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2005/10/12 16:52:27 $
 *
 * This library is free software; you can redistribute it and/or
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
package edu.ku.brc.specify.ui.forms.persist;

import java.util.List;
import java.util.Map;
import java.util.Vector;


public class FormFormView extends FormView
{
    protected String        columnDef = "";
    protected String        rowDef    = "";
    protected List<FormRow> rows      = new Vector<FormRow>(); 
    
    protected Map<String, String>  enableRules = null;

    /**
     * @param type the type (could be form or field)
     * @param id the id
     * @param name the name
     * @param className the class name of the data object
     * @param gettableClassName the class name of the gettable
     * @param settableClassName the class name of the settable
     * @param desc description
     * @param isValidated whether to turn on validation
     */
    public FormFormView(final FormView.ViewType type, 
                        final int     id, 
                        final String  name, 
                        final String  className, 
                        final String  gettableClassName, 
                        final String  settableClassName, 
                        final String  desc, 
                        final boolean isValidated)
    {
        super(type, id, name, className, gettableClassName, settableClassName, desc, isValidated);
        
    }
    
    /**
     * Add a row to the form
     * @param row the row to add
     * @return the row that was added
     */
    public FormRow addRow(FormRow row)
    {
        rows.add(row);
        return row;
    }

    /**
     * @return all the rows
     */
    public List<FormRow> getRows()
    {
        return rows;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.forms.persist.FormView#cleanUp()
     */
    public void cleanUp()
    {
        super.cleanUp();
        for (FormRow row : rows)
        {
            row.cleanUp();
        }
        rows.clear();
        enableRules.clear();
    }

    public String getColumnDef()
    {
        return columnDef;
    }

    public void setColumnDef(String columnDef)
    {
        this.columnDef = columnDef;
    }

    public String getRowDef()
    {
        return rowDef;
    }

    public void setRowDef(String rowDef)
    {
        this.rowDef = rowDef;
    }

    public Map<String, String> getEnableRules()
    {
        return enableRules;
    }

    public void setEnableRules(Map<String, String> enableRules)
    {
        this.enableRules = enableRules;
    }

    
}
