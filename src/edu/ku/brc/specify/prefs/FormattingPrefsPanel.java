package edu.ku.brc.specify.prefs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.PrefsPanelIFace;
import edu.ku.brc.af.prefs.PrefsSavable;
import edu.ku.brc.ui.UICacheManager;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.ViewFactory;
import edu.ku.brc.ui.forms.Viewable;
import edu.ku.brc.ui.forms.persist.View;
import edu.ku.brc.ui.validation.FormValidator;
import edu.ku.brc.ui.validation.ValComboBox;

/**
 * This panel will handle all the various options for formatting of data.
 *
 * @code_status Complete
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class FormattingPrefsPanel extends JPanel implements PrefsPanelIFace, PrefsSavable
{
    private static final Logger log  = Logger.getLogger(FormattingPrefsPanel.class);

    protected View         formView  = null;
    protected Viewable     form      = null;
    protected JComboBox    fontNames = null;
    protected JComboBox    fontSizes = null;
    protected JTextField   testField = null;

    /**
     * Constructor.
     */
    public FormattingPrefsPanel()
    {
        createUI();
    }

    /**
     * Create the UI for the panel
     */
    protected void createUI()
    {
        
        String viewName = "Formatting";
        String name     = "Preferences";

        formView = AppContextMgr.getInstance().getView(name, viewName);

        if (formView != null)
        {
            form = ViewFactory.createFormView(null, formView, null, AppPreferences.getRemote(), MultiView.NO_OPTIONS);
            add(form.getUIComponent(), BorderLayout.CENTER);

        } else
        {
            log.error("Couldn't load form with name ["+name+"] Id ["+viewName+"]");
        }

        form.setDataObj(AppPreferences.getRemote());
        
        ValComboBox fontNamesVCB = (ValComboBox)form.getCompById("fontNames");
        ValComboBox fontSizesVCB = (ValComboBox)form.getCompById("fontSizes");
        
        fontNames = fontNamesVCB.getComboBox();
        fontSizes = fontSizesVCB.getComboBox();
        
        testField = (JTextField)form.getCompById("fontTest");
        if (testField != null)
        {
            testField.setText("This is a Test");
        }
        
        Hashtable<String, String> namesUsed = new Hashtable<String, String>();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : ge.getAllFonts())
        {
            if (namesUsed.get(font.getFamily()) == null)
            {
                fontNames.addItem(font.getFamily());
                namesUsed.put(font.getFamily(), "X");
            }
        }
        for (int i=6;i<22;i++)
        {
            fontSizes.addItem(Integer.toString(i));
        }
        
        Font baseFont = UICacheManager.getBaseFont();
        if (baseFont != null)
        {
            fontNames.setSelectedItem(baseFont.getFamily());
            fontSizes.setSelectedItem(Integer.toString(baseFont.getSize()));
            
            if (testField != null)
            {
                ActionListener al = new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        testField.setFont(new Font((String)fontNames.getSelectedItem(), Font.PLAIN, fontSizes.getSelectedIndex()+6)); 
                        form.getUIComponent().validate();
                    }
                };
                fontNames.addActionListener(al);
                fontSizes.addActionListener(al);
            }
        }
        
        form.getValidator().validateForm();


    }

    //--------------------------------------------------------------------
    // PrefsSavable Interface
    //--------------------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.prefs.PrefsSavable#savePrefs()
     */
    public void savePrefs()
    {
        if (form.getValidator() == null || form.getValidator().hasChanged())
        {
            form.getDataFromUI();
            UICacheManager.setBaseFont(new Font((String)fontNames.getSelectedItem(), Font.PLAIN, fontSizes.getSelectedIndex()+6));
        }
    }


    //---------------------------------------------------
    // PrefsPanelIFace
    //---------------------------------------------------
    public FormValidator getValidator()
    {
        return form.getValidator();
    }

}
