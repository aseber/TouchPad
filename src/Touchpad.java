import java.awt.*;
import javax.swing.*;

public class Touchpad
{
	
	public static void main(String[] args)
	{
		
		JFrame window = new JFrame("Touchpad(?)");
        window.setContentPane(new ContentPanel());
        window.setSize(517, 539);
        window.setLocation(100, 100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
		
	}
	
	public static class ContentPanel extends JPanel
	{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2330920055269379430L;
		static int appletX, appletY, oldAppletX, oldAppletY;
		static int[] boxX;
		static int[] boxY;
		static double saturation;
		static int[] sColor = {0, 0, 255};  // The start color value.
		static int[] eColor = {125, 125, 125};  // The end color value.
		int[] diffInColors = subtractArrays(eColor, sColor);
		static int countPerColumn, countPerRow;
		
		public ContentPanel()
		{
			
			countPerColumn = 50;  // Amount of boxes on the X axis
			countPerRow = 50;  // The amount of boxes on the Y axis
			
			constructField();
			
		}
	
		public void paintComponent(Graphics g)
		{
			
			super.paintComponent(g);
			
			appletX = getWidth();
			appletY = getHeight();
		
			if (appletX != oldAppletX || appletY != oldAppletY)
			{
			
				saturation = appletX * appletY * 0.0016;
				oldAppletX = appletX;
				oldAppletY = appletY;
			
				constructField();
			
			}
		
			int mouseX, mouseY;
			
			mouseX = getMouseInWindow(true)[0];
			mouseY = getMouseInWindow(true)[1];	
			
			for (int row = 0; row < countPerRow; row++)
			{
				
				for (int column = 0; column < countPerColumn; column++)
				{
				
					int curBox, x, y;
					double dist = 0.0;
					int[] color = new int[4];
					
					curBox = row*countPerColumn + column;
					dist = distBetweenVectors(mouseX, mouseY, boxX[curBox], boxY[curBox]);
					color = multiplyArrayByDouble(diffInColors, dist);
					color = multiplyArrayByDouble(color, (1/saturation));
					color = clampArray(maxArray(sColor, eColor), subtractArrays(eColor, color), minArray(sColor, eColor));
					x = row*(appletX/countPerRow);
					y = column*(appletY/countPerColumn);

					g.setColor(isMouseInApplet() ? new Color(color[0], color[1], color[2]) : new Color(sColor[0], sColor[1], sColor[2]));
					g.fillRect(x, y, (appletX/countPerRow), (appletY/countPerColumn));
					g.setColor(Color.BLACK);
					g.drawRect(x, y, (appletX/countPerRow), (appletY/countPerColumn));
				
				}
				
			}
			
			repaint();
			
		}
		
		public boolean isMouseInApplet()
		{
		
			double mouseX, mouseY;
		
			mouseX = getMouseInWindow(false)[0];
			mouseY = getMouseInWindow(false)[1];

			if (0 <= mouseX && appletX >= mouseX && 0 <= mouseY && appletY >= mouseY)
			{
			
				return true;
			
			}
		
			return false;
		
		}
	
		public static void constructField()
		{
	
			int x, y, count;
			boxX = new int[countPerRow*countPerColumn];
			boxY = new int[countPerRow*countPerColumn];
		
			count = 0;
		
			for (int row = 0; row < countPerRow; row++)
			{
			
				for (int column = 0; column < countPerColumn; column++)
				{
				
					x = row*(appletX/countPerRow);
					y = column*(appletY/countPerColumn);

					boxX[count] = x + (int)((appletX/countPerRow)/2);
					boxY[count] = y + (int)((appletY/countPerColumn)/2);
					count++;
				
				}
			
			}
		
		}
	
		public static double distBetweenVectors(int a, int b, int c, int d)
		{

			return Math.sqrt(Math.pow(c - a, 2.0) + Math.pow(d - b, 2.0));
		
		}

		public static int[] subtractArrays(int[] a, int[] b)
		{
		
			int r1, g1, b1, r2, g2, b2;
		
			r1 = g1 = b1 = r2 = g2 = b2 = 0;
		
			r1 = a[0];
			g1 = a[1];
			b1 = a[2];
			r2 = b[0];
			g2 = b[1];
			b2 = b[2];
		
			int[] output = {r1 - r2, g1 - g2, b1 - b2};
		
			return output;
		
		}
	
		public static int[] multiplyArrayByDouble(int[] a, double b)
		{
		
			int r1, g1, b1;
		
			r1 = g1 = b1 = 0;
		
			r1 = a[0];
			g1 = a[1];
			b1 = a[2];
		
			int[] output = {(int)(r1 * b), (int)(g1 * b), (int)(b1 * b)};
		
			return output;
		
		}
	
		public static int clampInt(int a, int b, int c)
		{
		
			return Math.max(c, Math.min(a, b));
		
		}
	
		public static int[] clampArray(int[] a, int[] b, int[] c)
		{
		
			int r1, g1, b1, r2, g2, b2, r3, g3, b3;
		
			r1 = g1 = b1 = r2 = g2 = b2 = r3 = g3 = b3 = 0;
		
			r1 = a[0];
			g1 = a[1];
			b1 = a[2];
		
			r2 = b[0];
			g2 = b[1];
			b2 = b[2];
		
			r3 = c[0];
			g3 = c[1];
			b3 = c[2];
		
			int[] output = {clampInt(r1, r2, r3), clampInt(g1, g2, g3), clampInt(b1, b2, b3)};
		
			return output;
		
		}
	
		public static int[] maxArray(int[] a, int[] b)
		{
		
			int r1, g1, b1, r2, g2, b2;
		
			r1 = g1 = b1 = r2 = g2 = b2 = 0;
		
			r1 = a[0];
			g1 = a[1];
			b1 = a[2];
		
			r2 = b[0];
			g2 = b[1];
			b2 = b[2];
		
			int[] output =  {Math.max(r1, r2), Math.max(g1, g2), Math.max(b1, b2)};
	
			return output;
		
		}
	
		public static int[] minArray(int[] a, int[] b)
		{
		
			int r1, g1, b1, r2, g2, b2;
		
			r1 = g1 = b1 = r2 = g2 = b2 = 0;
		
			r1 = a[0];
			g1 = a[1];
			b1 = a[2];
		
			r2 = b[0];
			g2 = b[1];
			b2 = b[2];
		
			int[] output =  {Math.min(r1, r2), Math.min(g1, g2), Math.min(b1, b2)};
	
			return output;
		
		}	

		public int[] getMouseInWindow(boolean a)
		{

			int[] output = new int[2];
			
			output[0] = (int)(MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().getX());
			output[1] = (int)(MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().getY());
			
			if (a == true)
			{
			
				output[0] = clampInt(appletX, output[0], 0);
				output[1] = clampInt(appletY, output[1], 0);
				
			}
			
			return output;
			
		}
		
	}
	
}