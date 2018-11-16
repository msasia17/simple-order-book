package com.orderbook.core;

import com.orderbook.util.MatchedOrder;
import java.util.*;

public class SimpleOrderBook implements OrderBook {
   // private static final Logger LOGGER = Logger.getLogger( SimpleOrderBook.class.getName() );

    private Map<Double, Integer> buyOrders;
    private Map<Double, Integer> sellOrders;
    private List<MatchedOrder> matchedOrders;

    public SimpleOrderBook(){
        sellOrders = new TreeMap<>();
        buyOrders = new TreeMap<>(Comparator.reverseOrder());
        matchedOrders = new ArrayList<>();
    }

    @Override
    public void addNewOrder(double price, int qty, boolean isBuy) {
        if (isBuy) {
            List<Double> sell_side_prices = new ArrayList<>(sellOrders.keySet());
            for (double sell_price : sell_side_prices) {
                if (qty > 0 && price >= sell_price) {
                    int ask_quantity = sellOrders.get(sell_price);
                    if (qty >= ask_quantity) {
                        qty = qty - ask_quantity;
                        MatchedOrder matched_order = new MatchedOrder(sell_price, ask_quantity);
                        matchedOrders.add(matched_order);
                        removeSellOrder(sell_price, ask_quantity);
                    } else {
                        MatchedOrder matched_order = new MatchedOrder(sell_price, qty);
                        matchedOrders.add(matched_order);
                        removeSellOrder(sell_price, qty);
                        qty = 0;
                    }
                }
            }
            if (qty > 0) {
                addBuyOrder(price, qty);
            }

        } else {
            List<Double> buy_side_prices = new ArrayList<>(buyOrders.keySet());
            for (double buy_price : buy_side_prices) {
                if (qty > 0 && price <= buy_price) {
                    int bid_quantity = buyOrders.get(buy_price);
                    if (qty >= bid_quantity) {
                        qty = qty - bid_quantity;
                        MatchedOrder matched_order = new MatchedOrder(buy_price, bid_quantity);
               			matchedOrders.add(matched_order);
				        removeBuyOrder(buy_price, bid_quantity);
                    } else {
                        MatchedOrder matched_order = new MatchedOrder(buy_price, qty);
                     	matchedOrders.add(matched_order);
                        removeBuyOrder(buy_price, qty);
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

    public synchronized void addSellOrder(double price, int quantity) {
        sellOrders.put(price, quantity);
    }

    public synchronized void removeBuyOrder(double price, int quantity) {
        int lastQuantity = buyOrders.get(price);
        if (lastQuantity == quantity) {
            buyOrders.remove(price);
        } else {
            buyOrders.put(price, lastQuantity - quantity);
        }
    }


    public synchronized void removeSellOrder(double price, int quantity) {
        int lastQuantity = sellOrders.get(price);
        if (lastQuantity == quantity) {
            sellOrders.remove(price);
        } else {
            sellOrders.put(price, lastQuantity - quantity);
        }
    }

     /**
     * Method to get all buy quantity by passing min value -2^31. This will give all buy orders
     * @return buy order quantity
     */
    public int getBuyOrderQty() {
        return getBuyOrderQty(Integer.MIN_VALUE);
    }

    /**
     * Method to get all buy quantity by passing a best price.
     * @return buy order quantity
     */

    public int getBuyOrderQty(double bestBid) {
        int bidQuantity = 0;
        for (double price : buyOrders.keySet()) {
            if (price > bestBid) {
                bidQuantity += buyOrders.get(price);
            }
        }
        return bidQuantity;
    }

    /**
     * Method to get all buy quantity by passing max value 2^31-1. This will give all sell orders
     * @return sell order quantity
     */
    public int getSellOrderQuantity() {
        return getSellOrderQuantity(Integer.MAX_VALUE);
    }

    /**
     * Method to get all sell quantity by passing a best price.
     * @return sell order quantity
     */
    public int getSellOrderQuantity(double bestAsk) {
        int askQuantity = 0;
        for (double price : sellOrders.keySet()) {
            if (price < bestAsk) {
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

    @Override
    public void getMatchedOrders() {
        System.out.println("\n______EXECUTED ORDERS______");
        matchedOrders.
                stream()
                .forEach(System.out::println);
    }

    @Override
    public void getOrderBook() {
        System.out.println("\n______ORDER BOOK______\nside | qty@price");
        buyOrders.forEach((price, qty)->System.out.println("BID | "+ qty+" @ "+price));
        sellOrders.forEach((price, qty)->System.out.println("ASK | "+ qty+" @ "+price));
    }

    @Override
    public void getAllAsk() {
        System.out.println("\n______ALL ASK ORDERS______");
        sellOrders.forEach((price, qty)->System.out.println(qty+" @ "+price));
    }

    @Override
    public void getAllBid() {
        System.out.println("\n______ALL BID ORDERS______");
        buyOrders.forEach((price, qty)->System.out.println(qty+" @ "+price));

    }

    public void printSellAtLevel(int level) {
        sellOrders.entrySet().stream()
                .skip(level-1)
                .findFirst()
                .ifPresent(sellOrder->System.out.println("Ask | level-"+level+" | "+sellOrder.getValue()+"@"+sellOrder.getKey()));
    }


    public void printBuyAtLevel(int level) {
        buyOrders.entrySet().stream()
                .skip(level-1)
                .findFirst()
                .ifPresent(buyOrder->System.out.println("Bid | level-"+level+" | "+buyOrder.getValue()+"@"+buyOrder.getKey()));
    }

    @Override
    public Map.Entry<Double, Integer> getBuyAtLevel(int level){
       return buyOrders.entrySet().stream()
                .skip(level-1)
                .findFirst().orElse(null);
    }

    @Override
    public Map.Entry<Double, Integer> getSellAtLevel(int level){
        return sellOrders.entrySet().stream()
                .skip(level-1)
                .findFirst().orElse(null);
    }


    @Override
    public void clearOrderBook() {
        System.out.println("Clearning order book with pending sell["+sellOrders.size()+"] and buy[" + buyOrders.size()+"] orders");
        sellOrders.clear();
        buyOrders.clear();
        matchedOrders.clear();
        System.out.println("Order book is cleared!!!!");
    }

}
