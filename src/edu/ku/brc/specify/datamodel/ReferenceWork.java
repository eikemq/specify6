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

import java.util.HashSet;
import java.util.Set;

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
import org.hibernate.annotations.CascadeType;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "referencework")
public class ReferenceWork extends DataModelObjBase implements java.io.Serializable {

    // Fields    

     protected Integer referenceWorkId;
     protected Integer containingReferenceWorkId;
     protected Byte referenceWorkType;
     protected String title;
     protected String publisher;
     protected String placeOfPublication;
     protected String workDate;
     protected String volume;
     protected String pages;
     protected String url;
     protected String libraryNumber;
     protected String remarks;
     protected String text1;
     protected String text2;
     protected Float number1;
     protected Float number2;
     protected Boolean isPublished;
     protected Boolean yesNo1;
     protected Boolean yesNo2;
     protected String  guid;
     protected Set<LocalityCitation> localityCitations;
     protected Set<CollectionObjectCitation> collectionObjectCitations;
     protected Set<TaxonCitation> taxonCitations;
     protected Set<DeterminationCitation> determinationCitations;
     protected Journal journal;
     protected Set<Author> authors;


    // Constructors

    /** default constructor */
    public ReferenceWork() {
        //
    }
    
    /** constructor with id */
    public ReferenceWork(Integer referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }
   
    
    

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        referenceWorkId = null;
        containingReferenceWorkId = null;
        referenceWorkType = null;
        title = null;
        publisher = null;
        placeOfPublication = null;
        workDate = null;
        volume = null;
        pages = null;
        url = null;
        libraryNumber = null;
        remarks = null;
        text1 = null;
        text2 = null;
        number1 = null;
        number2 = null;
        isPublished = null;
        yesNo1 = null;
        yesNo2 = null;
        guid   = null;
        localityCitations = new HashSet<LocalityCitation>();
        collectionObjectCitations = new HashSet<CollectionObjectCitation>();
        taxonCitations = new HashSet<TaxonCitation>();
        determinationCitations = new HashSet<DeterminationCitation>();
        journal = null;
        authors = new HashSet<Author>();
    }
    // End Initializer

    // Property accessors

    /**
     *      * PrimaryKey
     */
    @Id
    @GeneratedValue
    @Column(name = "ReferenceWorkID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getReferenceWorkId() {
        return this.referenceWorkId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.referenceWorkId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return ReferenceWork.class;
    }
    
    public void setReferenceWorkId(Integer referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }

    /**
     *      * Link to Reference containing (if Section)
     */
    @Column(name = "ContainingReferenceWorkID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getContainingReferenceWorkId() {
        return this.containingReferenceWorkId;
    }
    
    public void setContainingReferenceWorkId(Integer containingReferenceWorkId) {
        this.containingReferenceWorkId = containingReferenceWorkId;
    }

    /**
     * 
     */
    @Column(name = "ReferenceWorkType", unique = false, nullable = false, insertable = true, updatable = true, length = 3)
    public Byte getReferenceWorkType() {
        return this.referenceWorkType;
    }
    
    public void setReferenceWorkType(Byte referenceWorkType) {
        this.referenceWorkType = referenceWorkType;
    }

    /**
     *      * Title of reference
     */
    @Column(name = "Title", unique = false, nullable = true, insertable = true, updatable = true)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     */
    @Column(name = "Publisher", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getPublisher() {
        return this.publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 
     */
    @Column(name = "PlaceOfPublication", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getPlaceOfPublication() {
        return this.placeOfPublication;
    }
    
    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    /**
     * 
     */
    @Column(name = "WorkDate", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
    public String getWorkDate() {
        return this.workDate;
    }
    
    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    /**
     *      * Volume/Issue for Journal articles
     */
    @Column(name = "Volume", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
    public String getVolume() {
        return this.volume;
    }
    
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     *      * Number of pages or Page range for Journal articles
     */
    @Column(name = "Pages", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getPages() {
        return this.pages;
    }
    
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * 
     */
    @Column(name = "URL", length=1024, unique = false, nullable = true, insertable = true, updatable = true)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     */
    @Column(name = "LibraryNumber", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getLibraryNumber() {
        return this.libraryNumber;
    }
    
    public void setLibraryNumber(String libraryNumber) {
        this.libraryNumber = libraryNumber;
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
    @Column(name = "GUID", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     *      * User definable
     */
    @Column(name = "Text1", length=300, unique = false, nullable = true, insertable = true, updatable = true)
    public String getText1() {
        return this.text1;
    }
    
    public void setText1(String text1) {
        this.text1 = text1;
    }

    /**
     *      * User definable
     */
    @Column(name = "Text2", length=300, unique = false, nullable = true, insertable = true, updatable = true)
    public String getText2() {
        return this.text2;
    }
    
    public void setText2(String text2) {
        this.text2 = text2;
    }

    /**
     *      * User definable
     */
    @Column(name = "Number1", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
    public Float getNumber1() {
        return this.number1;
    }
    
    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    /**
     *      * User definable
     */
    @Column(name = "Number2", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
    public Float getNumber2() {
        return this.number2;
    }
    
    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    /**
     * 
     */
    @Column(name="IsPublished", unique=false, nullable=true, insertable=true, updatable=true)
    public Boolean getIsPublished() {
        return this.isPublished;
    }
    
    public void setIsPublished(Boolean published) {
        this.isPublished = published;
    }

    /**
     *      * User definable
     */
    @Column(name="YesNo1",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getYesNo1() {
        return this.yesNo1;
    }
    
    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    /**
     *      * User definable
     */
    @Column(name="YesNo2",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getYesNo2() {
        return this.yesNo2;
    }
    
    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "referenceWork")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<LocalityCitation> getLocalityCitations() {
        return this.localityCitations;
    }
    
    public void setLocalityCitations(Set<LocalityCitation> localityCitations) {
        this.localityCitations = localityCitations;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "referenceWork")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<CollectionObjectCitation> getCollectionObjectCitations() {
        return this.collectionObjectCitations;
    }
    
    public void setCollectionObjectCitations(Set<CollectionObjectCitation> collectionObjectCitations) {
        this.collectionObjectCitations = collectionObjectCitations;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "referenceWork")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<TaxonCitation> getTaxonCitations() {
        return this.taxonCitations;
    }
    
    public void setTaxonCitations(Set<TaxonCitation> taxonCitations) {
        this.taxonCitations = taxonCitations;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "referenceWork")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<DeterminationCitation> getDeterminationCitations() {
        return this.determinationCitations;
    }
    
    public void setDeterminationCitations(Set<DeterminationCitation> determinationCitations) {
        this.determinationCitations = determinationCitations;
    }

    /**
     *      * Link to Journal containing the reference (if applicable)
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "JournalID", unique = false, nullable = true, insertable = true, updatable = true)
    public Journal getJournal() {
        return this.journal;
    }
    
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "referenceWork")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<Author> getAuthors() {
        return this.authors;
    }
    
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }





    // Add Methods

    public void addLocalityCitations(final LocalityCitation localityCitation)
    {
        this.localityCitations.add(localityCitation);
        localityCitation.setReferenceWork(this);
    }

    public void addCollectionObjectCitations(final CollectionObjectCitation collectionObjectCitation)
    {
        this.collectionObjectCitations.add(collectionObjectCitation);
        collectionObjectCitation.setReferenceWork(this);
    }

    public void addTaxonCitations(final TaxonCitation taxonCitation)
    {
        this.taxonCitations.add(taxonCitation);
        taxonCitation.setReferenceWork(this);
    }

    public void addDeterminationCitations(final DeterminationCitation determinationCitation)
    {
        this.determinationCitations.add(determinationCitation);
        determinationCitation.setReferenceWork(this);
    }

    public void addAuthor(final Author author)
    {
        this.authors.add(author);
        author.setReferenceWork(this);
    }

    // Done Add Methods

    // Delete Methods

    public void removeLocalityCitations(final LocalityCitation localityCitation)
    {
        this.localityCitations.remove(localityCitation);
        localityCitation.setReferenceWork(null);
    }

    public void removeCollectionObjectCitations(final CollectionObjectCitation collectionObjectCitation)
    {
        this.collectionObjectCitations.remove(collectionObjectCitation);
        collectionObjectCitation.setReferenceWork(null);
    }

    public void removeTaxonCitations(final TaxonCitation taxonCitation)
    {
        this.taxonCitations.remove(taxonCitation);
        taxonCitation.setReferenceWork(null);
    }

    public void removeDeterminationCitations(final DeterminationCitation determinationCitation)
    {
        this.determinationCitations.remove(determinationCitation);
        determinationCitation.setReferenceWork(null);
    }

    public void removeAuthor(final Author author)
    {
        this.authors.remove(author);
        author.setReferenceWork(null);
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
        return 69;
    }

}
