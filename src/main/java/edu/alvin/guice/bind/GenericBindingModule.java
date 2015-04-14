package edu.alvin.guice.bind;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * A module to bind generic interface to generic class.
 */
public class GenericBindingModule extends AbstractModule {
    public static final String CLASS = "CLASS";
    public static final String BINDER = "BINDER";
    public static final String PROVIDER = "PROVIDER";

    public static final String[] VALUES_BIND = {"test1-1", "test1-2", "test1-3"};
    public static final String[] VALUES_PROV = {"test2-1", "test2-2", "test2-3"};

    @Override
    protected void configure() {
        // bind the 'LocalSet' class to 'Set' interface, with 'String' generic argument
        bind(new TypeLiteral<Set<String>>() {
        })
                .annotatedWith(Names.named(CLASS))
                .to(new TypeLiteral<HashSet<String>>() {
                });

        // bind the instance of 'Set' to 'Set' interface
        bind(new TypeLiteral<Set<String>>() {
        })
                .annotatedWith(Names.named(BINDER))
                .toInstance(new HashSet<>(asList(VALUES_BIND)));
    }

    /**
     * To provide a string set instance
     */
    @Provides
    @Named(PROVIDER)
    public Set<String> provideStringSet() {
        return new HashSet<>(asList(VALUES_PROV));
    }
}
