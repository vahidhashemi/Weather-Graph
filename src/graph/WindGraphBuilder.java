package graph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is helper builder for WindGraph class
 * since in wind graph we need to draw two linegraph
 * and since we calculate the range of YGuides from the first parameter
 * so here we will change the first parameter to the biggest range
 * and then WindGraph will draw both lines flawlessly
 * 
 * @author Seyed Vahid Hashemi 
 *
 */
public class WindGraphBuilder 
{

	private float[] dataBig;
	private float[] dataSmall;
	
	protected String dataBigLabel="";
	protected String dataSmallLabel="";
	protected boolean drawPoints;
	protected boolean drawLines;
	protected int guidesNumber;
	
	/**
	 * Constructor : get two arrays of data , the data should be in sequence of windData then gustData
	 * 
	 * @param winddata float[]
	 * @param gustdata float[]
	 * @param guidenumber number of guide lines
	 * @param drawlines draw lines between point
	 * @param drawpoints plot the point on the graph
	 */
	public WindGraphBuilder(float[] winddata , float[] gustdata ,
			int guidenumber,boolean drawlines , boolean drawpoints)
	{
		if (getMaxNumber(winddata) > getMaxNumber(gustdata))
		{
			dataBig = winddata.clone();
			dataBigLabel = "Wind";
			dataSmall = gustdata.clone();
			dataSmallLabel = "Gust";
		}
		else
		{
			dataBig = gustdata.clone();
			dataBigLabel = "Gust";
			dataSmall = winddata.clone();
			dataSmallLabel = "Wind";
		}
		drawLines = drawlines;
		drawPoints = drawpoints;
		guidesNumber = guidenumber;
	}
	
	/**
	 * Calculating maximum number of a float array
	 * @param num 
	 * @return float maximum number in array
	 */
	private float getMaxNumber(float[] num)
	{
		ArrayList<Float> nums = new ArrayList<Float>();
		for (int i=0; i<num.length;i++ )
			nums.add(Float.valueOf(num[i]));
		Collections.sort(nums);
		
		return (nums.get(nums.size()-1).floatValue());
	}
	
	protected float[] getDataBig()
	{
		return dataBig;
	}
	
	protected float[] getDataSmall()
	{
		return dataSmall;
	}
}
