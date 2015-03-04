package com.nbmlm.housekeeping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 2015/3/4.
 */
public class OrderManager {
    public static final int ORDER_TYPE_HOUSE_KEEPING=0;
    public static final int ORDER_TYPE_DRY_CLEAN=1;
    public static final int ORDER_TYPE_LEATHER_PROTECTION=2;

    private static final OrderManager sOrdermanager = new OrderManager();
    private List<Order> mOrderList;

    private OrderManager(){
        mOrderList = new ArrayList<Order>();
    }
    public static OrderManager getInstance(){
        return sOrdermanager;
    }
    public List<Order> getOrders(){
        return mOrderList;
    }
    public void addOrder(Order order){
        mOrderList.add(order);
    }
    public void removeOrder(Order order){
        mOrderList.remove(order);
    }
}
