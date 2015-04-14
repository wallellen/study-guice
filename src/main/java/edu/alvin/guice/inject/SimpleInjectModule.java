package edu.alvin.guice.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

/**
 * A module bind the String instance to a provider.
 */
public class SimpleInjectModule extends AbstractModule {
    public static final String VALUE = "SimpleInjectModule";

    @Override
    protected void configure() {
        // To provide the String value for inject
        bind(String.class).toProvider(() -> VALUE);
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
     * Inject the value by set method.
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
