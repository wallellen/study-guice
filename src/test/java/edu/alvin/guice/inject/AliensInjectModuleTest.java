package edu.alvin.guice.inject;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.inject.AliensInjectModule.InjectByConstructor;
import static edu.alvin.guice.inject.AliensInjectModule.InjectByField;
import static edu.alvin.guice.inject.AliensInjectModule.InjectByMethod;
import static edu.alvin.guice.inject.AliensInjectModule.VALUE_A;
import static edu.alvin.guice.inject.AliensInjectModule.VALUE_B;
import static edu.alvin.guice.inject.AliensInjectModule.VALUE_C;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AliensInjectModuleTest {

    @Inject
    private InjectByField injectByField;

    @Inject
    private InjectByConstructor injectByConstructor;

    @Inject
    private InjectByMethod injectByMethod;

    @Inject
    @Named(AliensInjectModule.VALUE_C)
    private Provider<String> stringProvider;

    @Before
    public void setup() {
        Guice.createInjector(new AliensInjectModule()).injectMembers(this);
    }

    /**
     * Test inject by field of class, with alien name 'A'
     */
    @Test
    public void test_injectByField() {
        // Make sure the instance is injected into current object
        assertThat(injectByField, is(notNullValue()));

        String value = injectByField.getValue();
        // Make sure the value is certain value
        assertThat(value, is(VALUE_A));
        logger(this).debug(value);
    }

    /**
     * Test inject by constructor of class, with alien name 'B'
     */
    @Test
    public void test_injectByConstructor() {
        // Make sure the instance is injected into current object
        assertThat(injectByConstructor, is(notNullValue()));

        String value = injectByConstructor.getValue();
        // Make sure the value is certain value
        assertThat(value, is(VALUE_B));
        logger(this).debug(value);
    }

    /**
     * Test inject by set method of class, with alien name 'B'
     */
    @Test
    public void test_injectByMethod() {
        // Make sure the instance is injected into current object
        assertThat(injectByMethod, is(notNullValue()));

        String value = injectByMethod.getValue();
        // Make sure the value is certain value
        assertThat(value, is(VALUE_C));
        logger(this).debug(value);
    }

    @Test
    public void test_stringProvider() {
        assertThat(stringProvider.get(), is(AliensInjectModule.VALUE_C));
    }
}
