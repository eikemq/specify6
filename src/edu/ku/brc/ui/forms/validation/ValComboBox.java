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

package edu.ku.brc.ui.forms.validation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.af.prefs.AppPrefsChangeEvent;
import edu.ku.brc.af.prefs.AppPrefsChangeListener;
import edu.ku.brc.specify.datamodel.PickListItem;
import edu.ku.brc.ui.AutoCompletion;
import edu.ku.brc.ui.ColorWrapper;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.db.PickListDBAdapterIFace;
import edu.ku.brc.ui.db.PickListItemIFace;
import edu.ku.brc.ui.forms.FormDataObjIFace;


/**
 * A JComboBox that implements UIValidatable for participating in validation

 * @code_status Beta
 **
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ValComboBox extends JPanel implements UIValidatable, ListDataListener, GetSetValueIFace, AppPrefsChangeListener
{
    protected static Color        defaultTextBGColor = null;
    protected static ColorWrapper valtextcolor       = null;
    protected static ColorWrapper requiredfieldcolor = null;


    protected UIValidatable.ErrorType valState  = UIValidatable.ErrorType.Valid;
    protected boolean isRequired = false;
    protected boolean isChanged  = false;
    protected boolean isNew      = false;

    protected JComboBox         comboBox     = null;
    protected JTextComponent    textEditor   = null;
    protected String            defaultValue = null;

    /**
     * Constructor
     */
    public ValComboBox(boolean editable)
    {
        comboBox = new JComboBox();
        init(editable);
    }

    /**
     * Constructor
     * @param arg0 with a model
     */
    public ValComboBox(ComboBoxModel arg0, boolean editable)
    {
        comboBox = new JComboBox(arg0);
        init(editable);
    }

    /**
     * Constructor
     * @param arg0 object array of items
     */
    public ValComboBox(Object[] arg0, boolean editable)
    {
        comboBox = new JComboBox(arg0);
        init(editable);
    }

    /**
     * Constructor
     * @param arg0 vector of items
     */
    public ValComboBox(Vector<?> arg0, boolean editable)
    {
        comboBox = new JComboBox(arg0);
        init(editable);
    }

    /**
     * Constructor with dbAdapter
     * @param dbAdapter the adaptor for enabling auto complete
     */
    public ValComboBox(final PickListDBAdapterIFace adapter)
    {
        if (adapter instanceof ComboBoxModel)
        {
            comboBox = new JComboBox((ComboBoxModel)adapter);
        } else
        {
            throw new RuntimeException("PickListDBAdapterIFace is not an instanceof ComboBoxModel and MUST BE!");
        }
        
        init(!adapter.isReadOnly());
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.JAutoCompComboBox#init(boolean)
     */
    public void init(final boolean makeEditable)
    {

        if (makeEditable)
        {
            AutoCompletion.enable(comboBox);
            
            Component editComp = comboBox.getEditor().getEditorComponent();
            if (editComp instanceof JTextComponent)
            {
                textEditor = (JTextComponent)editComp;
            } else
            {
                throw new RuntimeException("JComboBox editor compoent is not an instanceof JTextComponent");
            }
        }

        setOpaque(false);
        
        if (defaultTextBGColor == null)
        {
            defaultTextBGColor = (new JTextField()).getBackground();
        }
        
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        PanelBuilder    builder    = new PanelBuilder(new FormLayout("f:p:g", "p:g:f"), this);
        CellConstraints cc         = new CellConstraints();
        builder.add(comboBox, cc.xy(1,1));

        comboBox.getModel().addListDataListener(this);

        if (valtextcolor == null || requiredfieldcolor == null)
        {
            valtextcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "valtextcolor");
            requiredfieldcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "requiredfieldcolor");
        }
        AppPreferences.getRemote().addChangeListener("ui.formatting.valtextcolor", this);
        AppPreferences.getRemote().addChangeListener("ui.formatting.requiredfieldcolor", this);

        FocusAdapter focusAdapter = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e)
            {
                if (textEditor != null)
                {
                    String str = textEditor.getText().trim();
                    if (StringUtils.isNotEmpty(str))
                    {
                        Object selObj = comboBox.getSelectedItem();
                        if (selObj != null && !selObj.toString().equals(str))
                        {
                            textEditor.setText(selObj.toString());
                        }                        
                    } else
                    {
                        comboBox.setSelectedIndex(-1);
                    }
                }
                //valState = isRequired && comboBox.getSelectedIndex() == -1;
                isNew = false;
                repaint();
            }
        };
        if (textEditor != null)
        {
            textEditor.addFocusListener(focusAdapter);
        } else
        {
            comboBox.addFocusListener(focusAdapter);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        comboBox.setEnabled(enabled);
        
        // Cheap and easy way to set the BG Color depending on Enabled state
        setRequired(isRequired);
    }

    /**
     * Returns the model for the combo box
     * @return the model for the combo box
     */
    public ComboBoxModel getModel()
    {
        return comboBox.getModel();
    }


    /* (non-Javadoc)
     * @see javax.swing.JComboBox#setModel(javax.swing.ComboBoxModel)
     */
    public void setModel(ComboBoxModel model)
    {
        comboBox.setModel(model);
        model.addListDataListener(this);
    }

    /* (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        if (!isNew && valState == UIValidatable.ErrorType.Error && isEnabled())
        {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Dimension dim = getSize();
            g2d.setColor(valtextcolor.getColor());
            g2d.drawRect(0, 0, dim.width-1, dim.height-1);
        }
    }

    /**
     * Returns the combobox
     * @return the combobox
     */
    public JComboBox getComboBox()
    {
        return comboBox;
    }

    /**
     * Helper function that indicates whether the control has text or not
     * @return whether the control has text or not
     */
    public boolean hasText()
    {
        return textEditor != null ? textEditor.getText().length() > 0 : comboBox.getSelectedIndex() > -1;
    }

    /**
     * Helper function that returns true if an item is selected or the text field is not empty
     * @return true if an item is selected or the text field is not empty
     */
    public boolean isNotEmpty()
    {
        //System.out.println((comboBox.getSelectedIndex() > -1) +"  "+ hasText()+"  "+comboBox.getSelectedItem());
        return comboBox.getSelectedIndex() > -1 || hasText();
    }

    /* (non-Javadoc)
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    @Override
    public void addFocusListener(FocusListener l)
    {
        comboBox.addFocusListener(l);
        if (textEditor != null)
        {
            textEditor.addFocusListener(l);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Component#removeFocusListener(java.awt.event.FocusListener)
     */
    @Override
    public void removeFocusListener(FocusListener l)
    {
        comboBox.removeFocusListener(l);
        if (textEditor != null)
        {
            textEditor.removeFocusListener(l);
        }
    }

    //--------------------------------------------------
    //-- UIValidatable Interface
    //--------------------------------------------------


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#isInError()
     */
    public boolean isInError()
    {
        return valState != UIValidatable.ErrorType.Valid;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#getState()
     */
    public ErrorType getState()
    {
        return valState;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setState(edu.ku.brc.ui.forms.validation.UIValidatable.ErrorType)
     */
    public void setState(ErrorType state)
    {
        this.valState = state;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#isRequired()
     */
    public boolean isRequired()
    {
        return isRequired;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setRequired(boolean)
     */
    public void setRequired(boolean isRequired)
    {
        if (textEditor != null)
        {
            textEditor.setBackground(isRequired && isEnabled() ? requiredfieldcolor.getColor() : defaultTextBGColor);
        }
        this.isRequired = isRequired;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#isChanged()
     */
    public boolean isChanged()
    {
        return isChanged;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setChanged(boolean)
     */
    public void setChanged(boolean isChanged)
    {
        this.isChanged = isChanged;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setAsNew(boolean)
     */
    public void setAsNew(boolean isNew)
    {
        this.isNew = isRequired ? isNew : false;
    }

    /* (non-Javadoc)
     * @see java.awt.Component#validate()
     */
    public UIValidatable.ErrorType validateState()
    {

        valState = isRequired && comboBox.getSelectedIndex() == -1 ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;
        return valState;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#reset()
     */
    public void reset()
    {
        comboBox.setSelectedIndex(-1);
        if (textEditor != null)
        {
            textEditor.setText(StringUtils.isNotEmpty(defaultValue) ? defaultValue : "");
        }
        valState = isRequired ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;
        repaint();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#getValidatableUIComp()
     */
    public Component getValidatableUIComp()
    {
        return this;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#cleanUp()
     */
    public void cleanUp()
    {
        if (textEditor != null)
        {
            UIHelper.removeFocusListeners(textEditor);
            UIHelper.removeKeyListeners(textEditor);
        }
        comboBox.getModel().removeListDataListener(this);
        UIHelper.removeFocusListeners(comboBox);
        UIHelper.removeKeyListeners(this);
        comboBox = null;
        AppPreferences.getRemote().removeChangeListener("ui.formatting.requiredfieldcolor", this);
    }

    //--------------------------------------------------------
    // ListDataListener (JComboxBox)
    //--------------------------------------------------------

    /* (non-Javadoc)
     * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e)
    {
        isChanged = true;
        validateState();
        repaint();
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
     */
    public void intervalAdded(ListDataEvent e)
    {
        // do nothing
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
     */
    public void intervalRemoved(ListDataEvent e)
    {
        // do nothing
    }


    //--------------------------------------------------------
    // GetSetValueIFace
    //--------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#setValue(java.lang.Object, java.lang.String)
     */
    public void setValue(Object value, String defaultValue)
    {
        boolean fnd = false;

        if (value != null)
        {
            ComboBoxModel  model = comboBox.getModel();
            boolean isFormObjIFace = value instanceof FormDataObjIFace;

            if (comboBox.getModel() instanceof PickListDBAdapterIFace)
            {
                for (int i=0;i<comboBox.getItemCount();i++)
                {
                    
                    PickListItemIFace pli    = (PickListItemIFace)model.getElementAt(i);
                    Object            valObj = pli.getValueObject();
                    
                    if (valObj != null)
                    {
                        if (isFormObjIFace && valObj instanceof FormDataObjIFace)
                        {
                            //System.out.println(((FormDataObjIFace)value).getId().longValue()+"  "+(((FormDataObjIFace)valObj).getId().longValue()));
                            if (((FormDataObjIFace)value).getId().longValue() == (((FormDataObjIFace)valObj).getId().longValue()))
                            {
                                comboBox.setSelectedIndex(i);
                                fnd = true;
                                break;                                
                            }
                        } else if (pli.getValue().equals(value.toString()))
                        {
                            comboBox.setSelectedIndex(i);
                            fnd = true;
                            break;                            
                        }
                    } else
                    {
                        Object pliObj = pli.getValue();
                        if (pliObj != null && pliObj.equals(value.toString())) // really should never be null!
                        {
                            comboBox.setSelectedIndex(i);
                            fnd = true;
                            break;
                        }
                    }
                }


            } else
            {
                for (int i=0;i<comboBox.getItemCount();i++)
                {
                    Object item = model.getElementAt(i);
                    if (item instanceof String)
                    {
                        String val = value != null && StringUtils.isEmpty(value.toString()) && StringUtils.isNotEmpty(defaultValue) ? defaultValue : (value != null ? value.toString() : "");
                        if (((String)item).equals(val))
                        {
                            comboBox.setSelectedIndex(i);
                            fnd = true;
                            break;
                        }
                    } else if ((isFormObjIFace && item == value) || item.equals(value))
                    {
                        comboBox.setSelectedIndex(i);
                        fnd = true;
                        break;
                        
                    }
                }
            }
            
            if (fnd)
            {
                this.valState = UIValidatable.ErrorType.Valid;
            } else
            {
                comboBox.setSelectedIndex(-1);
                this.valState = (comboBox.getModel() instanceof PickListDBAdapterIFace && isRequired) || isRequired ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;
            }
        } else
        {
            valState = isRequired && comboBox.getSelectedIndex() == -1 ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;
            if (textEditor != null)
            {
                textEditor.setText("");
            }
            comboBox.setSelectedIndex(-1);
        }

        repaint();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#getValue()
     */
    public Object getValue()
    {
        if (textEditor != null && StringUtils.isEmpty(textEditor.getText().trim()))
        {
            return null;
        }
        
        Object selectedObj = comboBox.getSelectedItem();
        if (selectedObj != null)
        {
            if (comboBox.getModel() instanceof PickListDBAdapterIFace)
            {
                return selectedObj instanceof PickListItemIFace ? ((PickListItem)selectedObj).getValueObject() : selectedObj;
            }
        }
        return selectedObj;
    }

    //-------------------------------------------------
    // AppPrefsChangeListener
    //-------------------------------------------------

    public void preferenceChange(AppPrefsChangeEvent evt)
    {
        if (evt.getKey().equals("ui.formatting.requiredfieldcolor"))
        {
            if (textEditor != null)
            {
                textEditor.setBackground(isRequired && isEnabled() ? requiredfieldcolor.getColor() : defaultTextBGColor);
            }
        }
    }
}
