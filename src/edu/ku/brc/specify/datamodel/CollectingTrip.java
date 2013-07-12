/* Copyright (C) 2013, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.specify.datamodel;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterIFace;

/**
 * CollectingTrip generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "collectingtrip")
@org.hibernate.annotations.Table(appliesTo="collectingtrip", indexes =
    {   
        @Index (name="COLTRPNameIDX", columnNames={"CollectingTripName"}),
        @Index (name="COLTRPStartDateIDX", columnNames={"StartDate"})
    })
public class CollectingTrip extends DisciplineMember implements java.io.Serializable
{
     // Fields    
    protected Integer               collectingTripId;
    protected String                remarks;
    protected Calendar              startDate;
    protected Byte                  startDatePrecision; // Accurate to Year, Month, Day
    protected String                startDateVerbatim;
    protected Calendar              endDate;
    protected Byte                  endDatePrecision;   // Accurate to Year, Month, Day
    protected String                endDateVerbatim;
    protected Short                 startTime;          // Minutes in 24 hours
    protected Short                 endTime;            // Minutes in 24 hours
    protected String                verbatimLocality;
    protected String                collectingTripName;
    protected String                sponsor;
    
    protected String                text1;
    protected String                text2;
    protected String                text3;
    protected String                text4;
    protected Integer               number1;
    protected Integer               number2;
    protected Boolean               yesNo1;
    protected Boolean               yesNo2;
    
    protected Set<CollectingEvent>  collectingEvents;
    protected Set<FundingAgent>     fundingAgents;

    // Constructors

    /** default constructor */
    public CollectingTrip()
    {
        // do nothing
    }
    
    /** constructor with id */
    public CollectingTrip(Integer collectingTripId) {
        this.collectingTripId = collectingTripId;
    }
   
    
    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        collectingTripId   = null;
        remarks            = null;
        startDate          = null;
        startDatePrecision = null;
        startDateVerbatim  = null;
        endDate            = null;
        endDatePrecision   = null;
        endDateVerbatim    = null;
        startTime          = null;
        endTime            = null;
        collectingTripName = null;
        sponsor            = null;
        
        text1   = null;
        text2   = null;
        text3   = null;
        text4   = null;
        number1 = null;
        number2 = null;
        yesNo1  = null;
        yesNo2  = null;
        
        fundingAgents = new HashSet<FundingAgent>();
        collectingEvents = new HashSet<CollectingEvent>();
    }

    // End Initializer

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "CollectingTripID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getCollectingTripId() {
        return this.collectingTripId;
    }
    
    public void setCollectingTripId(Integer collectingTripId) {
        this.collectingTripId = collectingTripId;
    }

    @Transient
    @Override
    public Integer getId()
    {
        return collectingTripId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return CollectingTrip.class;
    }
    
    /**
     * 
     */
    @Lob
    @Column(name = "Remarks", length = 4096)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "StartDate", unique = false, nullable = true, insertable = true, updatable = true)
    public Calendar getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     */
    @Column(name = "StartDatePrecision")
    public Byte getStartDatePrecision() {
        return this.startDatePrecision != null ? this.startDatePrecision : (byte)UIFieldFormatterIFace.PartialDateEnum.Full.ordinal();
    }
    
    public void setStartDatePrecision(Byte startDatePrecision) {
        this.startDatePrecision = startDatePrecision;
    }

    /**
     * 
     */
    @Column(name = "StartDateVerbatim", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getStartDateVerbatim() {
        return this.startDateVerbatim;
    }
    
    public void setStartDateVerbatim(String startDateVerbatim) {
        this.startDateVerbatim = startDateVerbatim;
    }

    /**
     * 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "EndDate", unique = false, nullable = true, insertable = true, updatable = true)
    public Calendar getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 
     */
    @Column(name = "EndDatePrecision")
    public Byte getEndDatePrecision() {
        return this.endDatePrecision != null ? this.endDatePrecision : (byte)UIFieldFormatterIFace.PartialDateEnum.Full.ordinal();
    }
    
    public void setEndDatePrecision(Byte endDatePrecision) {
        this.endDatePrecision = endDatePrecision;
    }

    /**
     * 
     */
    @Column(name = "EndDateVerbatim", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getEndDateVerbatim() {
        return this.endDateVerbatim;
    }
    
    public void setEndDateVerbatim(String endDateVerbatim) {
        this.endDateVerbatim = endDateVerbatim;
    }

    /**
     * 
     */
    @Column(name = "StartTime", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Short startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     */
    @Column(name = "EndTime", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Short endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the collectingTripName
     */
    @Column(name = "CollectingTripName", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getCollectingTripName()
    {
        return collectingTripName;
    }

    /**
     * @param name the collectingTripName to set
     */
    public void setCollectingTripName(String collectingTripName)
    {
        this.collectingTripName = collectingTripName;
    }

    /**
     * @return the sponsor
     */
    @Column(name = "Sponsor", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getSponsor()
    {
        return sponsor;
    }

    /**
     * @param sponsor the sponsor to set
     */
    public void setSponsor(String sponsor)
    {
        this.sponsor = sponsor;
    }

    /**
     * @return the text1
     */
    @Column(name = "Text1", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getText1()
    {
        return text1;
    }

    /**
     * @param text1 the text1 to set
     */
    public void setText1(String text1)
    {
        this.text1 = text1;
    }

    /**
     * @return the text2
     */
    @Column(name = "Text2", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getText2()
    {
        return text2;
    }

    /**
     * @param text2 the text2 to set
     */
    public void setText2(String text2)
    {
        this.text2 = text2;
    }

    /**
     * @return the text3
     */
    @Column(name = "Text3", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getText3()
    {
        return text3;
    }

    /**
     * @param text3 the text3 to set
     */
    public void setText3(String text3)
    {
        this.text3 = text3;
    }

    /**
     * @return the text4
     */
    @Column(name = "Text4", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getText4()
    {
        return text4;
    }

    /**
     * @param text4 the text4 to set
     */
    public void setText4(String text4)
    {
        this.text4 = text4;
    }

    /**
     * User definable
     */
    @Column(name = "Number1", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getNumber1() {
        return this.number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    /**
     * User definable
     */
    @Column(name = "Number2", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getNumber2() {
        return this.number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    /**
     * User definable
     */
    @Column(name="YesNo1",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getYesNo1() {
        return this.yesNo1;
    }

    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    /**
     * User definable
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
    @OneToMany(mappedBy = "collectingTrip")
    @Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN} )
    @OrderBy("orderNumber ASC")
    public Set<FundingAgent> getFundingAgents() 
    {
        return this.fundingAgents;
    }
    
    public void setFundingAgents(Set<FundingAgent> fundingAgents) 
    {
        this.fundingAgents = fundingAgents;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "collectingTrip")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    public Set<CollectingEvent> getCollectingEvents() 
    {
        return this.collectingEvents;
    }
    
    public void setCollectingEvents(Set<CollectingEvent> collectingEvents) 
    {
        this.collectingEvents = collectingEvents;
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
        return 87;
    }

}
