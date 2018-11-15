package com.orderbook.util;

public class PricePoint<Double, Integer> {

	  private final Double price;
	  private final Integer qty;

	  public PricePoint(Double price, Integer qty) {
	    this.price = price;
	    this.qty = qty;
	  }

	  public Double getPrice() { return price; }
	  public Integer getQty() { return qty; }

	  @Override
	  public int hashCode() {
	  	return price.hashCode() ^ qty.hashCode();
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (o == null) return false;
	    if (!(o instanceof PricePoint))
	    	return false;
	    PricePoint<Double, Integer> pp = (PricePoint<Double, Integer>) o;
	    return this.price.equals(pp.getPrice()) &&
	           this.qty.equals(pp.getQty());
	  }

	}