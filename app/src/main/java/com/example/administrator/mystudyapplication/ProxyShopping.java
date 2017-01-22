package com.example.administrator.mystudyapplication;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ProxyShopping implements Shopping {
    Shopping shopping;

    public ProxyShopping(Shopping shopping) {
        this.shopping = shopping;
    }

    @Override
    public Object[] doShopping(long money) {
        long cost = (long) (money * 0.5);
        System.out.println(String.format("花了%s块钱", cost));

        Object[] things = shopping.doShopping(cost);

        if (things != null && things.length > 0) {
            things[0] = "掉包了";
        }
        return things;
    }
}
