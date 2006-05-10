package edu.ku.brc.specify.datamodel;

import java.util.Date;
import java.util.Set;




/**
 * Workbench generated by hbm2java
 */
public class Workbench  implements java.io.Serializable {

    // Fields    

     private Integer workbenchID;
     private String name;
     private Integer tableId;
     protected String remarks;
     protected Integer formid;
     protected String exportinstitutionname;
     private Date timestampModified;
     private Date timestampCreated;
     protected WorkbenchTemplate workbenchTemplates;
     protected Set workbenchItems;


    // Constructors

    /** default constructor */
    public Workbench() {
    }
    
    /** constructor with id */
    public Workbench(Integer workbenchID) {
        this.workbenchID = workbenchID;
    }
   
    
    

    // Property accessors

    /**
     * 
     */
    public Integer getWorkbenchID() {
        return this.workbenchID;
    }
    
    public void setWorkbenchID(Integer workbenchID) {
        this.workbenchID = workbenchID;
    }

    /**
     *      * Name of workbench
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public Integer getTableId() {
        return this.tableId;
    }
    
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    /**
     * 
     */
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     */
    public Integer getFormid() {
        return this.formid;
    }
    
    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    /**
     *      * Name of Institution being exported from
     */
    public String getExportinstitutionname() {
        return this.exportinstitutionname;
    }
    
    public void setExportinstitutionname(String exportinstitutionname) {
        this.exportinstitutionname = exportinstitutionname;
    }

    /**
     * 
     */
    public Date getTimestampModified() {
        return this.timestampModified;
    }
    
    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    /**
     * 
     */
    public Date getTimestampCreated() {
        return this.timestampCreated;
    }
    
    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    /**
     * 
     */
    public WorkbenchTemplate getWorkbenchTemplates() {
        return this.workbenchTemplates;
    }
    
    public void setWorkbenchTemplates(WorkbenchTemplate workbenchTemplates) {
        this.workbenchTemplates = workbenchTemplates;
    }

    /**
     * 
     */
    public Set getWorkbenchItems() {
        return this.workbenchItems;
    }
    
    public void setWorkbenchItems(Set workbenchItems) {
        this.workbenchItems = workbenchItems;
    }




}