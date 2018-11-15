package com.orderbook.client;

import com.orderbook.core.SimpleOrderBook;
import com.orderbook.util.PriceGenerator;
import com.orderbook.util.QtyGenerator;

public class OrderBookClient {
    private final static int MAX_RANGE_FOR_RANDOM_NUMBER = 4096;

    OrderBookClient() { }

    public void simulateOrders() {

        SimpleOrderBook simpleOrderBook = new SimpleOrderBook();
        simpleOrderBook.addBuyOrder(9.9, 1000);
        simpleOrderBook.addBuyOrder(9.8, 3000);
        simpleOrderBook.addBuyOrder(9.7, 5000);

        simpleOrderBook.addSellOrder(10.1, 3000);
        simpleOrderBook.addSellOrder(10.2, 3000);
        simpleOrderBook.addSellOrder(10.3, 10000);

        simpleOrderBook.printOrderBook();

        simpleOrderBook.addNewOrder(10.5, 17000, true);

        simpleOrderBook.printOrderBook();

        simpleOrderBook.addNewOrder(9.8, 60000, false);

        simpleOrderBook.printOrderBook();

        simpleOrderBook.addNewOrder(9.8, 55000, true);

        simpleOrderBook.printOrderBook();

        simpleOrderBook.addNewOrder(1, 5000, false);

        simpleOrderBook.printOrderBook();

        simpleOrderBook.printMatchedOrders();

    }

    public void simulateHighLoad() throws InterruptedException {
        SimpleOrderBook simpleOrderBook = new SimpleOrderBook();
        PriceGenerator priceGenerator = new PriceGenerator(MAX_RANGE_FOR_RANDOM_NUMBER);
        QtyGenerator qtyGenerator = new QtyGenerator(MAX_RANGE_FOR_RANDOM_NUMBER);

        long start = System.currentTimeMillis();
        int maxOrderLimit = 1000000;
        for (int i = 0; i < maxOrderLimit; i++) {

            if(i % 100000 == 0) {
                System.out.println(i + " orders placed");
            }

            if (priceGenerator.nextPrice() > 50) {
                double price = priceGenerator.nextPrice();
                int qty = qtyGenerator.nextQty();
                simpleOrderBook.addNewOrder(price, qty, true);
            } else {
                double price = priceGenerator.nextPrice();
                int qty = qtyGenerator.nextQty();
                simpleOrderBook.addNewOrder(price, qty, false);
            }

        }

        long end = System.currentTimeMillis();
        long timeElapsed = end-start;
        System.out.println("Total orders placed :"+maxOrderLimit );
        System.out.println("Time taken " +timeElapsed+"ms");
        simpleOrderBook.clearOrderBook();

    }

    public static void main(String[] args) throws InterruptedException {
        OrderBookClient orderBookClient = new OrderBookClient();
        orderBookClient.simulateOrders();
        orderBookClient.simulateHighLoad();
    }
}
