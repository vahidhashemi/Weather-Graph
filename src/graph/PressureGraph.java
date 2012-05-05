package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;


/**
 * This Graph will draw pressure units
 * as pressure unit start from nearly 870 to 1080
 * which means the numbers of are big enough and if we want to start
 * plotting from (0,0) the result won't be readable by eyes
 * for this matter we override the calculateStepsAndGaps() from 
 * TemperatureGraph class and we start to draw from 
 * minimunm pressure-graphboud to maximum pressure + graph bound
 * @author Seyed Vahid Hashemi 
 *
 */
public class PressureGraph extends TemperatureGraph
{
	private float min_pressure;
	private float averagePressure;
	private String pressureTrend;
	
    private static String UNIT = "hPa";
    private static String AVERAGE = "Average Pressure : ";
    
    
    public void setPressureTrend(String t)
    {
    	pressureTrend = t;
    }
    
    public String getPressureTrend()
    {
    	return pressureTrend;
    }
    
    public void setAveragePressure(float t)
    {
    	averagePressure = t;
    }
    
    public float getAveragePressure()
    {
    	return averagePressure;
    }
    
	
	public PressureGraph(float[] data , int guidenumber , boolean drawpoint , boolean drawlines) 
	{
		
		super(data, guidenumber, drawpoint, drawlines);
		calculateData(data);
		

	}
	
	@Override
	protected void printExtraInfo(Graphics2D g2,double scale)
	{
		
		drawString(g2, this.UNIT, getBorderSpace()-30,
				getBorderSpace()-15,Color.red.brighter(),new Font("Arial",Font.ITALIC ,10));
        drawString(g2, this.AVERAGE + getAveragePressure()  + " Pressure Trend : " + getPressureTrend() ,
        		getBorderSpace(), getHeight() - 5,Color.red.brighter(),new Font("Arial",Font.ITALIC ,12));
	}
	
	private void calculateData(float[] data)
	{
		//since data in pressure are big usually bigger than 900
		//here we will find the minimum number and reduce the actual amount
		//to something like real_pressure_number - min_pressure_number
		//but for plotting it will use the real_pressure_number
		
		//for labling Y guides we will label the original pressures not the one
		//that we 
				
		setData(data);
		setOriginalData(data);
		setOriginalUnsignedData(data);
		min_pressure =  getOriginalMinData();
		for(int i=0;i<data.length;i++)
		{
			
			getOriginalData()[i] = data[i] - min_pressure;
			
		}
		setOriginalUnsignedData(getOriginalData().clone());
		
		setData(data);
		calculateBounds(data);
		calculateStepsAndGaps();
		
	}
	
	@Override
	public void calculateStepsAndGaps()
	{
		float range;
		
		if(getUnsignedMin() < 0)
			range = getSignedMax() - getSignedMin();
		else
			range = getSignedMax();
		double ysteps = range / getGuidesNumber() ; 
		
		ysteps = Math.ceil(ysteps);
		int counter=0;
		for (int i=0;i<getGuidesNumber() * ysteps;i= (int) (i+ysteps) )
		{
			
			if (getUnsignedMin() <0)
			{
				if (counter == 0)
    				setYGuidesAtIndex(0, counter); 
    			else
    				setYGuidesAtIndex(i, counter); 
				
				setYNamesAtIndex(getYGuidesAtIndex(counter) -
						Math.abs(getUnsignedMin()) + min_pressure , counter);  
			}
			else
			{
				setYGuidesAtIndex(i, counter); 
				setYNamesAtIndex(getYGuidesAtIndex(counter) + min_pressure, counter); 
			}
			counter++;
		}
	}
	
	
	/**
     * using this method for drawing Y guides 
     * @param g2
     * @param scale the scale should calculated based on number of weather incidents
     * @param height height of the form or frame
     * @param width width of the form or frame
     * @param color
     */
	@Override
    public void drawYGuides(Graphics2D g2, double scale , int height , int width ,Color color )
    {
    	g2.setPaint(color);
    	int i=0;
    	for(;i< getYGuides().length;i++)
        {
        	//eliminate all numbers more than the maximum number in array
        	//as we don't need to draw those guides
        	if (getYGuidesAtIndex(i) > getSignedMax())
        		break;
        	
        	double x1 = getBorderSpace() ;
        	double y1 = height - getBorderSpace() - scale * getYGuidesAtIndex(i);
        	double x2 = width - getBorderSpace() ;
        	g2.draw(new Line2D.Double(x1,y1,x2,y1));
        	drawString(g2, String.valueOf(getYNamesAtIndex(i)),0, (float)y1);
        	
        }
        //drawing the top and last number
        double topY = height - getBorderSpace() - scale * getSignedMax();
        g2.draw(new Line2D.Double(getBorderSpace(), topY , width - getBorderSpace(),topY));
        drawString(g2, String.valueOf(getYNamesAtIndex(i-1) + 2*GRAPH_BOUND ), 0, (float) topY );
        
    }
}
