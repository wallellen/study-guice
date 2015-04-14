package edu.alvin.guice.bind;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.alvin.guice.interfaces.BindDemo;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.bind.ConstructorBindingModule.BindDemoImpl;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

/**
 * Test binding a constructor of class to an interface.
 */
public class ConstructorBindModuleTest {

    private Injector injector;

    @Before
    public void setup() throws Exception {
        // Create injector instance
        injector = Guice.createInjector(new ConstructorBindingModule());
    }

    @Test
    public void test() throws Exception {
        // Get instance of interface
        BindDemo bindDemo = injector.getInstance(BindDemo.class);
        // Make sure the type of instance is 'BindDemoImpl'
        assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImpl.class));
        logger(this).debug(bindDemo.getClass().getCanonicalName());

        // Get value from instance
        String expected = bindDemo.test();
        // Make sure the value is equals to the expected value
        assertThat(expected, is(ConstructorBindingModule.VALUE));
        logger(this).debug(expected);
    }
}
