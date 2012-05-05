package graph;


import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


import javax.swing.*;
/**
 *  Graph class using for drawing basic graph structure
 *  this class is an abstract class hence its not a complete implementation 
 *  of line graphing.This class only 
 *  1-draw x vertical guides with hours label underneath of each line(for 24 hours).
 *  2-draw a box which surround the graph inside itself
 *  3-draw strings(all derived class should use this method for draw string inside graph)
 *  all derived classes should implement their own version of
 *  plotting spots and lines and Y horizontal guides and any other
 *  information they want to show
 * @author Seyed Vahid Hashemi 
 *
 */
public abstract class Graph extends JPanel 
{
	
	//using this number for indicating extended boundaries from above and below 
	//for avoiding to touch the plotted lines with bottom or ceiling of the graph
	protected final float GRAPH_BOUND = 5;
	 
	private int borderSpace = 32;
    
    private Color boxColor = Color.BLACK;
    private Color fontColor = Color.BLACK;
    private Color lineColor = Color.GREEN;
    private Color spotColor = Color.RED;
    private Font smallFont;
    
    private float[] originalData; // reserve the original data in this array
    private float[] originalUnsignedData; // the original data but mapped to +ve area
    private float originalMinData; //for calculating the unsigned data we need to have the minimum
    
   
    
    protected void setOriginalData(float[] data)
    {
    	originalData = data;
    }
    
    /**
     * return all data of originaldata as it is
     * @return
     */
    protected float[] getOriginalData()
    {
    	return originalData;
    }
    
    protected void setOriginalUnsignedData(float[] data)
    {
    	originalUnsignedData = data;
    }
    
    /**
     * return all data of originaldata but only in +ve
     * @return
     */
    protected float[] getOriginalUnsignedData()
    {
    	return originalUnsignedData;
    }
    
    /**
     * calculating the minimum data of array
     * @return
     */
    protected float getOriginalMinData()
    {
    	ArrayList<Float> num = new ArrayList<Float>();
    	for(int i=0;i<getOriginalData().length;i++)
    	{
    		num.add(Float.valueOf(getOriginalData()[i]));
    	}
    	Collections.sort(num);
    	return num.get(0).floatValue();
    }
    
   

	/**
   	 * @return the borderSpace
   	 */
	public int getBorderSpace() {
   		return borderSpace;
   	}

   	/**
   	 * set border space of top , bottom , left , right equally
   	 * @param borderSpace the borderSpace to set
   	 */
   	public void setBorderSpace(int borderSpace) {
   		this.borderSpace = borderSpace;
   	}
    
    /**
	 * @return the smallFont
	 */
   	public Font getSmallFont() {
		return smallFont;
	}

	/**
	 * set the font for writting underneath of graph or anyother text around the graph
	 * NOTE : its better to use small size font to make graph neat
	 * @param smallFont the smallFont to set
	 */
	public void setSmallFont(Font smallFont) {
		this.smallFont = smallFont;
	}

	

	/**
	 * @return the boxColor
	 */
	protected Color getBoxColor() {
		return boxColor;
	}

	/**
	 * @param boxColor the boxColor to set
	 */
	protected void setBoxColor(Color boxColor) {
		this.boxColor = boxColor;
		repaint();
	}

