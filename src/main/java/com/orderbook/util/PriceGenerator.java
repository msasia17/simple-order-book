package com.orderbook.util;

import java.text.DecimalFormat;

public class PriceGenerator {

	private int maxRange;
	private double[] price;
	private int counter = 0;

	public PriceGenerator(int maxRange){
		this.maxRange = maxRange;
		price = new double[maxRange];
	}
	
	public void initialize() {
		for(int i=0; i<maxRange; i++) {
			price[i] = getRandomDoubleAsPrice();
		}
	}

	private double getRandomDoubleAsPrice()
	{
		DecimalFormat format = new DecimalFormat("#.##"); //two decimal precision for this ex.
		return Double.valueOf(format.format(Math.random() * 100));
	}
	
	public double nextPrice() {
		if(counter == maxRange-1) {
			counter = 0;
		}
		else {
			++counter;
		}
		return price[counter];
	}
	
}
