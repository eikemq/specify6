package edu.ku.brc.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import org.jdesktop.animation.timing.Envelope.EndBehavior;
import org.jdesktop.animation.timing.Envelope.RepeatBehavior;

/**
 * 
 * @code_status Beta
 * @author jstewart
 */
@SuppressWarnings("serial")
public class BasicAnimatedComponent extends AnimationComponent
{
	public BasicAnimatedComponent(	double repeatCount,
									int begin,
									RepeatBehavior repeatBehavior,
									EndBehavior endBehavior,
									int duration,
									int resolution)
	{
		super(repeatCount,begin,repeatBehavior,endBehavior,duration,resolution);
	}

	@Override
	protected void doPaintComponent(Graphics g)
	{
		Color bg = Color.BLACK;
		Color fg = Color.GREEN;
		
		int w = getWidth();
		int h = getHeight();
		int size = Math.min(w, h);
		int halfSize = (int)(size/2.0);
		int x = getX();
		int y = getY();

		g.setColor(bg);
		g.fillOval(x, y, size, size);

		int startAngle = 90 + (int)(percent * -360);
		int angleWidth = 30;
		
		Graphics2D g2d = (Graphics2D)g;

		double radEnd = degreesToRadians(startAngle+angleWidth);
	
		Point a = new Point(halfSize,halfSize);
		int bx = (int)(halfSize+halfSize * Math.cos(radEnd));
		int by = (int)(halfSize-halfSize * Math.sin(radEnd));
		Point b = new Point(bx,by);
				
		g2d.setPaint(new GradientPaint(a,fg,b,bg));
		g.fillArc(x+1, y+1, size-2, size-2, startAngle, angleWidth);
	}
	
	protected double degreesToRadians( int degrees )
	{
		double rad = degrees * (Math.PI / 180);
		return rad;
	}
}
