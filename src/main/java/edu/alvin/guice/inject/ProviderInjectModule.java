package edu.alvin.guice.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

/**
 * A module contains a provider witch provide a String value.
 */
public class ProviderInjectModule extends AbstractModule {
    public static final String VALUE = "ProviderInjectModule";

    @Override
    protected void configure() {
    }

    @Provides
    public String valueProvider() {
        return VALUE;
    }

    /**
     * Inject the value by field.
     */
    public static class InjectByField {
        @Inject
        private String value;

        public String getValue() {
            return value;
        }
    }

    /**
     * Inject the value by constructor.
     */
    public static class InjectByConstructor {
        private String value;

        @Inject
        public InjectByConstructor(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Inject the value by method.
     */
    public static class InjectByMethod {
        private String value;

        public String getValue() {
            return value;
        }

        @Inject
        public void setValue(String value) {
            this.value = value;
        }
    }
}
