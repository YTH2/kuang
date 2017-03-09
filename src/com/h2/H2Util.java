package com.h2;

import java.util.TreeMap;

public class H2Util
{
	/*
	 * 得到X的和 power代表幂
	 * 
	 */
	public static long getSumX(TreeMap<Integer, Double> map, int power)
	{
		long sum = 0;

		if (map.size() > 0)
		{
			for (Integer value : map.keySet())
			{
				sum += Math.pow(value.intValue(), power);
			}
		}

		return sum;
	}

	/*
	 * 得到Y的和 power代表幂
	 * 
	 */
	public static double getSumY(TreeMap<Integer, Double> map, int power)
	{
		double sum = 0;

		if (map.size() > 0)
		{
			for (Integer value : map.keySet())
			{
				sum += Math.pow(map.get(value), power);
			}
		}

		return sum;
	}

	/*
	 * 得到X*Y的和
	 * 
	 */
	public static double getSumXY(TreeMap<Integer, Double> map)
	{
		double sum = 0;

		if (map.size() > 0)
		{
			for (Integer value : map.keySet())
			{
				sum += value.intValue() * map.get(value);
			}
		}

		return sum;
	}
}
