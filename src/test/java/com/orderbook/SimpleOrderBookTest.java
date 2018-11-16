package com.orderbook;

import com.orderbook.core.SimpleOrderBook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class SimpleOrderBookTest {
/*
    void getOrderBook();
    void getAllAsk();
    void getAllBid();
    void getAskAtLevel(int level);
    void getBidAtLevel(int level);
    void addNewOrder(double price, int qty, boolean isBuy);
    void getMatchedOrders();
    void clearOrderBook();

    testBuyLevels()
    testSellLevels()
 */

    private  SimpleOrderBook simpleOrderBook;

    @Before
    public void createOrderBook() {
        simpleOrderBook = new SimpleOrderBook();
        simpleOrderBook.addBuyOrder(4.91, 1000);
        simpleOrderBook.addBuyOrder(4.81, 3000);
        simpleOrderBook.addBuyOrder(4.71, 5000);

        simpleOrderBook.addSellOrder(5.11, 3000);
        simpleOrderBook.addSellOrder(5.21, 3000);
        simpleOrderBook.addSellOrder(5.31, 10000);

    }

    @Test
    public void testBuySideOrders(){
        simpleOrderBook.addBuyOrder(4.61, 7000);
        Assert.assertEquals(4, simpleOrderBook.getBuySize());
        Assert.assertEquals(16000, simpleOrderBook.getBuyOrderQty());
    }

    @Test
    public void testSellSideOrders(){
        simpleOrderBook.addSellOrder(5.41, 7000);
        Assert.assertEquals(4, simpleOrderBook.getSellSize());
        Assert.assertEquals(23000, simpleOrderBook.getSellOrderQuantity());
    }

    @Test
    public void testShouldMatchOrder(){
        simpleOrderBook.addNewOrder(5.51, 17000, true);
        Assert.assertEquals(0, simpleOrderBook.getSellSize()); //all sell order should match
        Assert.assertEquals(4, simpleOrderBook.getBuySize());

        Assert.assertEquals(10000, simpleOrderBook.getBuyOrderQty()); //remaining buy orders
        Assert.assertEquals(0, simpleOrderBook.getSellOrderQuantity());

        simpleOrderBook.addNewOrder(4.81, 60000, false);

        Assert.assertEquals(1, simpleOrderBook.getSellSize());
        Assert.assertEquals(1, simpleOrderBook.getBuySize());

        Assert.assertEquals(5000, simpleOrderBook.getBuyOrderQty());
        Assert.assertEquals(55000, simpleOrderBook.getSellOrderQuantity());

        simpleOrderBook.addNewOrder(4.81, 55000, true);

        Assert.assertEquals(0, simpleOrderBook.getSellSize());
        Assert.assertEquals(1, simpleOrderBook.getBuySize());

        Assert.assertEquals(5000, simpleOrderBook.getBuyOrderQty());
        Assert.assertEquals(0, simpleOrderBook.getSellOrderQuantity());

        simpleOrderBook.addNewOrder(1, 5000, false);

        //all orders should have been executed now
        Assert.assertEquals(0, simpleOrderBook.getSellSize());
        Assert.assertEquals(0, simpleOrderBook.getBuySize());

        Assert.assertEquals(0, simpleOrderBook.getBuyOrderQty());
        Assert.assertEquals(0, simpleOrderBook.getSellOrderQuantity());

    }

    @Test
    public void testOrdersAtLevel(){

        Assert.assertEquals(4.81, simpleOrderBook.getBuyAtLevel(2).getKey().doubleValue(), 0.01);
        Assert.assertEquals(3000, simpleOrderBook.getBuyAtLevel(2).getValue().intValue());

        Assert.assertEquals(5.31, simpleOrderBook.getSellAtLevel(3).getKey().doubleValue(), 0.01);
        Assert.assertEquals(10000, simpleOrderBook.getSellAtLevel(3).getValue().intValue());

        //Add new orders that changes level for buy and sell order book
        simpleOrderBook.addNewOrder(4.89, 5000, true);
        simpleOrderBook.addNewOrder(5.29, 5000, false);

        Assert.assertEquals(4.89, simpleOrderBook.getBuyAtLevel(2).getKey().doubleValue(), 0.01);
        Assert.assertEquals(5000, simpleOrderBook.getBuyAtLevel(2).getValue().intValue());

        Assert.assertEquals(5.29, simpleOrderBook.getSellAtLevel(3).getKey().doubleValue(), 0.01);
        Assert.assertEquals(5000, simpleOrderBook.getSellAtLevel(3).getValue().intValue());

    }

    @Test
    public void testOrderAtNonExistingLevel(){
        Map.Entry<Double, Integer> entryBuy = simpleOrderBook.getBuyAtLevel(10000);
        Assert.assertNull("Verify no any buy order at given level", entryBuy);

        Map.Entry<Double, Integer> entrySell = simpleOrderBook.getBuyAtLevel(10000);
        Assert.assertNull("Verify no any sell order at given level", entrySell);

    }
}
