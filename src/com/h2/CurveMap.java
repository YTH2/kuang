package com.h2;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CurveMap extends Mapper<LongWritable, Text, Text, DoubleWritable>
{
	/*
	 * 建立一个treemap结构存储输入的数据;
	 *  map函数完成记录计数和存储数据的功能 
	 *  cleanup函数完成本split数据的计算并输出a和b的值
	 */

	TreeMap<Integer, Double> tree = new TreeMap<Integer, Double>();

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
					throws IOException, InterruptedException
	{
		try
		{
			String[] index = value.toString().split(" ");
			int x = Integer.parseInt(index[0].trim());
			double y = Double.parseDouble(index[1].trim());

			tree.put(x, y);

		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

	@Override
	protected void cleanup(Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
			throws IOException, InterruptedException
	{
		//获取各种参数
		int N=tree.size();//N的值
		//System.out.println("N........"+N);
		
		long sumSquareX=H2Util.getSumX(tree, 2);
		//System.out.println("sumSquareX........"+sumSquareX);
		
		long sumX=H2Util.getSumX(tree, 1);
		//System.out.println("sumX........"+sumX);
		
		double sumY=H2Util.getSumY(tree, 1);
		//System.out.println("sumY........"+sumY);
		
		double sumXY=H2Util.getSumXY(tree);
		//System.out.println("sumXY........"+sumXY);
		
		//获取直线函数的a，b值 ；y=ax+b
		double b=(sumSquareX*sumY-sumX*sumXY)/(N*sumSquareX-Math.pow(sumX, 2));
		double a=(N*sumXY-sumX*sumY)/(N*sumSquareX-Math.pow(sumX, 2));
		
		context.write(new Text("a"), new DoubleWritable(a));
		context.write(new Text("b"), new DoubleWritable(b));
	}
}
