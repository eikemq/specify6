package edu.ku.brc.specify.datamodel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;




/**

 */
public class CollectionObjDef  implements java.io.Serializable 
{

    protected static CollectionObjDef currentCollectionObjDef = null;
    
    // Fields

     protected Long collectionObjDefId;
     protected String name;
     protected String discipline;
     protected DataType dataType;
     protected Date timestampModified;
     protected Date timestampCreated;
     protected Set<CatalogSeries> catalogSeries;
     protected SpecifyUser specifyUser;
     protected Set<AttributeDef> attributeDefs;
     protected GeographyTreeDef geographyTreeDef;
     protected GeologicTimePeriodTreeDef geologicTimePeriodTreeDef;
     protected LocationTreeDef locationTreeDef;
     protected TaxonTreeDef taxonTreeDef;
     protected Set<Locality> localities;
     protected Set<AppResourceDefault> appResourceDefaults;

    // Constructors

    /** default constructor */
    public CollectionObjDef() {
    }

    /** constructor with id */
    public CollectionObjDef(Long collectionObjDefId) {
        this.collectionObjDefId = collectionObjDefId;
    }

    public static CollectionObjDef getCurrentCollectionObjDef()
    {
        return currentCollectionObjDef;
    }

    public static void setCurrentCollectionObjDef(CollectionObjDef currentCollectionObjDef)
    {
        CollectionObjDef.currentCollectionObjDef = currentCollectionObjDef;
    }

    // Initializer
    public void initialize()
    {
        collectionObjDefId = null;
        name = null;
        discipline = null;
        dataType = null;
        timestampModified = null;
        timestampCreated = new Date();
        catalogSeries = new HashSet<CatalogSeries>();
        specifyUser = null;
        attributeDefs = new HashSet<AttributeDef>();
        geographyTreeDef = null;
        geologicTimePeriodTreeDef = null;
        locationTreeDef = null;
        taxonTreeDef = null;
        localities = new HashSet<Locality>();
        appResourceDefaults = new HashSet<AppResourceDefault>();
    }
    // End Initializer

    // Property accessors

    /**
     *
     */
    public Long getCollectionObjDefId() {
        return this.collectionObjDefId;
    }

    public void setCollectionObjDefId(Long collectionObjDefId) {
        this.collectionObjDefId = collectionObjDefId;
    }

    /**
     *
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
    public String getDiscipline()
    {
        return discipline;
    }

    public void setDiscipline(String discipline)
    {
        this.discipline = discipline;
    }

    /**
     *
     */
    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
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
    public Set<CatalogSeries> getCatalogSeries() {
        return this.catalogSeries;
    }

    public void setCatalogSeries(Set<CatalogSeries> catalogSeries) {
        this.catalogSeries = catalogSeries;
    }

    /**
     *
     */
    public SpecifyUser getSpecifyUser() {
        return this.specifyUser;
    }

    public void setSpecifyUser(SpecifyUser specifyUser) {
        this.specifyUser = specifyUser;
    }

    /**
     *
     */
    public Set<AttributeDef> getAttributeDefs() {
        return this.attributeDefs;
    }

    public void setAttributeDefs(Set<AttributeDef> attributeDefs) {
        this.attributeDefs = attributeDefs;
    }

    /**
     *
     */
    public GeographyTreeDef getGeographyTreeDef() {
        return this.geographyTreeDef;
    }

    public void setGeographyTreeDef(GeographyTreeDef geographyTreeDef) {
        this.geographyTreeDef = geographyTreeDef;
    }

    /**
     *
     */
    public GeologicTimePeriodTreeDef getGeologicTimePeriodTreeDef() {
        return this.geologicTimePeriodTreeDef;
    }

    public void setGeologicTimePeriodTreeDef(GeologicTimePeriodTreeDef geologicTimePeriodTreeDef) {
        this.geologicTimePeriodTreeDef = geologicTimePeriodTreeDef;
    }

    /**
     *
     */
    public LocationTreeDef getLocationTreeDef() {
        return this.locationTreeDef;
    }

    public void setLocationTreeDef(LocationTreeDef locationTreeDef) {
        this.locationTreeDef = locationTreeDef;
    }

    /**
     *      * @hibernate.one-to-one
     */
    public TaxonTreeDef getTaxonTreeDef() {
        return this.taxonTreeDef;
    }

    public void setTaxonTreeDef(TaxonTreeDef taxonTreeDef) {
        this.taxonTreeDef = taxonTreeDef;
    }

    /**
     *
     */
    public Set<Locality> getLocalities() {
        return this.localities;
    }

    public void setLocalities(Set<Locality> localities) {
        this.localities = localities;
    } 

    public Set<AppResourceDefault> getAppResourceDefaults()
    {
        return appResourceDefaults;
    }

    public void setAppResourceDefaults(Set<AppResourceDefault> appResourceDefaults)
    {
        this.appResourceDefaults = appResourceDefaults;
    }

/**
	 * toString
	 * @return String
	 */
  public String toString() {
	  StringBuffer buffer = new StringBuffer(128);

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("name").append("='").append(getName()).append("' ");
      buffer.append("]");

      return buffer.toString();
	}




    // Add Methods

    public void addCatalogSeries(final CatalogSeries catalogSeries)
    {
        this.catalogSeries.add(catalogSeries);
        catalogSeries.getCollectionObjDefItems().add(this);
    }

    public void addAttributeDefs(final AttributeDef attributeDef)
    {
        this.attributeDefs.add(attributeDef);
        attributeDef.setCollectionObjDef(this);
    }

    public void addLocalities(final Locality localities)
    {
        this.localities.add(localities);
        localities.getCollectionObjDefs().add(this);
    }

    // Done Add Methods

    // Delete Methods

    public void removeCatalogSeries(final CatalogSeries catalogSeries)
    {
        this.catalogSeries.remove(catalogSeries);
        catalogSeries.getCollectionObjDefItems().remove(this);
    }

    public void removeAttributeDefs(final AttributeDef attributeDef)
    {
        this.attributeDefs.remove(attributeDef);
        attributeDef.setCollectionObjDef(null);
    }

    public void removeLocalities(final Locality localities)
    {
        this.localities.remove(localities);
        localities.getCollectionObjDefs().remove(this);
       ;
    }

    // Delete Add Methods
}
