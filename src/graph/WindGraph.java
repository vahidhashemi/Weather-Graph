package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

/**
 * WinGraph will draw 4 lines
 * 1-wind speeds in 24 hours
 * 2-gust speeds in 24 hours
 * 3-wind average as straight line
 * 4-gust average as straight line
 * 
 * the construct of this class is different that its
 * super class since this class is responsible for two
 * different range of data and since the mechanism of
 * the super class for finding the range is based on the
 * first argument minimum and maximum range . there is 
 * Probability of have greater range on second argument for this
 * matter we have a WindGraphBuilder which will take decision 
 * of which one of the two arguments have much bigger max and min
 * then it will pass the highest range first and so forth
 * 
 * this class also will show an extra information
 * two indicators at top right of the graph to indicate which
 * one of the lines belong to wind and which one belong to gust
 * @author Seyed Vahid Hashemi
 *
 */
public class WindGraph extends TemperatureGraph
{

	
	private float[] gustData;
	private float gustMaxData;
	
	private float averageWindSpeed;
	private float averageGustSpeed;
    private static String UNIT = "km/h";
    private static String AVERAGEWIND = "Average Wind Speed : ";
    private static String AVERAGEGUST = "Average Gust Speed : ";

    WindGraphBuilder windBuilder;
    
    
    public void setAverageWindSpeed(float t)
    {
    	averageWindSpeed = t;
    }
    
    public float getAverageWindSpeed()
    {
    	return averageWindSpeed;
    }
    
    public void setAverageGustSpeed(float t)
    {
    	averageGustSpeed = t;
    }
    
    public float getAverageGustSpeed()
    {
    	return averageGustSpeed;
    }
    
    
	
	protected float getGustMaxData()
	{
		return gustMaxData;
	}
	
	protected void setGustMaxData()
	{
		float max = -Float.MAX_VALUE;
        for(int i = 0; i < getGustData().length; i++) 
        {
            if(getGustDataAtIndex(i) > max)
                max =getGustDataAtIndex(i);
        }
        this.gustMaxData = max;
	}
	
	protected float[] getGustData()
	{
		return gustData;
	}
	
	protected float getGustDataAtIndex(int i) throws ArrayIndexOutOfBoundsException
	{
		return gustData[i];
	}
	
	protected void setGustData(float[] data)
	{
		gustData = data;
	}
	
	protected void setGustDataAtindex(float value , int i) throws ArrayIndexOutOfBoundsException
	{
		gustData[i] = value;
	}

	public WindGraph(WindGraphBuilder wgb)
	{
		
		super(wgb.getDataBig(),wgb.guidesNumber , wgb.drawPoints, wgb.drawLines);
		windBuilder = wgb;
		//since gust data  always should be in +ve so we don't need
		//to do any extra calculation just make ourselves sure that data are in
		//positive position
		setGustData(wgb.getDataSmall());
		setGustMaxData();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//calculating scale , scale is a number based on maximum number in array which  let us to calculate
		//the accurate distance of each spot on the graph
        double scale = (double)(getHeight() - 2*getBorderSpace())/getSignedMax() ;
        
        //calculating x increments
        double xInc = (double)(getWidth() - 2*getBorderSpace())/(getGustData().length-1);
        drawGustSpots(g2, scale, xInc, getHeight(), Color.BLUE);
        drawGustLines(g2, scale, xInc, getHeight(), Color.RED);
        printExtraInfo(g2,scale);
	}
	
	private void drawAverages(Graphics2D g2,double scale , Color windColor , Color gustColor)
	{
		
		Paint oldp = g2.getPaint();
		
		//draw gust average
		g2.setColor(gustColor);
		g2.draw(new Line2D.Double(getBorderSpace(), getHeight() - getBorderSpace() - getAverageGustSpeed() * scale, 
				getWidth() - getBorderSpace(),  getHeight() - getBorderSpace() -getAverageGustSpeed() * scale));

		//draw wind average
		g2.setColor(windColor);
		g2.draw(new Line2D.Double(getBorderSpace(), getHeight() - getBorderSpace() - getAverageWindSpeed() * scale, 
				getWidth() - getBorderSpace(),  getHeight() - getBorderSpace() -getAverageWindSpeed() * scale));
		
		g2.setPaint(oldp);
		
	}
	
	
	protected void printExtraInfo(Graphics2D g2,double scale )
	{
		
		drawString(g2, this.UNIT, getBorderSpace()-30, getBorderSpace()-15,Color.red.brighter(),new Font("Arial",Font.ITALIC ,10));
		drawString(g2, this.AVERAGEWIND + new DecimalFormat("###.##").format(getAverageWindSpeed()) 
        		+  " "  + this.UNIT  +
        		" / " + this.AVERAGEGUST + new DecimalFormat("###.##").format(getAverageGustSpeed()) + " " + this.UNIT ,
        		getBorderSpace(), getHeight() - 5,Color.red.brighter(),new Font("Arial",Font.ITALIC ,12));
		
		
		
        if(windBuilder.dataBigLabel == "Gust")
        {
        	Paint oldP = g2.getPaint();
        	Color windColor = Color.green.darker();
        	
        	g2.setPaint(windColor);
        	drawString(g2, "WIND",getWidth() - 180, getBorderSpace()-12,windColor,new Font("Arial",Font.ITALIC ,10));
        	g2.drawLine(getWidth() - 150 , getBorderSpace() -15, getWidth() -130, getBorderSpace() -15);
        	
        	drawString(g2, "GUST", getWidth() - 120, getBorderSpace()-12, Color.RED, new Font("Arial",Font.ITALIC ,10));
        	g2.setPaint(oldP);
        	g2.drawLine(getWidth() - 90 , getBorderSpace() -15, getWidth() -70, getBorderSpace() -15);
        	
        	drawAverages(g2,scale , windColor , (Color) oldP);
        }
        else
        {
        	Paint oldP = g2.getPaint();
        	Color gustColor =Color.green.darker();
        	
        	g2.setPaint(gustColor);
        	drawString(g2, "GUST",getWidth() - 180, getBorderSpace()-12,gustColor,new Font("Arial",Font.ITALIC ,10));
        	g2.drawLine(getWidth() - 150 , getBorderSpace() -15, getWidth() -130, getBorderSpace() -15);
        	
        	drawString(g2, "WIND", getWidth() - 120, getBorderSpace()-12, Color.RED, new Font("Arial",Font.ITALIC ,10));
        	g2.setPaint(oldP);
        	g2.drawLine(getWidth() - 90 , getBorderSpace() -15, getWidth() -70, getBorderSpace() -15);
        	
        	drawAverages(g2, scale , (Color)oldP , gustColor);
        }
	}
	
