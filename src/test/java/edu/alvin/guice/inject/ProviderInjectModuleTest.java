package edu.alvin.guice.inject;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.inject.ProviderInjectModule.InjectByConstructor;
import static edu.alvin.guice.inject.ProviderInjectModule.InjectByField;
import static edu.alvin.guice.inject.ProviderInjectModule.InjectByMethod;
import static edu.alvin.guice.inject.ProviderInjectModule.VALUE;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test inject the value by provider method in module.
 */
public class ProviderInjectModuleTest {

    @Inject
    private InjectByField injectByField;

    @Inject
    private InjectByConstructor injectByConstructor;

    @Inject
    private InjectByMethod injectByMethod;

    @Before
    public void setup() {
        // Create the injector instance and inject somethings into current object
        Guice.createInjector(new ProviderInjectModule()).injectMembers(this);
    }

    /**
     * Test inject by field.
     */
    @Test
    public void test_injectByField() {
        // Make sure the instance is injected into current object
        assertThat(injectByField, is(notNullValue()));

        String value = injectByField.getValue();
        // Make sure the certain value is injected into the instance
        assertThat(value, is(VALUE));
        logger(this).debug(value);
    }

    /**
     * Test inject by constructor.
     */
    @Test
    public void test_injectByConstructor() {
        // Make sure the instance is injected into current object
        assertThat(injectByConstructor, is(notNullValue()));

        String value = injectByConstructor.getValue();
        // Make sure the certain value is injected into the instance
        assertThat(value, is(VALUE));
        logger(this).debug(value);
    }

    /**
     * Test inject by method
     */
    @Test
    public void test_injectByMethod() {
        // Make sure the instance is injected into current object
        assertThat(injectByMethod, is(notNullValue()));

        String value = injectByMethod.getValue();
        // Make sure the certain value is injected into the instance
        assertThat(value, is(VALUE));
        logger(this).debug(value);
    }
}
