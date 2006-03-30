package edu.ku.brc.specify.datamodel;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;




/**
 * ExternalResource generated by hbm2java
 */
public class ExternalResource  implements java.io.Serializable {

    // Fields    

     protected Integer externalResourceId;
     protected String mimeType;
     protected String fileName;
     protected Calendar fileCreatedDate;
     protected String remarks;
     protected String externalLocation;
     protected Date timestampCreated;
     protected Date timestampModified;
     protected String lastEditedBy;
     protected Set attrs;
     private Agent createdByAgent;
     private Set agents;
     private Set collectionObjects;
     private Set collectinEvents;
     private Set loans;
     private Set localities;
     private Set permits;
     private Set preparations;
     private Set taxonomy;


    // Constructors

    /** default constructor */
    public ExternalResource() {
    }
    
    /** constructor with id */
    public ExternalResource(Integer externalResourceId) {
        this.externalResourceId = externalResourceId;
    }
   
    
    

    // Property accessors

    /**
     * 
     */
    public Integer getExternalResourceId() {
        return this.externalResourceId;
    }
    
    public void setExternalResourceId(Integer externalResourceId) {
        this.externalResourceId = externalResourceId;
    }

    /**
     * 
     */
    public String getMimeType() {
        return this.mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * 
     */
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 
     */
    public Calendar getFileCreatedDate() {
        return this.fileCreatedDate;
    }
    
    public void setFileCreatedDate(Calendar fileCreatedDate) {
        this.fileCreatedDate = fileCreatedDate;
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
    public String getExternalLocation() {
        return this.externalLocation;
    }
    
    public void setExternalLocation(String externalLocation) {
        this.externalLocation = externalLocation;
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
    public Date getTimestampModified() {
        return this.timestampModified;
    }
    
    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    /**
     * 
     */
    public String getLastEditedBy() {
        return this.lastEditedBy;
    }
    
    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    /**
     * 
     */
    public Set getAttrs() {
        return this.attrs;
    }
    
    public void setAttrs(Set attrs) {
        this.attrs = attrs;
    }

    /**
     * 
     */
    public Agent getCreatedByAgent() {
        return this.createdByAgent;
    }
    
    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    /**
     * 
     */
    public Set getAgents() {
        return this.agents;
    }
    
    public void setAgents(Set agents) {
        this.agents = agents;
    }

    /**
     * 
     */
    public Set getCollectionObjects() {
        return this.collectionObjects;
    }
    
    public void setCollectionObjects(Set collectionObjects) {
        this.collectionObjects = collectionObjects;
    }

    /**
     * 
     */
    public Set getCollectinEvents() {
        return this.collectinEvents;
    }
    
    public void setCollectinEvents(Set collectinEvents) {
        this.collectinEvents = collectinEvents;
    }

    /**
     * 
     */
    public Set getLoans() {
        return this.loans;
    }
    
    public void setLoans(Set loans) {
        this.loans = loans;
    }

    /**
     * 
     */
    public Set getLocalities() {
        return this.localities;
    }
    
    public void setLocalities(Set localities) {
        this.localities = localities;
    }

    /**
     * 
     */
    public Set getPermits() {
        return this.permits;
    }
    
    public void setPermits(Set permits) {
        this.permits = permits;
    }

    /**
     * 
     */
    public Set getPreparations() {
        return this.preparations;
    }
    
    public void setPreparations(Set preparations) {
        this.preparations = preparations;
    }

    /**
     * 
     */
    public Set getTaxonomy() {
        return this.taxonomy;
    }
    
    public void setTaxonomy(Set taxonomy) {
        this.taxonomy = taxonomy;
    }




}