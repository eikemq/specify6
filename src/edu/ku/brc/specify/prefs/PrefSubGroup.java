/* Filename:    $RCSfile: PrefSubGroup.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2005/10/19 19:59:54 $
 *
 * This library is free software; you can redistribute it and/or
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
package edu.ku.brc.specify.prefs;

import java.util.ArrayList;
import java.util.Collection;


public class PrefSubGroup implements PrefSubGroupIFace
{
    private Collection<Preference> preferences = new ArrayList<Preference>();
    
    private String name;
    private String iconName;
    
    public PrefSubGroup() 
    {
        
    }

    public String getName() 
    {
        return this.name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getIconName() 
    {
        return iconName;
    }

    public void setIconName(String iconName) 
    {
        this.iconName = iconName;
    }

    public void addPreference(Preference aPref)
    {
        preferences.add(aPref);
    }
    
    public Collection<Preference> getPreferences()
    {
        return preferences;
    }
    
    public PrefIFace getPrefByName(String aName)
    {
        for (Preference pref : preferences)
        {
            if (pref.getName().equals(aName))
            {
                return pref;
            }
        }
        return null;
    }

}
