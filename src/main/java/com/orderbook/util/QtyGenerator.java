package com.orderbook.util;


public class QtyGenerator {

	private int maxRange;
	private int[] quantity;
	private int counter = 0;

	public QtyGenerator(int maxRange){
		this.maxRange = maxRange;
		this.quantity = new int[maxRange];
	}


	public void initialize() {
		for(int i=0; i<maxRange; i++) {
			quantity[i] = getRandomIntAsQty();
		}
	}
	
	private int getRandomIntAsQty()
	{
		return (int)(Math.random() * 10000);
	}
	
	public int nextQty() {
		if(counter == maxRange-1) {
			counter = 0;
		}
		else {
			++counter;
		}
		return quantity[counter];
	}

}
