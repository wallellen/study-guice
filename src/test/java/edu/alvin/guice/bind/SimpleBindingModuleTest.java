package edu.alvin.guice.bind;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.alvin.guice.interfaces.BindDemo;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.bind.SimpleBindingModule.BindDemoImpl;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

/**
 * Test the simple binding, binding a interface to it's implemented class.
 */
public class SimpleBindingModuleTest {

    private Injector injector;

    @Before
    public void setup() {
        // Create a injector instance
        injector = Guice.createInjector(new SimpleBindingModule());
    }

    /**
     * Test the binding
     */
    @Test
    public void test() {
        // Get the instance by 'BindDemo' type
        BindDemo instance = injector.getInstance(BindDemo.class);

        // Make sure the instance is match the certain type
        assertThat(instance.getClass(), typeCompatibleWith(BindDemoImpl.class));
        logger(this).debug(instance.getClass().getCanonicalName());
    }
}
