package com.example.administrator.mystudyapplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/12/21.
 */
@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {
    MyAppication get();
}
