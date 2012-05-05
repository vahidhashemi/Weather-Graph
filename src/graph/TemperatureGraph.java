package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

/**
 * This class is suitable for plotting temperature
 * graphs.
 * It will draw only one graph suitable for deriving
 * any other class which derived from this class should be aware that
 * this class will draw a linegraph in between and it always hass +ve / -ve 
 * bound range if you don't need any bound expanded range you should 
 * implement your own setdata()/calculateBounds()/calculateStepsAndGaps()
 * 
 * This class is able to draw from -ve to +ve
 * 
 *  basically this class is the main class for drawing graph
 *  as it is much completed than Graph
 * @author Seyed Vahid Hashemi 
 *
 */
public class TemperatureGraph extends Graph
{
	private float[] YGuides;
    private float[] YNames;
    
    private float unsignedMin;
    private float unsignedMax;

    private float[] signedData; // this array uses for original data + boundaries
    private float signedMin; 
    private float signedMax;
    
    
    private float unsignedData[];
    private int guidesNumber=1; //default value for guides number
    private boolean drawSpot = true;
    private boolean drawLine = true;
    
    private float averageTemperature;
    private static String UNIT = "C";
    private static String AVERAGE = "Average temprature : ";
    
    
    public void setAverageTemperature(float t)
    {
    	averageTemperature = t;
    }
    
    public float getAverageTemperature()
    {
    	return averageTemperature;
    }
    
    /**
   	 * @return the signedMin
   	 */
       protected float getSignedMin() {
   		return signedMin;
   	}


   	/**
   	 * getting signed min from signedMax()
   	 */
   	protected void setSignedMin() {
   		//since we need to get min number in array 
       	//first we can find the max number in array so we make ourselves sure that
       	//there won't be any number higher than that then we will start the loop to replace the
       	//max num with lowest number
       	float min = getSignedMax(); 
           for(int i = 0; i < getSignedData().length; i++) {
               if(getSignedDataAtIndex(i) < min)
                   min = getSignedDataAtIndex(i);
           }
           this.signedMin = min;
   	}


   	/**
   	 * @return the signedMax
   	 */
   	protected float getSignedMax() {
   		return signedMax;
   	}


   	/**
   	 * get getSignedData() and calculate signed max and store it into signedMax
   	 * you can access to singedMax using getSignedMax()
   	 * 
   	 */
   	protected void setSignedMax() 
   	{
   		
   	     float max = -Float.MAX_VALUE;
   	        for(int i = 0; i < getSignedData().length; i++) 
   	        {
   	            if(getSignedDataAtIndex(i) > max)
   	                max =getSignedDataAtIndex(i);
   	        }
   	        this.signedMax = max;
   	}
   	
       
       /**
        * NOTE : signedData should be defined before using this function
        * for definition use setSignedData()
        * @param i index
        * @return will return a float number
        * @throws ArrayIndexOutOfBoundsException
        */
   	protected float getSignedDataAtIndex(int i) throws ArrayIndexOutOfBoundsException
       {
       	return signedData[i];
       }
       
       /**
        * NOTE : signedData should be defined before using this function
        * for definition use setSignedData() 
        * @param value get value to replace 
        * @param i index
        * @throws ArrayIndexOutOfBoundsException
        */
       protected void setSignedDataAtIndex(float value , int i) throws ArrayIndexOutOfBoundsException
       {
       	signedData[i] = value;
       }
       
       
       /**
   	 * @return the signedData
   	 */
       protected float[] getSignedData() {
   		return signedData;
   	}

   	/**
   	 * @param signedData the signedData to set
   	 */
   	protected void setSignedData(float[] signedData) {
   		this.signedData = signedData;
   	}
    
    protected float[] getYGuides()
    {
    	return YGuides;
    }
	/**
	 * @return the drawLine
	 */
	public boolean isDrawLine() {
		return drawLine;
	}

	/**
	 * @param drawLine the drawLine to set
	 */
	public void setDrawLine(boolean drawLine) {
		this.drawLine = drawLine;
	}
    
    /**
	 * @return the drawSpot
	 */
	public boolean isDrawSpot() {
		return drawSpot;
	}

	/**
	 * @param drawSpot the drawSpot to set
	 */
	public void setDrawSpot(boolean drawSpot) {
		this.drawSpot = drawSpot;
	}
    /**
     * set the number of elements in YGuides
     * @param int n
     */
	
	public void setYGuidesAtIndex(float value , int i) throws ArrayIndexOutOfBoundsException
	{
		YGuides[i] = value;
	}
	
	public void setYNamesAtIndex(float value , int i) throws ArrayIndexOutOfBoundsException
	{
		YNames[i] = value;
	}
	
	public float getYNamesAtIndex(int i) throws ArrayIndexOutOfBoundsException
	{
		return YNames[i];
	}
	
