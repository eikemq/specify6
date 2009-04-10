/* Copyright (C) 2009, University of Kansas Center for Research
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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.ku.brc.util.Orderable;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "dnasequenceattachment")
public class DNASequencingRunAttachment extends DataModelObjBase implements ObjectAttachmentIFace<DNASequencingRun>, 
                                                                       Orderable, 
                                                                       Serializable,
                                                                       Comparable<DNASequencingRunAttachment>
{
    protected Integer     dnaSequencingRunAttachmentId;
    protected DNASequencingRun dnaSequencingRun;
    protected Attachment  attachment;
    protected Integer     ordinal;
    protected String      remarks;
    
    public DNASequencingRunAttachment()
    {
        
    }

    @Id
    @GeneratedValue
    @Column(name = "DnaSequencingRunAttachmentId")
    public Integer getDnaSequencingRunAttachmentId()
    {
        return dnaSequencingRunAttachmentId;
    }

    public void setDnaSequencingRunAttachmentId(Integer dnaSequencingRunAttachmentId)
    {
        this.dnaSequencingRunAttachmentId = dnaSequencingRunAttachmentId;
    }

    @Override
    public void initialize()
    {
        super.init();

        dnaSequencingRunAttachmentId = null;
        dnaSequencingRun             = null;
        attachment              = null;
        ordinal                 = null;
        remarks                 = null;
    }

    @Column(name = "Ordinal")
    public Integer getOrdinal()
    {
        return this.ordinal;
    }

    public void setOrdinal(Integer ordinal)
    {
        this.ordinal = ordinal;
    }

    @Lob
    @Column(name = "Remarks", length = 4096)
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    @ManyToOne
    @JoinColumn(name = "DnaSequencingRunID", nullable = false)
    public DNASequencingRun getDnaSequencingRun()
    {
        return dnaSequencingRun;
    }

    public void setDnaSequencingRun(DNASequencingRun dnaSequencingRun)
    {
        this.dnaSequencingRun = dnaSequencingRun;
    }

    @ManyToOne
    @JoinColumn(name = "AttachmentID", nullable = false)
    @Cascade( {CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK} )
    public Attachment getAttachment()
    {
        return this.attachment;
    }

    public void setAttachment(Attachment attachment)
    {
        this.attachment = attachment;
    }

    @Transient
    public DNASequencingRun getObject()
    {
        return getDnaSequencingRun();
    }

    public void setObject(DNASequencingRun dnaSequencingRun)
    {
        this.dnaSequencingRun = dnaSequencingRun;
    }

    @Transient
    public int getOrderIndex()
    {
        return getOrdinal();
    }

    public void setOrderIndex(int order)
    {
        setOrdinal(order);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    {
         return Attachment.getIdentityTitle(this);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getDataClass()
     */
    @Override
    @Transient
    public Class<?> getDataClass()
    {
        return DNASequencingRunAttachment.class;
    }

    @Override
    @Transient
    public Integer getId()
    {
        return dnaSequencingRunAttachmentId;
    }

	/**
     * @return the Table ID for the class.
     */
	@Transient
    public static int getClassTableId()
    {
    	return 135;
    }
	
    @Override
    @Transient
    public int getTableId()
    {
        return getClassTableId();
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(DNASequencingRunAttachment obj)
    {
        return ordinal.compareTo(obj.ordinal);
    }

}
