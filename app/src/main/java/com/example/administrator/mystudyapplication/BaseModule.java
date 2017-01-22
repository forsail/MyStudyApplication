package com.example.administrator.mystudyapplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/12/21.
 */
@Module
public class BaseModule {
    @Singleton
    @Provides
    public MyAppication get() {
        return MyAppication.get();
    }

}
