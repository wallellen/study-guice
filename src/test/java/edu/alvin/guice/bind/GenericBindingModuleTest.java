package edu.alvin.guice.bind;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

import static edu.alvin.guice.bind.GenericBindingModule.BINDER;
import static edu.alvin.guice.bind.GenericBindingModule.CLASS;
import static edu.alvin.guice.bind.GenericBindingModule.PROVIDER;
import static edu.alvin.guice.bind.GenericBindingModule.VALUES_BIND;
import static edu.alvin.guice.bind.GenericBindingModule.VALUES_PROV;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class GenericBindingModuleTest {
    private static final Logger LOG = getLogger(GenericBindingModuleTest.class);

    private Injector injector;

    @Before
    public void setup() {
        // Get the instance of injector.
        injector = Guice.createInjector(new GenericBindingModule());
    }

    /**
     * Get instance of generic interface by aliens name 'CLASS'
     */
    @Test
    public void test_getInstanceOfGenericClass() {
        // Get instance of generic 'Set' type, with alien name 'CLASS'
        Set<String> instance = injector.getInstance(Key.get(
                new TypeLiteral<Set<String>>() {
                },
                Names.named(CLASS)));

        // Make sure the type of instance is 'HashSet'
        assertThat(instance.getClass(), typeCompatibleWith(HashSet.class));
        LOG.debug(instance.getClass().getCanonicalName());
    }

    /**
     * Get instance of generic interface by aliens name 'BINDER'
     */
    @Test
    public void test_getInstanceOfGenericTypeWithAlienNameBINDER() {
        // Get instance of generic 'Set' type, with alien name 'BINDER'
        Set<String> instance = injector.getInstance(Key.get(
                new TypeLiteral<Set<String>>() {
                },
                Names.named(BINDER)));

        // Make sure the type of instance is 'HashSet'
        assertThat(instance.getClass(), typeCompatibleWith(HashSet.class));

        // Check the instance, if contains the certain values
        asList(VALUES_BIND).forEach(value -> assertThat(instance.contains(value), is(true)));
        LOG.debug(instance.toString());
    }

    /**
     * Get instance of generic interface by aliens name 'PROVIDER'
     */
    @Test
    public void test_getInstanceOfGenericTypeWithAlienNamePROVIDER() {
        // Get instance of generic 'Set' type, with alien name 'PROVIDER'
        Set<String> instance = injector.getInstance(Key.get(
                new TypeLiteral<Set<String>>() {
                },
                Names.named(PROVIDER)));

        // Make sure the type of instance is 'HashSet'
        assertThat(instance.getClass(), typeCompatibleWith(HashSet.class));

        // Check the instance, if contains the certain values
        asList(VALUES_PROV).forEach(value -> assertThat(instance.contains(value), is(true)));
        LOG.debug(instance.toString());
    }
}
