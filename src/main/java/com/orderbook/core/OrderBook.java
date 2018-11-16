package com.orderbook.core;

import java.util.Map;

public interface OrderBook {
    void getOrderBook();
    void getAllAsk();
    void getAllBid();
    Map.Entry<Double, Integer> getSellAtLevel(int level);
    Map.Entry<Double, Integer> getBuyAtLevel(int level);
    void addNewOrder(double price, int qty, boolean isBuy);
    void getMatchedOrders();
    void clearOrderBook();
   }
