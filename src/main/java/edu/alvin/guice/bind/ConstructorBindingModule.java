package edu.alvin.guice.bind;

import com.google.inject.Binder;
import com.google.inject.Module;
import edu.alvin.guice.interfaces.BindDemo;

import java.lang.reflect.Constructor;

/**
 * Binding constructor of class to an interface.
 */
public class ConstructorBindingModule implements Module {
    public static String VALUE = "test-ConstructorBindingModule";

    @Override
    public void configure(Binder binder) {
        try {
            // bind a constructor of class to it's interface
            Constructor<BindDemoImpl> constructor = BindDemoImpl.class.getConstructor(String.class);
            binder.bind(BindDemo.class).toConstructor(constructor);

            // bind the string value to 'String' type
            binder.bind(String.class).toInstance(VALUE);
        } catch (NoSuchMethodException e) {
            binder.addError(e);
        }
    }

    /**
     * Implement class
     */
    public static class BindDemoImpl implements BindDemo {
        private String value;

        /**
         * This constructor is used to inject value.
         */
        public BindDemoImpl(String value) {
            this.value = value;
        }

        @Override
        public String test() {
            return value;
        }
    }
}
