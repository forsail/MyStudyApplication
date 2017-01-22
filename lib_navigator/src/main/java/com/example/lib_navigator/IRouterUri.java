package com.example.lib_navigator;

/**
 * Created by Administrator on 2016/12/29.
 */


public interface IRouterUri {
    @RouterUri(routerUri = "xl://goods:8888/goodsDetail")
    void jumpToGoodsDetail(@RouterParam("goodsId")
                                     String goodsId,
                             @RouterParam("des")
                                     String des);

    @RouterUri(routerUri = "http://sec")
    void jumpToMain();
}
