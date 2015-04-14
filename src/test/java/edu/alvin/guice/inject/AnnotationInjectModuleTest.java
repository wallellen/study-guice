package edu.alvin.guice.inject;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.inject.AnnotationInjectModule.InjectByConstructor;
import static edu.alvin.guice.inject.AnnotationInjectModule.InjectByField;
import static edu.alvin.guice.inject.AnnotationInjectModule.InjectByMethod;
import static edu.alvin.guice.inject.AnnotationInjectModule.VALUE_A;
import static edu.alvin.guice.inject.AnnotationInjectModule.VALUE_B;
import static edu.alvin.guice.inject.AnnotationInjectModule.VALUE_C;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test inject different values by different annotations
 */
public class AnnotationInjectModuleTest {

    @Inject
    private InjectByField injectByField;

    @Inject
    private InjectByConstructor injectByConstructor;

    @Inject
    private InjectByMethod injectByMethod;

    @Before
    public void setup() {
        Guice.createInjector(new AnnotationInjectModule()).injectMembers(this);
    }

    /**
     * Test inject value by field, the value was injected with annotation 'A'
     */
    @Test
    public void test_injectByField() {
        // Make sure the instance has been injected into current object
        assertThat(injectByField, is(notNullValue()));

        // Get the value from injected instance
        String value = injectByField.getValue();
        // Make sure the value is certain value
        assertThat(value, is(VALUE_A));
        logger(this).debug(value);
    }

    /**
     * Test inject value by constructor, the value was injected with annotation 'B'
     */
    @Test
    public void test_injectByConstructor() {
        // Make sure the instance has been injected into current object
        assertThat(injectByConstructor, is(notNullValue()));

        // Get the value from injected instance
        String value = injectByConstructor.getValue();
        // Make sure the value is certain value
        assertThat(value, is(VALUE_B));
        logger(this).debug(value);
    }

    /**
     * Test inject value by set method, the value was injected with annotation 'C'
     */
    @Test
    public void test3_injectByMethod() {
        // Make sure the instance has been injected into current object
        assertThat(injectByMethod, is(notNullValue()));

        // Get the value from injected instance
        String value = injectByMethod.getValue();
        // Make sure the value is certain value
        assertThat(value, is(VALUE_C));
        logger(this).debug(value);
    }
}
