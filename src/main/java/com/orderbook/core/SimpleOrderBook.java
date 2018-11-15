package com.orderbook.core;

import com.orderbook.util.PricePoint;

import java.util.*;
import java.util.logging.Logger;

public class SimpleOrderBook implements OrderBook {
    private static final Logger LOGGER = Logger.getLogger( SimpleOrderBook.class.getName() );

    private Map<Double, Integer> buyOrders;
    private Map<Double, Integer> sellOrders;
    private List<PricePoint<Double, Integer>> matchedOrders;

    public SimpleOrderBook(){
        sellOrders = new TreeMap<>(); //TreeMap to sort
        buyOrders = new TreeMap<>(new Comparator<Double>() {  //To reverse sort for bids
            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });
        matchedOrders = new ArrayList<>();
    }

    @Override
    public void addNewOrder(double price, int qty, boolean isBuy) {
        if (isBuy) {
            Set<Double> ask_prices = sellOrders.keySet();
            List<Double> ask_prices_list = new ArrayList<>(ask_prices);
            for (double ask_price : ask_prices_list) {
                if (qty > 0 && price >= ask_price) {
                    int ask_quantity = sellOrders.get(ask_price);
                    if (qty >= ask_quantity) {
                        qty = qty - ask_quantity;
                        removeSellOrder(ask_price, ask_quantity);
                    } else {
                        removeSellOrder(ask_price, qty);
                        qty = 0;
                    }
                }
            }
            if (qty > 0) {
                addBuyOrder(price, qty);
            }

        } else {
            Set<Double> bid_prices = buyOrders.keySet();
            List<Double> bid_prices_list = new ArrayList<>(bid_prices);
            for (double bid_price : bid_prices_list) {
                if (qty > 0 && price <= bid_price) {
                    int bid_quantity = buyOrders.get(bid_price);
                    if (qty >= bid_quantity) {
                        qty = qty - bid_quantity;
				        removeBuyOrder(bid_price, bid_quantity);
                    } else {
                        removeBuyOrder(bid_price, qty);
                        qty = 0;
                    }
                }

            }

            if (qty > 0) {
                addSellOrder(price, qty);
            }
        }

    }


    public synchronized void addBuyOrder(double price, int quantity) {
        buyOrders.put(price, quantity);
    }

    public synchronized void removeBuyOrder(double price, int quantity) {
        int lastQuantity = buyOrders.get(price);
        if (lastQuantity == quantity) {
            buyOrders.remove(price);
        } else {
            buyOrders.put(price, lastQuantity - quantity);
        }
    }

    public synchronized void addSellOrder(double price, int quantity) {
        sellOrders.put(price, quantity);
    }

    public synchronized void removeSellOrder(double price, int quantity) {
        int lastQuantity = sellOrders.get(price);
        if (lastQuantity == quantity) {
            sellOrders.remove(price);
        } else {
            sellOrders.put(price, lastQuantity - quantity);
        }
    }



    @Override
    public List<PricePoint<Double, Integer>> getMatchedOrder() {
        return matchedOrders;
    }

    @Override
    public void printMatchedOrders() {
        System.out.println("");
        System.out.println("____ exec ____ ");
        matchedOrders.stream().
                forEach(e->System.out.println(e.getQty()+"@"+e.getPrice()));
        System.out.println("______________ ");
    }

    @Override
    public void printOrderBook() {
        System.out.println("");
        Set<Double> bid_prices = buyOrders.keySet();
        List<Double> bid_prices_list = new ArrayList<>(bid_prices);

        System.out.println("____ bid ____ ");
        for (Double bid_price : bid_prices_list) {
            System.out.println(buyOrders.get(bid_price) + " @ " + bid_price);
        }

        Set<Double> ask_prices = sellOrders.keySet();
        System.out.println("____ ask ____ ");
        for (double ask_price : ask_prices) {
            System.out.println(sellOrders.get(ask_price) + " @ " + ask_price);
        }
        System.out.println("_____________ ");
    }


    //Below methods are not functional requirements but added to help unit testing
    public int getBuyOrderQty() {
        return getBuyOrderQty(Integer.MIN_VALUE);
    }

    public int getBuyOrderQty(double bestPrice) {
        int bidQuantity = 0;
        for (double price : buyOrders.keySet()) {
            if (price > bestPrice) {
                bidQuantity += buyOrders.get(price);
            }
        }

        return bidQuantity;
    }

    public int getSellOrderQuantity() {
        return getSellOrderQuantity(Integer.MAX_VALUE);
    }

    public int getSellOrderQuantity(double bestPrice) {
        int askQuantity = 0;
        for (double price : sellOrders.keySet()) {
            if (price < bestPrice) {
                askQuantity += sellOrders.get(price);
            }
        }
        return askQuantity;
    }

    public int getSellSize() {
        return sellOrders.size();
    }

    public int getBuySize() {
        return buyOrders.size();
    }


    public void clearOrderBook() {
        System.out.println("Sell Size = " + sellOrders.size());
        System.out.println("Buy Size = " + buyOrders.size());
        System.out.println("Matched orders = " + matchedOrders.size());
        sellOrders.clear();
        buyOrders.clear();
        matchedOrders.clear();
        System.out.println("Order book is cleared!!!!");
    }

}
