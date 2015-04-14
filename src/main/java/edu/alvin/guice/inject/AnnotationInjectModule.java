package edu.alvin.guice.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import edu.alvin.guice.annotations.A;
import edu.alvin.guice.annotations.B;
import edu.alvin.guice.annotations.C;

/**
 * A module that bind three values with three different annotations.
 */
public class AnnotationInjectModule extends AbstractModule {
    public static final String VALUE_A = "A", VALUE_B = "B", VALUE_C = "C";

    @Override
    protected void configure() {
        // bind String value with annotation 'A'
        bind(String.class).annotatedWith(A.class).toProvider(() -> VALUE_A);
        // bind String value with annotation 'B'
        bind(String.class).annotatedWith(B.class).toInstance(VALUE_B);
    }

    /**
     * A provider to get String value with annotation 'C'.
     */
    @C
    @Provides
    public String valueAProvider() {
        return VALUE_C;
    }

    /**
     * Inject the value by field with annotation 'A'.
     */
    public static class InjectByField {
        @A
        @Inject
        private String value;

        public String getValue() {
            return value;
        }
    }

    /**
     * Inject the value by constructor with annotation 'B'.
     */
    public static class InjectByConstructor {
        private String value;

        @Inject
        public InjectByConstructor(@B String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Inject the value by set method with annotation 'C'.
     */
    public static class InjectByMethod {
        private String value;

        public String getValue() {
            return value;
        }

        @Inject
        public void setValue(@C String value) {
            this.value = value;
        }
    }
}