	public float getYGuidesAtIndex(int i) throws ArrayIndexOutOfBoundsException
	{
		return YGuides[i];
	}
	
    private void setYGuides(int n)
    {
    	YGuides = new float[n];
    }
    
	/**
	 * set the number of elements in YNames
	 * @param int n
	 */
    protected void setYNames(int n)
    {
    	YNames = new float[n];
    }
    
    protected float[] getYNames()
    {
    	
    	return YNames;
    }
    /**
	 * @return the guidesNumber
	 */
	public int getGuidesNumber() {
		return guidesNumber;
	}

	/**
	 * @param guidesNumber the guidesNumber to set
	 */
	public void setGuidesNumber(int guidesNumber) {
		this.guidesNumber = guidesNumber;
	}

	
    
    /**
     * 
     * @param float[] d
     */
    protected void setUnsignedData(float[] d)
    {
    	unsignedData = null;
    	unsignedData = d.clone();
    }
    
    /**
     * 
     * @return float[]
     */
    protected float[] getUnsignedData()
    {
    	return unsignedData;
    }
    
    
    /**
	 * @return the unsignedMin
	 */
	protected float getUnsignedMin() {
		return unsignedMin;
	}


	/**
	 * getting unsigned min from unsignedMax()
	 */
	protected void setUnsignedMin() {
		//since we need to get min number in array 
    	//first we can find the max number in array so we make ourselves sure that
    	//there won't be any number higher than that then we will start the loop to replace the
    	//max num with lowest number
    	float min = getUnsignedMax(); 
        for(int i = 0; i < getUnsignedData().length; i++) {
            if(getUnsignedData()[i] < min)
                min = getUnsignedData()[i];
        }
        this.unsignedMin = min;
	}


	/**
	 * @return the unsignedMax
	 */
	protected float getUnsignedMax() {
		return unsignedMax;
	}


	/**
	 * @param unsignedMax the unsignedMax to set
	 */
	protected void setUnsignedMax() 
	{
		  
		float max = -Float.MAX_VALUE;
	        for(int i = 0; i < getUnsignedData().length; i++) 
	        {
	            if(getUnsignedData()[i] > max)
	                max = getUnsignedData()[i];
	        }
	        this.unsignedMax = max;
	}


	
	//-------------------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor Basic construct only get array of data
	 * the guides number will be 1 by default and it will
	 *  draw spots and lines to by default
	 * @param data
	 */
	public TemperatureGraph(float[] data)
	{
		this(data, 1, true, true);
	}
	
	public String unit;
	public String averageTemp;
	
