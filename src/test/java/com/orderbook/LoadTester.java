package com.orderbook;

import com.orderbook.core.SimpleOrderBook;
import com.orderbook.util.PriceGenerator;
import com.orderbook.util.QtyGenerator;

public class LoadTester {
    private final static int MAX_RANGE_FOR_RANDOM_NUMBER = 4096;

    public void simulateHighLoad() {
        System.out.println("***STARTING ORDER BOOK SIMULATOR FOR HIGH LOAD(100000 ORDERS)*****");

        SimpleOrderBook simpleOrderBook = new SimpleOrderBook();
        PriceGenerator priceGenerator = new PriceGenerator(MAX_RANGE_FOR_RANDOM_NUMBER);
        priceGenerator.initialize();

        QtyGenerator qtyGenerator = new QtyGenerator(MAX_RANGE_FOR_RANDOM_NUMBER);
        qtyGenerator.initialize();

        long start = System.currentTimeMillis();
        int maxOrderLimit = 1000000;

        for (int i = 0; i < maxOrderLimit; i++) {
            double price = priceGenerator.nextPrice();
            int qty = qtyGenerator.nextQty();

            if (priceGenerator.nextPrice() > 50) {
                simpleOrderBook.addNewOrder(price, qty, true);
            } else {
                simpleOrderBook.addNewOrder(price, qty, false);
            }

        }
        long end = System.currentTimeMillis();
        long timeElapsed = end-start;
        double latency = ((double)timeElapsed) / maxOrderLimit;

        System.out.println(maxOrderLimit +" orders placed in :"+timeElapsed+"ms | Latency(ms) for per order "+latency );

        simpleOrderBook.clearOrderBook();

    }

    public static void main(String[] args) throws InterruptedException {
        LoadTester loadTester = new LoadTester();
        loadTester.simulateHighLoad();
    }
}
