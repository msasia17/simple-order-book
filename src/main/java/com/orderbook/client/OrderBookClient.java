package com.orderbook.client;

import com.orderbook.core.SimpleOrderBook;

public class OrderBookClient {
    //private final static Logger LOGGER = Logger.getLogger(OrderBookClient.class.getSimpleName());
    private SimpleOrderBook simpleOrderBook;

     OrderBookClient(SimpleOrderBook simpleOrderBook) {
         this.simpleOrderBook = simpleOrderBook;
     }

    public void placeOrders() {
        System.out.println("*****STARTING ORDER BOOK SIMULATOR*****");
        simpleOrderBook.addBuyOrder(9.9, 1000);
        simpleOrderBook.addBuyOrder(9.8, 3000);
        simpleOrderBook.addBuyOrder(9.7, 5000);

        simpleOrderBook.addSellOrder(10.1, 3000);
        simpleOrderBook.addSellOrder(10.2, 3000);
        simpleOrderBook.addSellOrder(10.3, 10000);

        simpleOrderBook.getOrderBook();
        simpleOrderBook.getSellAtLevel(3);
        simpleOrderBook.getBuyAtLevel(2);

        simpleOrderBook.addNewOrder(10.5, 17000, true);

        simpleOrderBook.getOrderBook();

        simpleOrderBook.addNewOrder(9.8, 60000, false);

        simpleOrderBook.getOrderBook();

        simpleOrderBook.addNewOrder(9.8, 55000, true);

        simpleOrderBook.getOrderBook();

        simpleOrderBook.addNewOrder(1, 5000, false);

        simpleOrderBook.getOrderBook();

        simpleOrderBook.getMatchedOrders();
        simpleOrderBook.clearOrderBook();
    }

    public static void main(String[] args) {
        SimpleOrderBook simpleOrderBook = new SimpleOrderBook();
        OrderBookClient orderBookClient = new OrderBookClient(simpleOrderBook);
        orderBookClient.placeOrders();
     }
}
