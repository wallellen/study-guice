package edu.alvin.guice.singleton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class SingletonValue {
    private String value;

    @Inject
    public SingletonValue(@Named("Value") String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void showValue() {
        System.out.println("The value is " + value);
    }
}
