/**
 * 分别计算每个分片的a、b值
 * reduce阶段对a，b值分别取平均值
 */
package com.h2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author hadoop 直线拟合
 */
public class Curve
{

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
	{
		// 固定格式
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		fs.delete(new Path("output"), true);

		Job job = Job.getInstance(conf, "Curve");
		job.setJarByClass(Curve.class);

		job.setMapperClass(CurveMap.class);
		job.setReducerClass(CurveReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		FileInputFormat.addInputPath(job, new Path("input/2311.txt"));
		FileOutputFormat.setOutputPath(job, new Path("output"));

		boolean complete = job.waitForCompletion(true);

		FSDataInputStream fsdi = fs.open(new Path("hdfs://Lenovo:9000/user/hadoop/output/part-r-00000"));
		OutputStream output = new FileOutputStream("/home/hadoop/kuang.txt");
		IOUtils.copyBytes(fsdi, output, 4096, true);

		System.exit(complete ? 0 : 1);
	}

}
