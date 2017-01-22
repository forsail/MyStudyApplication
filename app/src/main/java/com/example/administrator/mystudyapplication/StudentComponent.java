package com.example.administrator.mystudyapplication;

import dagger.Component;

/**
 * Created by Administrator on 2016/12/21.
 */
@PerActivity
@Component(modules = StudentModule.class,dependencies = BaseComponent.class)
public interface StudentComponent {
    void inject(MainActivity mainActivity);
}
