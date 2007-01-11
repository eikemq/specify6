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

import java.util.Calendar;
import java.util.Date;




/**

 */
public class LoanReturnPhysicalObject extends DataModelObjBase implements java.io.Serializable {

    // Fields    

     protected Long loanReturnPhysicalObjectId;
     protected Calendar returnedDate;
     protected Integer quantity;
     protected String remarks;
     protected LoanPhysicalObject loanPhysicalObject;
     protected DeaccessionPreparation deaccessionPreparation;
     protected Agent receivedBy;


    // Constructors

    /** default constructor */
    public LoanReturnPhysicalObject() {
    }
    
    /** constructor with id */
    public LoanReturnPhysicalObject(Long loanReturnPhysicalObjectId) {
        this.loanReturnPhysicalObjectId = loanReturnPhysicalObjectId;
    }
   
    
    

    // Initializer
    public void initialize()
    {
        loanReturnPhysicalObjectId = null;
        returnedDate = null;
        quantity = null;
        remarks = null;
        timestampCreated = new Date();
        timestampModified = null;
        lastEditedBy = null;
        loanPhysicalObject = null;
        deaccessionPreparation = null;
        receivedBy = null;
    }
    // End Initializer

    // Property accessors

    /**
     *      * PrimaryKey
     */
    public Long getLoanReturnPhysicalObjectId() {
        return this.loanReturnPhysicalObjectId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    public Long getId()
    {
        return this.loanReturnPhysicalObjectId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    public Class<?> getDataClass()
    {
        return LoanReturnPhysicalObject.class;
    }
    
    public void setLoanReturnPhysicalObjectId(Long loanReturnPhysicalObjectId) {
        this.loanReturnPhysicalObjectId = loanReturnPhysicalObjectId;
    }

    /**
     * 
     */
    public Calendar getReturnedDate() {
        return this.returnedDate;
    }
    
    public void setReturnedDate(Calendar returnedDate) {
        this.returnedDate = returnedDate;
    }

    /**
     *      * Quantity of items returned (necessary for lots)
     */
    public Integer getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
     *      * Link to LoanPhysicalObject table
     */
    public LoanPhysicalObject getLoanPhysicalObject() {
        return this.loanPhysicalObject;
    }
    
    public void setLoanPhysicalObject(LoanPhysicalObject loanPhysicalObject) {
        this.loanPhysicalObject = loanPhysicalObject;
    }

    /**
     *      * ID of associated (if present) DeaccessionPhysicalObject record
     */
    public DeaccessionPreparation getDeaccessionPreparation() {
        return this.deaccessionPreparation;
    }
    
    public void setDeaccessionPreparation(DeaccessionPreparation deaccessionPreparation) {
        this.deaccessionPreparation = deaccessionPreparation;
    }

    /**
     *      * Person processing the loan return
     */
    public Agent getReceivedBy() {
        return this.receivedBy;
    }
    
    public void setReceivedBy(Agent agent) {
        this.receivedBy = agent;
    }





    // Add Methods

    // Done Add Methods

    // Delete Methods

    // Delete Add Methods
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 55;
    }

}
