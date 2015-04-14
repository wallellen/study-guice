package edu.alvin.guice.singleton;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SingletonInterfaceImpl implements SingletonInterface {
    private String value;

    @Inject
    public SingletonInterfaceImpl(@Named("Value") String value) {
        this.value = value;
    }

    @Override
    public void showValue() {
        System.out.println("The value is " + value);
    }
}