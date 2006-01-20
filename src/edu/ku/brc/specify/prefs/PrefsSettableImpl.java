/* Filename:    $RCSfile: PrefsSettableImpl.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2006/01/10 16:52:27 $
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

import java.util.prefs.*;

import edu.ku.brc.specify.ui.forms.*;

/**
 * Class that implements the DataObjectSettable for preferences
 * 
 * @author rods
 *
 */
public class PrefsSettableImpl implements DataObjectSettable
{

    public PrefsSettableImpl()
    {
        super();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.forms.DataObjectSettable#setFieldValue(java.lang.Object, java.lang.String, java.lang.Object)
     */
    public void setFieldValue(Object dataObj, String fieldName, Object data)
    {
        if (dataObj instanceof Preferences)
        {
            Preferences prefNode = (Preferences)dataObj;
            //System.out.println("setFieldValue["+dataObj+"]  ["+fieldName+"]  ["+ data+"]");
            prefNode.put(fieldName, data == null ? "" : data.toString());
            /*try
            {
                prefNode.flush();
            } catch (BackingStoreException ex)
            {
                // XXX FIXME
            }*/
        } else
        {
            throw new RuntimeException("In setFieldValue dataObj["+dataObj+"] is not of class Preferences.");
        }
    }
}