	/**
	 * Constructor
	 * @param data get data as float[]
	 * @param guidenumber number of horizontal guides (
	 * @param drawpoint
	 * @param drawlines
	 */
	public TemperatureGraph(float[] data  ,int guidenumber , boolean drawpoint , boolean drawlines)
	{
		super();

		setYGuides(guidenumber);
    	setYNames(guidenumber);
    	setGuidesNumber(guidenumber);
    	setDrawSpot(drawpoint);
    	setDrawLine(drawlines);
		
    	setOriginalData(data);
    	float min = getOriginalMinData();
    	float[] tempUnsigned = data.clone();
    	
    	if (min <0)
    	{
	    	for(int i=0;i<data.length;i++)
	    	{
	    		
	    		tempUnsigned[i] = data[i] + Math.abs(min);
	    	}
    	}
		setOriginalUnsignedData(tempUnsigned);
    	calculateBounds(data);
	}
	
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
		dataBound[i++] = getUnsignedMin() - GRAPH_BOUND;
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
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//calculating scale , scale is a number based on maximum number in array which  let us to calculate
        //the accurate distance of each spot on the graph
        double scale = (double)(getHeight() - 2*getBorderSpace())/getSignedMax() ;
        double xInc = (double)(getWidth() - 2*getBorderSpace())/(getOriginalData().length-1);
        //draw Y guides
        drawYGuides(g2, scale, getHeight(), getWidth(), Color.gray.brighter());
        // Draw lines.
        if(isDrawLine())
        {
	        drawLines(g2, scale, xInc, getHeight(), getLineColor());
        }
        // Mark data points.
        if (isDrawSpot())
        {
        	drawSpots(g2, scale, xInc, getHeight() , getSpotColor());
        }
        printExtraInfo(g2,scale);
     
	}
	
	protected void printExtraInfo(Graphics2D g2,double scale)
	{
        drawString(g2, this.UNIT, getBorderSpace()-30,
				getBorderSpace()-15,Color.red.brighter(),new Font("Arial",Font.ITALIC ,10));
        drawString(g2, this.AVERAGE +  new DecimalFormat("##.##").format(getAverageTemperature()) + " " +
				this.UNIT, getBorderSpace(), getHeight() - 5,Color.red.brighter(),new Font("Arial",Font.ITALIC ,12));
	}
	
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
    				YGuides[counter] = 0;
    			else
    				YGuides[counter] = i;
				
				YNames[counter] = YGuides[counter] - Math.abs(getUnsignedMin());
			}
			else
			{
				YGuides[counter] =  i;
				YNames[counter] = YGuides[counter];
			}
			counter++;
		}
	}
	
	/**
     * config signed/unsigned data array and their corresponding min/max
     * @param d 
     */
    protected void setData(float[] d)
    {
    	
    	//in order to calculate and start to plotting the lines on the graph
    	//we need to store the data into two arrays 1-signed array 2-unsigned array
    	
    	setUnsignedData(d);
    	setUnsignedMax();
    	setUnsignedMin();
    	
   	 	float[] tempData = new float[unsignedData.length];
   	 
   	 		//if there are negative numbers as we need to shift the whole numbers to positive values we have to 
   	 		//check all numbers and add the ABS(minimum) to all the numbers
   	 		if (getUnsignedMin() < 0)
   	 		{
		    	for (int i=1;i<d.length;i++)
		    	{
		    		tempData[i] = unsignedData[i] + Math.abs(getUnsignedMin());
		    	}
   	 		}
   	 		//if there is not any negative numbers so we don't need to do any shifting
   	 		else
   	 			tempData = d.clone();
   	 		//store the tempData array into signedData[]
   	 		setSignedData(tempData);
   	 		setSignedMax();
   	 		setSignedMin();
   	 		
   	 	
    }
    
	
	 /**
     * using this method for drawing Y guides 
     * @param g2
     * @param scale the scale should calculated based on number of weather incidents
     * @param height height of the form or frame
     * @param width width of the form or frame
     * @param color
     */
    public void drawYGuides(Graphics2D g2, double scale , int height , int width ,Color color )
    {
    	
    	g2.setPaint(color);
    	int i=0;
    	for(;i< YGuides.length;i++)
        {
        	//eliminate all numbers more than the maximum number in array
        	//as we don't need to draw those guides
        	if (YGuides[i] > getSignedMax())
        		break;
        	
        	double x1 = getBorderSpace() ;
        	double y1 = height - getBorderSpace() - scale * YGuides[i];
        	double x2 = width - getBorderSpace() ;
        	g2.draw(new Line2D.Double(x1,y1,x2,y1));
        	drawString(g2, String.valueOf(YNames[i]),0, (float)y1);
        	
        }
        //drawing the top and last number
        double topY = height - getBorderSpace() - scale * getSignedMax();
        g2.draw(new Line2D.Double(getBorderSpace(), topY , width - getBorderSpace(),topY));
        drawString(g2, String.valueOf(getUnsignedMax()), 0, (float) topY );         
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
            double y;
            //if the minimun point is in the range of bound range 
            //then we have to consider about it and follow the following
            //algorithm for calculating the points 
            if (getOriginalMinData() > -1 && getOriginalMinData() <= GRAPH_BOUND)
            	y = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i]+(GRAPH_BOUND - getOriginalMinData()));
            else if (getOriginalMinData() < 0)
            	y = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i]+GRAPH_BOUND);
            else
            	y = height - getBorderSpace() - scale*(getOriginalUnsignedData()[i]);
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
	        	 //if the minimun point is in the range of bound range 
	            //then we have to consider about it and follow the following
	            //algorithm for calculating the points 
	        	
	        	//calculating  x1,y1 of line
	        	double y1=0;
	        	if (getOriginalMinData() > -1 && getOriginalMinData() <= GRAPH_BOUND)
	            	y1 = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i]+(GRAPH_BOUND - getOriginalMinData()));
	            else if (getOriginalMinData() < 0)
	            	y1 = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i]+GRAPH_BOUND);
	            else
	            	y1 = height - getBorderSpace() - scale*(getOriginalUnsignedData()[i]);
	        	//calculating x2,y2 of line			
	            double x2 = getBorderSpace() + (i+1)*xInc;
	            double y2=0; 
	            
	            if (getOriginalMinData() > -1 && getOriginalMinData() <= GRAPH_BOUND)
	            	y2 = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i+1]+(GRAPH_BOUND - getOriginalMinData()));
	            else if (getOriginalMinData() < 0)
	            	y2 = height  - getBorderSpace() - scale*(getOriginalUnsignedData()[i+1]+GRAPH_BOUND);
	            else
	            	y2 = height - getBorderSpace() - scale*(getOriginalUnsignedData()[i+1]);
	            
	            g2.draw(new Line2D.Double(x1, y1 , x2, y2));
	        }
    	 
    }
}
