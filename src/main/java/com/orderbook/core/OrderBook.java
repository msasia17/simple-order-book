package com.orderbook.core;

import com.orderbook.util.PricePoint;

import java.util.List;

public interface OrderBook {
    void addNewOrder(double price, int qty, boolean isBuy);
    List<PricePoint<Double, Integer>> getMatchedOrder();
    void printOrderBook();
    void printMatchedOrders();
   }
