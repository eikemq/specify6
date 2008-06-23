package edu.ku.brc.specify.datamodel.busrules;

import java.awt.Component;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.ui.forms.BusinessRulesOkDeleteIFace;
import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.forms.FormViewObj;
import edu.ku.brc.ui.forms.Viewable;
import edu.ku.brc.ui.forms.validation.ValComboBoxFromQuery;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Feb 11, 2008
 *
 */
public class LocalityBusRules extends AttachmentOwnerBaseBusRules implements ListSelectionListener
{
    protected ValComboBoxFromQuery geographyCBX = null;
    /**
     * 
     */
    public LocalityBusRules()
    {
        super(Locality.class);
        
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BaseBusRules#initialize(edu.ku.brc.ui.forms.Viewable)
     */
    @Override
    public void initialize(Viewable viewableArg)
    {
        super.initialize(viewableArg);
        
        if (formViewObj != null)
        {
            Component comp = formViewObj.getCompById("4");
            if (comp != null && comp instanceof ValComboBoxFromQuery)
            {
                geographyCBX = (ValComboBoxFromQuery)comp;
                geographyCBX.addListSelectionListener(this);
            }
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BaseBusRules#formShutdown()
     */
    @Override
    public void formShutdown()
    {
        super.formShutdown();
        
        if (geographyCBX != null)
        {
            geographyCBX.removeListSelectionListener(this);
            geographyCBX = null;
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BaseBusRules#processBusinessRules(java.lang.Object)
     */
    @Override
    public STATUS processBusinessRules(Object dataObj)
    {
        // TODO Auto-generated method stub
        return super.processBusinessRules(dataObj);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BaseBusRules#afterFillForm(java.lang.Object)
     */
    @Override
    public void afterFillForm(Object dataObj)
    {
        super.afterFillForm(dataObj);
        
        if (viewable instanceof FormViewObj)
        {
            Locality locality = (Locality)dataObj;
            if (locality  != null)
            {
                boolean   enable   = locality.getGeography() != null && StringUtils.isNotEmpty(locality.getLocalityName());
                Component bgmComp  = formViewObj.getCompById("23");
                if (bgmComp != null)
                {
                    bgmComp.setEnabled(enable);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent e)
    {
        if (formViewObj != null)
        {
            /*Geography geography = (Geography)geographyCBX.getValue();
            Locality  locality  = (Locality)formViewObj.getDataObj();
            if (locality  != null)
            {
                locality.setGeography(geography);
            }*/
        }
        
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#okToDelete(java.lang.Object, edu.ku.brc.dbsupport.DataProviderSessionIFace, edu.ku.brc.ui.forms.BusinessRulesOkDeleteIFace)
     */
    @Override
    public void okToDelete(final Object dataObj,
                           final DataProviderSessionIFace session,
                           final BusinessRulesOkDeleteIFace deletable)
    {
        boolean isOK = false;
        if (deletable != null)
        {
            FormDataObjIFace dbObj = (FormDataObjIFace)dataObj;
            
            Integer id = dbObj.getId();
            if (id == null)
            {
                isOK = false;
                
            } else
            {
               isOK = okToDelete(new String[] {"collectingevent", "LocalityID"}, dbObj.getId());
            }
            deletable.doDeleteDataObj(dataObj, session, isOK);
            
        } else
        {
            super.okToDelete(dataObj, session, deletable);
        }
    }

}
