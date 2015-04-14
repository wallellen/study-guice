package edu.alvin.guice.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * A module that bind three values with three different alien names.
 */
public class AliensInjectModule extends AbstractModule {
    public static final String VALUE_A = "A", VALUE_B = "B", VALUE_C = "C";

    @Override
    protected void configure() {
        // bind String value with alien name 'A'
        bind(String.class).annotatedWith(Names.named(VALUE_A)).toProvider(() -> VALUE_A);
        // bind String value with alien name 'B'
        bind(String.class).annotatedWith(Names.named(VALUE_B)).toInstance(VALUE_B);
    }

    /**
     * A provider to get String value with alien name 'C'.
     */
    @Named(VALUE_C)
    @Provides
    public String valueAProvider() {
        return VALUE_C;
    }

    /**
     * Inject the value by field with alien name 'A'.
     */
    public static class InjectByField {
        @Named(VALUE_A)
        @Inject
        private String value;

        public String getValue() {
            return value;
        }
    }

    /**
     * Inject the value by constructor with alien name 'B'.
     */
    public static class InjectByConstructor {
        private String value;

        @Inject
        public InjectByConstructor(@Named(VALUE_B) String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Inject the value by set method with alien name 'C'.
     */
    public static class InjectByMethod {
        private String value;

        public String getValue() {
            return value;
        }

        @Inject
        public void setValue(@Named(VALUE_C) String value) {
            this.value = value;
        }
    }
}
