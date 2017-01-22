package com.example.administrator.mystudyapplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/12/21.
 */

@Module
public class StudentModule {

    @Provides
    public Student provideStudent() {
        Student student = new Student();
        student.setName("lx");
        return student;
    }
}