	/**
	 * @return the fontColor
	 */
	protected Color getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor the fontColor to set
	 */
	protected void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		repaint();
	}

	/**
	 * @return the lineColor
	 */
	protected Color getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor the lineColor to set
	 */
	protected void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		repaint();
	}

	/**
	 * @return the spotColor
	 */
	protected Color getSpotColor() {
		return spotColor;
	}

	/**
	 * @param spotColor the spotColor to set
	 */
	protected void setSpotColor(Color spotColor) {
		this.spotColor = spotColor;
		repaint();
	}
	
	
	/**
	 * Constructor
	 */

	public Graph()
    {
		setSmallFont(new Font("Arial", 1,8));
		repaint();
    }
    
    /**
     * Plot a box graph
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight() ;
        
        drawXguids(g2, height, width);
        //drawing a box
          Rectangle2D rect = new Rectangle2D.Double(borderSpace ,borderSpace , width - borderSpace * 2,height - borderSpace *2);
          g2.setPaint(getBoxColor());
          g2.draw(rect);
    }
    
    protected void drawString(Graphics2D g2 ,String text , float X , float Y , Color fontcolor , Font font)
    {
    	
    	Paint tempPaint = g2.getPaint();
    	g2.setFont(font);
    	g2.setPaint(fontcolor);
    	g2.drawString(text, X, Y);
    	g2.setPaint(tempPaint);
    	
    }
    
    /**
     * All strings in graph should be drawn by the help of this function
     * @param g2
     * @param text
     * @param X
     * @param Y
     */
    protected void drawString(Graphics2D g2 , String text , float X , float Y )
    {
    	Paint tempPaint = g2.getPaint();
    	
    	g2.setPaint(getFontColor());
    	g2.setFont(getSmallFont());
    	g2.drawString(text, X, Y);
    	
    	g2.setPaint(tempPaint);
    	
    }
    
    /**
     * Drawing x guides vertically and hours underneath of each line
     * it will draw 24 lines based on 24 hours a day
     * the 0=midnight 12=noon
     * @param g2
     * @param height height of form or frame
     * @param width width of form or frame
     */
    //private void drawXguides(Graphics2D g2,int height , int width,double xInc)
    protected void drawXguids(Graphics2D g2,int height , int width)
    {
    	 int hour =0;
         String noon = "noon";
         String midNight = "midnight";
         String temp="";
         
         //draw hours X guide
         for(int xi= borderSpace; xi<=width - borderSpace*2 ; xi= xi + (width-borderSpace*2)/24 )
         {
         	//drawing gray lines in X axis
         	g2.setPaint(Color.GRAY);
         	g2.draw(new Line2D.Double(width -  xi-10 , borderSpace, width-xi -10 , height- borderSpace));
         	
         	//drawing small black spot underneath of X axis
         	g2.setPaint(Color.BLACK);
         	g2.draw(new Line2D.Double(width -  xi-10 , height- borderSpace , width-xi-10  , height- borderSpace+4));
         	//adding hour for each spot
         	
         	g2.setFont(smallFont);
         	if (hour == 13)
         		hour = 1;
         	
         	//labling each hour
         	//0 = minight
         	//12 = noon
         	temp = String.valueOf(hour);
         	
         	if (hour == 12)
         	{
         		temp  = noon;
         	}
         	
         	if (hour == 0)
         	{
         		temp = midNight;
         		drawString(g2, temp, xi-12 , height - borderSpace + 10);
         	}
         	else
         	{
         		drawString(g2, temp, xi-5 , height - borderSpace + 10);
         	}
         	
         	hour++;
         	
         }
    }

    /**
     * All derived classes should implement drawYGuides 
     * As this method will be responsible for drawing
     * horizontal lines from borderspace() to width-borderspace
     * @param g2
     * @param scale This number should calculate based on maximum number of plotting data
     * @param height
     * @param width
     * @param color
     */
    protected abstract void drawYGuides(Graphics2D g2, double scale , int height , int width ,Color color );
    /**
     * For draw small circle of each point all derived class
     * should implement this class
     * @param g2
     * @param scale This number should calculate based on maximum number of plotting data
     * @param xInc This number should calculate based on numbers of data of plotting data
     * @param height
     * @param color
     */
    protected abstract void drawSpots(Graphics2D g2, double scale , double xInc ,int height , Color color );
    
    /**
     * For connecting the small circles together all derived classes
     * should implement this method
     * @param g2
     * @param scale scale This number should calculate based on maximum number of plotting data
     * @param xInc xInc This number should calculate based on numbers of data of plotting data
     * @param height
     * @param color
     */
    protected abstract void drawLines(Graphics2D g2 , double scale , double xInc , int height , Color color);
    
    /**
     * Printing extra information such as average and unit etc...
     * should be implemented here 
     */ 
}
