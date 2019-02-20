/* Copyright (C) 2019, University of Kansas Center for Research
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

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.ku.brc.af.ui.db.PickListIFace;
import edu.ku.brc.af.ui.db.PickListItemIFace;
import edu.ku.brc.ui.TitleValueIFace;
import edu.ku.brc.util.Orderable;

/**
 * PickListItem generated by hbm2java
 *
 * @code_status Beta
 * 
 * @author rods
 *
 */
@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "picklistitem")
public class PickListItem extends DataModelObjBase implements PickListItemIFace, 
                                                              Orderable,
                                                              java.io.Serializable,
                                                              TitleValueIFace
{
    // Fields
    protected Integer  pickListItemId;
    private String     title;
    private String     value;
    private Integer    ordinal;
    protected PickList pickList;
    
    // Non-Persisted Value as an Object
    private Object valueObject;

    // Constructors

    /** default constructor */
    public PickListItem()
    {
        // do nothing
        //initialize();
    }

    public PickListItem(final String title, final String value, final Timestamp timestampCreated)
    {
        super();
        initialize();
        this.title = title;
        this.value = value;
        this.timestampCreated = timestampCreated;
    }

    public PickListItem(final String title, final Object valueObject, final Timestamp timestampCreated)
    {
        super();
        initialize();
        this.title       = title;
        this.valueObject = valueObject;
        this.timestampCreated = timestampCreated;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        super.init();
        pickListItemId = null;
        title          = null;
        value          = null;
        pickList       = null;
        ordinal        = 0;
    }
    /**
     * @return
     */
    @Id
    @GeneratedValue
    @Column(name = "PickListItemID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getPickListItemId()
    {
        return pickListItemId;
    }

    public void setPickListItemId(Integer pickListItemId)
    {
        this.pickListItemId = pickListItemId;
    }

    /**
     * 
     */
    @Column(name = "Title", nullable = false, length = 128)
    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "PickListID", nullable = false)
    public PickList getPickList()
    {
        return pickList;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.db.PickListItemIFace#SetPickList(edu.ku.brc.af.ui.db.PickListIFace)
     */
    public void SetPickList(PickListIFace pickListArg)
    {
        if (pickListArg instanceof PickList)
        {
            this.pickList = (PickList)pickListArg;
        } else
        {
            throw new RuntimeException("PickListIFace is not of class PickList");
        }
    }
    
    public void setPickList(PickList pickList)
    {
        this.pickList = pickList;
    }
    
    public void setPickList(PickListIFace pickList)
    {
    	setPickList((PickList)pickList);
    }

    /**
     * 
     */
    @Column(name = "Value", length = 128)
    public String getValue()
    {
        return this.value;// == null ? title : (value.equals("|null|") ? null : value);
    }

    public void setValue(String value)
    {
        this.value = value;
    }
    
    @Transient
    public Object getValueObject()
    {
        return valueObject == null ? value : valueObject;
    }

    public void setValueObject(Object valueObject)
    {
        this.valueObject = valueObject;
    }

    /**
     * @return the ordinal
     */
    @Column(name = "Ordinal")
    public Integer getOrdinal()
    {
        return ordinal == null ? 0 : ordinal;
    }

    /**
     * @param ordinal the ordinal to set
     */
    public void setOrdinal(Integer ordinal)
    {
        this.ordinal = ordinal;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return title;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.PickListItemIFace#getId()
     */
    @Transient
    public Integer getId()
    {
        return pickListItemId;
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
    @Transient
    public static int getClassTableId()
    {
        return 501;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getDataClass()
     */
    @Override
    @Transient
    public Class<?> getDataClass()
    {
        return PickListItem.class;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    {
        return StringUtils.isNotEmpty(title) ? title : super.getIdentityTitle();
    } 
    

    /* (non-Javadoc)
     * @see edu.ku.brc.util.Orderable#getOrderIndex()
     */
    @Transient
    @Override
    public int getOrderIndex()
    {
        return getOrdinal();
    }

    //-------------------------------------
    // Orderable
    //-------------------------------------
    /* (non-Javadoc)
     * @see edu.ku.brc.util.Orderable#setOrderIndex(int)
     */
    @Override
    public void setOrderIndex(int order)
    {
        ordinal = order;
    }
    
    //-------------------------------------
    // Comparable
    //-------------------------------------
    public int compareTo(PickListItemIFace obj)
    {
        Byte sortType = null;
        
        if (pickList != null)
        {
            sortType = pickList.getSortType();
        }
        
        if (sortType != null && sortType.equals(PickListIFace.PL_ORDINAL_SORT))
        {
            if (ordinal.equals(obj.getOrdinal()))
            {
                return 0;
            }
            // else
            return ordinal.compareTo(obj.getOrdinal());
        }
        
        // Default to title
        if (title != null && obj != null && obj.getTitle() != null)
        {
            return title.compareTo(obj.getTitle());
        }
        // else
        return 0;
    }


}
