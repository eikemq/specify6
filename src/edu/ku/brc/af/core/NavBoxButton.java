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
package edu.ku.brc.af.core;

import javax.swing.ImageIcon;

import edu.ku.brc.ui.RolloverCommand;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 */
public class NavBoxButton extends RolloverCommand implements NavBoxItemIFace
{

    /**
     * Constructor.
     */
    public NavBoxButton()
    {
        // nothing to do 
    }

    /**
     * Constructor.
     * @param label the text label
     * @param imgIcon the image icon
     */
    public NavBoxButton(String label, ImageIcon imgIcon)
    {
        super(label, imgIcon);
    }
    
    /* (non-Javadoc)
     * @see java.awt.Component#toString()
     */
    public String toString()
    {
        return label;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final NavBoxItemIFace obj)
    {
        return label.toLowerCase().compareTo(obj.getTitle().toLowerCase());
    }
}
