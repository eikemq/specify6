/**
 * 
 */
package edu.ku.brc.specify.ui.treetables;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import edu.ku.brc.specify.datamodel.TreeDefinitionItemIface;

/**
 *
 *
 * @author jstewart
 * @version %I% %G%
 */
public class TreeDefListCellRenderer extends DefaultListCellRenderer
{
	protected Icon enforcedIcon;
	
	public TreeDefListCellRenderer(Icon enforcedIcon)
	{
		super();
		this.setHorizontalTextPosition(SwingConstants.LEFT);
		this.enforcedIcon = enforcedIcon;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		JLabel l = (JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
		
		if(value instanceof TreeDefinitionItemIface)
		{
			TreeDefinitionItemIface defItem = (TreeDefinitionItemIface)value;
			l.setText(defItem.getName());
			if(defItem.getIsEnforced()!=null && defItem.getIsEnforced().booleanValue()==true)
			{
				l.setIcon(enforcedIcon);
			}
		}
		
		return l;
	}
}
