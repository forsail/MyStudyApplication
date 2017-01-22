package com.example.administrator.mystudyapplication;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ShoppingImpl implements Shopping {
    @Override
    public Object[] doShopping(long money) {
        System.out.println("买东西");
        System.out.println(String.format("花了%s块钱", money));
        return new Object[]{"鞋子", "衣服", "零食"};
    }
}
