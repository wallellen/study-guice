package edu.alvin.guice.bind;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import edu.alvin.guice.interfaces.BindDemo;

/**
 * A module that bind a interface to a provider callback method.
 * This method knows how to create the instance of certain interface type.
 */
public class ProviderBindingModule extends AbstractModule {
    public static final String VALUE = "Test";

    @Override
    protected void configure() {
        // Bind the interface type to a provider method
        // The provider method return a instance of class 'BindDemoImpl' witch implements the interface
        bind(BindDemo.class).toProvider(CacheProvider.class);
    }

    public static class CacheProvider implements Provider<BindDemo> {
        private final ThreadLocal<BindDemo> LOCAL = new ThreadLocal<>();

        @Override
        public BindDemo get() {
            BindDemo bindDemo = LOCAL.get();
            if (bindDemo == null) {
                bindDemo = new BindDemoImpl(VALUE);
                LOCAL.set(bindDemo);
            }
            return bindDemo;
        }
    }

    /**
     * A class implements the 'BindDemo' interface.
     */
    public static class BindDemoImpl implements BindDemo {
        private String value;

        public BindDemoImpl(String value) {
            this.value = value;
        }

        @Override
        public String test() {
            return value;
        }
    }
}
