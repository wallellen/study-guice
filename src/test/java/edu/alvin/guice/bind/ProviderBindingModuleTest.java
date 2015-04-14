package edu.alvin.guice.bind;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import edu.alvin.guice.interfaces.BindDemo;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.bind.ProviderBindingModule.BindDemoImpl;
import static edu.alvin.guice.bind.ProviderBindingModule.VALUE;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

/**
 * Test bind interface to a provider callback method
 */
public class ProviderBindingModuleTest {

    private Injector injector;

    @Before
    public void setup() {
        // Create the injector instance
        injector = Guice.createInjector(new ProviderBindingModule());
    }

    /**
     * Test get class instance with binding a provider.
     */
    @Test
    public void test_getBindDemoInstanceByProviderBinding() {
        // Get the instance of 'BindDemo' type
        BindDemo instance = injector.getInstance(BindDemo.class);
        assertThat(instance.getClass(), typeCompatibleWith(BindDemoImpl.class));
        logger(this).debug(instance.getClass().getCanonicalName());

        // Get the value from instance
        String value = instance.test();
        // Make sure the value is a certain value
        assertThat(value, is(VALUE));
        logger(this).debug(value);
    }

    /**
     * Test get provider with binding a provider.
     */
    @Test
    public void test_getBindDemoProviderByProviderBinding() {
        // Get the provider
        Provider<BindDemo> provider = injector.getProvider(BindDemo.class);
        // Get the instance of 'BindDemo' type by the provider
        BindDemo instance = provider.get();
        // Make sure the type of instance is 'BindDemoImpl'
        assertThat(instance.getClass(), typeCompatibleWith(BindDemoImpl.class));
        logger(this).debug(instance.getClass().getCanonicalName());

        // Get the value from instance
        String value = instance.test();
        // Make sure the value is a certain value
        assertThat(value, is(VALUE));
        logger(this).debug(value);
    }
}
