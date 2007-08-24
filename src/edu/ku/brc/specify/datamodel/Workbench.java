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
package edu.ku.brc.specify.datamodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Index;

import edu.ku.brc.dbsupport.DBTableIdMgr;

/**
 * Workbench generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "workbench")
@org.hibernate.annotations.Table(appliesTo="workbench", indexes =
    {   
        @Index (name="WorkbenchNameIDX", columnNames={"name"})
    })
public class Workbench extends DataModelObjBase implements java.io.Serializable, Comparable<Workbench>
{

    // Fields    

    protected Integer                   workbenchId;
    protected String                 name;
    protected Integer                dbTableId;
    protected String                 remarks;
    protected Integer                formId;
    protected String                 exportInstitutionName;
    protected Integer                ownerPermissionLevel;
    protected Integer                groupPermissionLevel;
    protected Integer                allPermissionLevel;
    protected WorkbenchTemplate      workbenchTemplate;
    protected Set<WorkbenchRow>      workbenchRows;
    protected SpecifyUser            specifyUser;
    protected UserGroup              group;
    protected String                 srcFilePath;

     // Transient Data
    protected Vector<WorkbenchRow> rows        = new Vector<WorkbenchRow>();
    protected Vector<WorkbenchRow> deletedRows = new Vector<WorkbenchRow>();
    
    protected Hashtable<Short, WorkbenchTemplateMappingItem> wbtmiItems    = new Hashtable<Short, WorkbenchTemplateMappingItem>();

     
    // Constructors

    /** default constructor */
    public Workbench() {
        //
    }
    
    /** constructor with id */
    public Workbench(Integer workbenchId) 
    {
        this.workbenchId = workbenchId;
    }
   
    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        workbenchId           = null;
        name                  = null;
        dbTableId             = null;
        remarks               = null;
        formId                = null;
        exportInstitutionName = null;
        srcFilePath           = null;
        ownerPermissionLevel  = null;
        groupPermissionLevel  = null;
        allPermissionLevel    = null;
        workbenchTemplate     = null;
        workbenchRows         = new HashSet<WorkbenchRow>();
        specifyUser           = null;
        group                 = null;
        
        rows.clear();
        deletedRows.clear();
        wbtmiItems.clear();
    }
    // End Initializer

    
    /**
     * Assumes it is connected to a Session and forces all the data to be loaded. 
     */
    public void forceLoad()
    {
        getWorkbenchTemplate().forceLoad();
        
        for (WorkbenchRow row : getWorkbenchRows())
        {
            row.forceLoad();
        }
    }

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "WorkbenchID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getWorkbenchId() {
        return this.workbenchId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.workbenchId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return Workbench.class;
    }
    
    public void setWorkbenchId(Integer workbenchId) {
        this.workbenchId = workbenchId;
    }

    /**
     *      * Name of workbench
     */
    @Column(name = "Name", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    @Column(name = "TableID", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getDbTableId() {
        return this.dbTableId;
    }
    
    public void setDbTableId(Integer tableId) {
        this.dbTableId = tableId;
    }

    /**
     * 
     */
    @Lob
    @Column(name="Remarks", unique=false, nullable=true, updatable=true, insertable=true)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     */
    @Column(name = "FormId", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getFormId() {
        return this.formId;
    }
    
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    /**
     Name of Institution being exported from
     */
    @Column(name = "ExportInstitutionName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getExportInstitutionName() {
        return this.exportInstitutionName;
    }
    
    public void setExportInstitutionName(String exportInstitutionName) {
        this.exportInstitutionName = exportInstitutionName;
    }
    

    /**
     * Returns the path to the original File.
     * @return the path to the original File.
     */
    @Column(name = "SrcFilePath", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getSrcFilePath()
    {
        return srcFilePath;
    }

    /**
     * Sets the path to the original file.
     * @param srcFilePath the path.
     */
    public void setSrcFilePath(String srcFilePath)
    {
        this.srcFilePath = srcFilePath;
    }
    /**
     * 
     */
    @Column(name = "OwnerPermissionLevel", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getOwnerPermissionLevel() {
        return this.ownerPermissionLevel;
    }
    
    public void setOwnerPermissionLevel(Integer ownerPermissionLevel) {
        this.ownerPermissionLevel = ownerPermissionLevel;
    }

    /**
     * 
     */
    @Column(name = "GroupPermissionLevel", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getGroupPermissionLevel() {
        return this.groupPermissionLevel;
    }
    
    public void setGroupPermissionLevel(Integer groupPermissionLevel) {
        this.groupPermissionLevel = groupPermissionLevel;
    }

    /**
     * 
     */
    @Column(name = "AllPermissionLevel", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getAllPermissionLevel() {
        return this.allPermissionLevel;
    }
    
    public void setAllPermissionLevel(Integer allPermissionLevel) {
        this.allPermissionLevel = allPermissionLevel;
    }
    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkbenchTemplateID", unique = false, nullable = false, insertable = true, updatable = true)
    @Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.LOCK })
    public WorkbenchTemplate getWorkbenchTemplate() {
        return this.workbenchTemplate;
    }
    
    public void setWorkbenchTemplate(WorkbenchTemplate workbenchTemplates) {
        this.workbenchTemplate = workbenchTemplates;
    }

    /**
     * 
     */
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "workbench")
    // @Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.LOCK })
    public Set<WorkbenchRow> getWorkbenchRows() 
    {
        if (rows == null)
        {
            rows = new Vector<WorkbenchRow>();
        }
        return this.workbenchRows;
    }
    
    public void setWorkbenchRows(Set<WorkbenchRow> workbenchDataItems) 
    {
        if (rows == null)
        {
            rows = new Vector<WorkbenchRow>();
        }
        this.workbenchRows = workbenchDataItems;
    }
    
    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SpecifyUserID", unique = false, nullable = false, insertable = true, updatable = true)
    public SpecifyUser getSpecifyUser() {
        return this.specifyUser;
    }
    
    public void setSpecifyUser(SpecifyUser owner) {
        this.specifyUser = owner;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserGroupID", unique = false, nullable = true, insertable = true, updatable = true)
    public UserGroup getGroup() {
        return this.group;
    }
    
    public void setGroup(UserGroup group) {
        this.group = group;
    }
    
    public void addWorkbenchDataItem(WorkbenchRow item)
    {
        workbenchRows.add(item);
        item.setWorkbench(this);
        
        if (rows == null)
        {
            rows = new Vector<WorkbenchRow>();
        }
        rows.add(item);
        Collections.sort(rows);
    }
    
    /**
     * @param item - 
     * void
     */
    public void removeWorkbenchDataItem(final WorkbenchRow item)
    {
        this.workbenchRows.remove(item);
        item.setWorkbench(null);
        
        if (rows != null)
        {
            rows.remove(item);
        } else
        {
            throw new RuntimeException("Why isn't this object in the list?");
        }
        
    }  
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public int getTableId()
    {
        return getClassTableId();
    }
    
    /**
     * @return the Table ID for the class.
     */
    public static int getClassTableId()
    {
        return 79;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    { 
        if (name != null) return name;
        return super.getIdentityTitle();
    }
    
    @Transient
    public Vector<WorkbenchRow> getWorkbenchRowsAsList()
    { 
        if (rows.size() == 0 && workbenchRows.size() > 0)
        {
            rows.addAll(workbenchRows);
            Collections.sort(rows);
        }

        return rows;
    }
    
    /**
     * @return the mapping from column number to the mapping item definition.
     */
    @Transient
    public Hashtable<Short, WorkbenchTemplateMappingItem> getTemplateMappings()
    {
        if (wbtmiItems.size() == 0 || wbtmiItems.size() != workbenchTemplate.getWorkbenchTemplateMappingItems().size())
        {
            wbtmiItems.clear();
            createWBTMIMappings();
        }
        return wbtmiItems;
    }
    
    /**
     * Returns the mapping item for column number.
     * @param column the colum
     * @return the mapping
     */
    public WorkbenchTemplateMappingItem getMappingFromColumn(final short column)
    {
        return getTemplateMappings().get(column); 
    }
    
    /**
     * CReate the mapping from the column number to the definition. 
     */
    protected void createWBTMIMappings()
    {
        if (wbtmiItems.size() == 0)
        {
            for (WorkbenchTemplateMappingItem wbtmi : workbenchTemplate.getWorkbenchTemplateMappingItems())
            {
                wbtmiItems.put(wbtmi.getViewOrder().shortValue(), wbtmi);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Workbench obj)
    {
        return name.compareTo(obj.name);
    }
    
    //----------------------------------------------------
    // Row Helper methods
    //----------------------------------------------------

    /**
     * Return all the rows that are to be deleted.
     * @return all the rows that are to be deleted.
     */
    @Transient
    public Vector<WorkbenchRow> getDeletedRows()
    {
        return deletedRows;
    }
    
    /**
     * Appends a new rows to the workbench.
     * @return the new row
     */
    public WorkbenchRow addRow()
    {
        WorkbenchRow wbRow = new WorkbenchRow(this, (short)rows.size()); // initializes
        rows.add(wbRow);
        workbenchRows.add(wbRow);
        return wbRow;
    }
    
    /**
     * Inserts a new row and returns it.
     * @param rowIndex the index where the row is to be inserted
     * @return the new row
     */
    public WorkbenchRow insertRow(final short rowIndex)
    {
        if (rowIndex < 0)
        {
            throw new RuntimeException("Row Index is less than zero ["+rowIndex+"]");
        }
        
        if (rowIndex >= rows.size())
        {
            return addRow();
        }
        
        WorkbenchRow workbenchRow = new WorkbenchRow(this, rowIndex);
        for (int i=rowIndex;i<rows.size();i++)
        {
            rows.get(i).setRowNumber((short)(i+1));
        }
        rows.insertElementAt(workbenchRow, rowIndex);
        workbenchRows.add(workbenchRow);
        return workbenchRow;
    }
    
    /**
     * Returns a Row.
     * @param rowIndex the row to be returned
     * @return the row to be returned
     */
    public WorkbenchRow getRow(final int rowIndex)
    {
        return rows.get(rowIndex);
    }
    
    /**
     * Remove a Row.
     * @param rowIndex the row to remove
     * @return the rows that was removed.
     */
    public WorkbenchRow deleteRow(final int rowIndex)
    {
        WorkbenchRow wbRow = rows.get(rowIndex);
        rows.remove(rowIndex);
        for (int i=rowIndex+1;i<rows.size();i++)
        {
            rows.get(i).setRowNumber((short)(i-1));
        }
        deletedRows.add(wbRow);
        workbenchRows.remove(wbRow);
        return wbRow;
    }

    //----------------------------------------------------
    // Other Helper methods
    //----------------------------------------------------
    
    @Transient
    public boolean containsGeoRefData()
    {
        // get the template mapping data
        WorkbenchTemplate template = getWorkbenchTemplate();
        Set<WorkbenchTemplateMappingItem> mappingItems = template.getWorkbenchTemplateMappingItems();
        
        // see if lat/lon fields exist
        boolean hasLat1 = false;
        boolean hasLon1 = false;
        int localityTableId = DBTableIdMgr.getInstance().getIdByClassName(Locality.class.getName());
        for( WorkbenchTemplateMappingItem item: mappingItems )
        {
            int dataTableId = item.getSrcTableId();
            if (dataTableId == localityTableId)
            {
                String fieldName = item.getFieldName();
                
                // look for a Latitude1 field
                if (fieldName.equals("latitude1"))
                {
                    hasLat1 = true;
                }
                
                // look for a Integeritude1 field
                if (fieldName.equals("longitude1"))
                {
                    hasLon1 = true;
                }
            }
        }
        
        // returns true if both are present
        return (hasLat1 && hasLon1);
    }
    
    @Transient
    public boolean containsLocalityStringData()
    {
        // get the template mapping data
        WorkbenchTemplate template = getWorkbenchTemplate();
        Set<WorkbenchTemplateMappingItem> mappingItems = template.getWorkbenchTemplateMappingItems();

        int localityTableId = DBTableIdMgr.getInstance().getIdByClassName(Locality.class.getName());
        for( WorkbenchTemplateMappingItem item: mappingItems )
        {
            int dataTableId = item.getSrcTableId();
            if (dataTableId == localityTableId)
            {
                String fieldName = item.getFieldName();
                
                // look for a Latitude1 field
                if (fieldName.equals("localityName"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Transient
    public int getColumnIndex(int dataTableId, String fieldNameOrCaption)
    {
        // get the template mapping data
        WorkbenchTemplate template = getWorkbenchTemplate();
        Set<WorkbenchTemplateMappingItem> mappingItems = template.getWorkbenchTemplateMappingItems();
        
        for( WorkbenchTemplateMappingItem item: mappingItems )
        {
            int itemDataTableId = item.getSrcTableId();
            if (itemDataTableId == dataTableId)
            {
                String fieldName = item.getFieldName();
                String caption = item.getCaption();
                if (fieldName.equalsIgnoreCase(fieldNameOrCaption) || caption.equalsIgnoreCase(fieldNameOrCaption))
                {
                    return item.getViewOrder();
                }
            }
        }
        
        return -1;
    }
    
    public int getColumnIndex(Class<?> dataClass, String fieldNameOrCaption)
    {
        int tableId = DBTableIdMgr.getInstance().getIdByClassName(dataClass.getName());
        return getColumnIndex(tableId,fieldNameOrCaption);
    }

}