	protected void drawGustSpots(Graphics2D g2, double scale, double xInc,
			int height, Color color)
	{
				
		g2.setPaint(color);
    	
        for(int i = 0; i < getGustData().length; i++)
        {
        	
            double x = getBorderSpace() + i*xInc;
            
            double y = height - getBorderSpace() - scale*(getGustDataAtIndex(i)+0);
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
        
	}
	
	
	protected void drawGustLines(Graphics2D g2, double scale, double xInc,
			int height, Color color) 
	{
		
		 g2.setPaint(color);
		        for(int i =0 ; i < getGustData().length-1; i++) 
		        {
		        	double x1 = getBorderSpace() + i*xInc;
		            double y1 = height  - getBorderSpace() - scale*(getGustDataAtIndex(i)+0);
		            double x2 = getBorderSpace() + (i+1)*xInc;
		            double y2 = height  - getBorderSpace() - scale*(getGustDataAtIndex(i+1)+0);
		            g2.draw(new Line2D.Double(x1, y1 , x2, y2));
		        }
	}


	@Override
	public void calculateBounds(float[] data)
	{
		//for calculating an upper bound we need
		//to calculate a median of all numbers that we have
		//and add that median to the last place of the array
		//but during plot the graph we don't need to draw
		//the last element in array . As that point only use for 
		//indicating a boundry and doesn't have any other use
		//and we need to take into account about the number of elements
		//inside drawspot() and drawlines()
		
		//first we will set data with raw data to 
		//make ourselves able to have access to 
		//getsigned/unsigned min/max
		
		setData(data);
		float[] dataBound = new float[data.length +2];
		int i=0;
		
		//lower bound will be set here 
		//I set it to a constant value
		
		
		//since we need to start the lower bound from 0 we can use below line
		dataBound[i++] = getUnsignedMin() - 0;
		
		for(int j=0;j<dataBound.length-2;j++)
		{
			dataBound[i++] = data[j];
		}
		
		//the upper bound will be set here and again this will be a constant value
		dataBound[i] = getUnsignedMax() + GRAPH_BOUND ;
		//after injecting the boundries we will calculate the min/max agian
		//with new data
		setData(dataBound);
		calculateStepsAndGaps();

		
	}
	
	/**
     * Using this method to plot spots on the graph
     *  NOTE : always add lower bound at index=0 and upper bound at last index
     *  since this function will start iterating from element=1 not element=0 and will
     *  iterating to element -2 
     * @param g2 
     * 
     * @param scale  the scale should calculated based on number of weather incidents
     * 
     * @param xInc scale number based on numbers of weather spots
     * 
     * @param height height of the form or frame
     *  
     * @param color 
     */
    protected void drawSpots(Graphics2D g2, double scale , double xInc ,int height , Color color )
    {
    	
    	g2.setPaint(color);

    	for(int i = 0; i < getOriginalUnsignedData().length; i++)
        {
        	
            double x = getBorderSpace() + i*xInc;
            double y = height - getBorderSpace() - scale*(getOriginalUnsignedData()[i]+0);
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
    }
    
    /**
     * Using this method to plot lines between each weather spot
     *  NOTE : always add lower bound at index=0 and upper bound at last index
     *  since this function will start iterating from element=1 not element=0 and will
     *  iterating to element -2 
     * @param g2 
     * 
     * @param scale  the scale should calculated based on number of weather incidents
     * 
     * @param xInc scale number based on numbers of weather spots
     * 
     * @param height height of the form or frame
     *  
     * @param color 
     */
    protected void drawLines(Graphics2D g2 , double scale , double xInc , int height , Color color   )
    {
    	 g2.setPaint(color);
    	 for(int i = 0 ; i < getOriginalUnsignedData().length-1; i++)
	        {
	        	double x1 = getBorderSpace() + i*xInc;
	        	double y1 = height  - getBorderSpace() - scale* (getOriginalUnsignedData()[i]+0);
	            double x2 = getBorderSpace() + (i+1)*xInc;
	            double y2 = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i+1]+0);
	            g2.draw(new Line2D.Double(x1, y1 , x2, y2));
	        }
    	 
    }
	
	
}
