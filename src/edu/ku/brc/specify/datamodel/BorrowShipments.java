package edu.ku.brc.specify.datamodel;

import java.util.Date;




/**

 */
public class BorrowShipments  implements java.io.Serializable {

    // Fields    

     protected Long borrowShipmentsId;
     protected String remarks;
     protected Date timestampModified;
     protected Date timestampCreated;
     protected String lastEditedBy;
     protected Shipment shipment;
     protected Borrow borrow;


    // Constructors

    /** default constructor */
    public BorrowShipments() {
    }
    
    /** constructor with id */
    public BorrowShipments(Long borrowShipmentsId) {
        this.borrowShipmentsId = borrowShipmentsId;
    }
   
    
    

    // Initializer
    public void initialize()
    {
        borrowShipmentsId = null;
        remarks = null;
        timestampModified = null;
        timestampCreated = new Date();
        lastEditedBy = null;
        shipment = null;
        borrow = null;
    }
    // End Initializer

    // Property accessors

    /**
     * 
     */
    public Long getBorrowShipmentsId() {
        return this.borrowShipmentsId;
    }
    
    public void setBorrowShipmentsId(Long borrowShipmentsId) {
        this.borrowShipmentsId = borrowShipmentsId;
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
    public String getLastEditedBy() {
        return this.lastEditedBy;
    }
    
    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    /**
     *      * The shipment
     */
    public Shipment getShipment() {
        return this.shipment;
    }
    
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    /**
     *      * The borrow being shipped (returned)
     */
    public Borrow getBorrow() {
        return this.borrow;
    }
    
    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }





    // Add Methods

    // Done Add Methods

    // Delete Methods

    // Delete Add Methods
}
