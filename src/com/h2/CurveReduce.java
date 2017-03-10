package com.h2;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CurveReduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable>
{
	@Override
	protected void reduce(Text key, Iterable<DoubleWritable> values,
			Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context) throws IOException, InterruptedException
	{
		int count=0;
		double sum=0;
		//求a或者b的平均值
		for (DoubleWritable value : values)
		{
			sum+=value.get();
			count++;
		}
		
		context.write(key, new DoubleWritable((double)Math.round((sum/count)*100)/100));
		
	}
}
