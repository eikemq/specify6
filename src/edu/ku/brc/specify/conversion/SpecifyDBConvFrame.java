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
package edu.ku.brc.specify.conversion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A Frame displaying the progress of the conversion process.
 * 
 * @author rods
 *
 * @code_status Beta
 *
 * Created Date: Nov 7, 2006
 *
 */
public class SpecifyDBConvFrame extends JFrame
{
    protected JProgressBar overallProgress;
    protected JProgressBar processProgress;
    protected JLabel       desc;
    protected JButton      closeBtn;
    protected JFrame       instance;
    
    public SpecifyDBConvFrame()
    {
        createUI();
    }
    
    protected void createUI()
    {
        PanelBuilder    builder    = new PanelBuilder(new FormLayout("p,2px,f:p:g", "p,5px,p,5px,p,10px,p"));
        CellConstraints cc         = new CellConstraints();

        overallProgress = new JProgressBar();
        processProgress = new JProgressBar();
        desc            = new JLabel("");
        closeBtn        = new JButton("Close");
        
        processProgress.setStringPainted(true);
        closeBtn.setEnabled(false);

        desc.setHorizontalAlignment(JLabel.CENTER);
        builder.add( desc, cc.xywh(1,1,3,1));
        
        builder.add( new JLabel("Process:"), cc.xy(1,3));
        builder.add( processProgress, cc.xy(3,3));
        
        builder.add( new JLabel("Overall:"), cc.xy(1,5));
        builder.add( overallProgress, cc.xy(3,5));
        
        builder.add( closeBtn, cc.xy(1,7));
        
        builder.getPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(builder.getPanel());
        
        setSize(new Dimension(500,200));
        
        setTitle("Converting");
        
        overallProgress.setIndeterminate(true);
        
        instance = this;
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                instance.setVisible(false);
                System.exit(0);
            }
        });
    }
    
    public synchronized void incOverall()
    {

        overallProgress.setValue(overallProgress.getValue() + 1);

    }
    
    public synchronized void setOverall(final int min, final int max)
    {
        overallProgress.setIndeterminate(min == 0 && max == 0);
        overallProgress.setMinimum(min);
        overallProgress.setMaximum(max);
        overallProgress.setValue(min);

    }
    
    public synchronized void setOverall(final int value)
    {

        overallProgress.setValue(value);

    }
    
    public synchronized void setProcess(final int min, final int max)
    {
        processProgress.setMinimum(min);
        processProgress.setMaximum(max);
        processProgress.setValue(min);
        closeBtn.setEnabled(false);
        processProgress.setString("");
    }
    
    public synchronized void setProcess(final int value)
    {
        processProgress.setValue(value);
        processProgress.setString(value > 0 ? (processProgress.getValue() +" / "+ processProgress.getMaximum()) : "");

    }
    
    public synchronized void setDesc(final String text)
    {

        desc.setText(text);

    }
    
    public synchronized void processDone()
    {

        desc.setText("");
        processProgress.setMinimum(0);
        processProgress.setMaximum(0);
        processProgress.setValue(0);
        processProgress.setString("");
        closeBtn.setEnabled(true);

    }
}
