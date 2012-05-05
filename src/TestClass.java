

import java.awt.Container;

import javax.swing.JFrame;

import graph.*;

/**
 * A little demonstration of how to use weather graphs
 * By slight changes its possible to convert this weather graph to whatever
 * you want.
 * 
 * Feel free to add some changes to this project and please commit to repository
 * @author Seyed Vahid Hashemi
 *
 */
public class TestClass extends JFrame
{
	
	public static void main(String[] a)
	{
		TestClass c = new TestClass();
	}
	
	public TestClass()
	{
		setTitle("test form for weather graphs");
		setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainContentPage = this.getContentPane();
		float [] data = new float[5];
		float [] data2 = new float[5];
		
		data[0] = 40;
		data[1] = 02;
		data[2] = 0;
		data[3] = 30;
		data[4] = 01;
		
		data2[0] = 4;
		data2[1] = 2;
		data2[2] = 0;
		data2[3] = 0;
		data2[4] = 1;
		
		int guidenumber = 3;
		boolean drawpoint = true;
		boolean drawlines = true;
		
		TemperatureGraph tg = new TemperatureGraph(data, guidenumber, drawpoint, drawlines);
		//for using pressure graph your data needs to be in hpa scale
		PressureGraph pg = new PressureGraph(data2, guidenumber, drawpoint, drawlines);
		WindGraphBuilder wgb = new WindGraphBuilder(data, data2, guidenumber, drawlines, drawpoint);
		WindGraph wg = new WindGraph(wgb);
		
		this.add(pg);
		this.setVisible(true);
		
	}

}
