package edu.alvin.guice.bind;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import edu.alvin.guice.interfaces.BindDemo;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.bind.AliensBindingModule.BindDemoImplA;
import static edu.alvin.guice.bind.AliensBindingModule.BindDemoImplB;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

/**
 * Test binding different classes to one interface with different alien names.
 */
public class AliensBindingModuleTest {
    private Injector injector;

    @Before
    public void setup() {
        // create new injector instance.
        injector = Guice.createInjector(new AliensBindingModule());
    }

    /**
     * Test get instance which is bind with 'BindDemo' interface with alien name 'A'.
     */
    @Test
    public void test_getInstanceByAlienNameA() {
        // Get instance of interface by alien name 'A'
        BindDemo bindDemo = injector.getInstance(Key.get(BindDemo.class, Names.named("A")));

        // Make sure the type of instance is 'BindDemoImplA'
        assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplA.class));
        logger(this).debug(bindDemo.getClass().getCanonicalName());
    }

    /**
     * Test get instance which is bind with 'BindDemo' interface with alien name 'B'.
     */
    @Test
    public void test_getInstanceByAlienNameB() {
        // Get instance of interface by alien name 'B'
        BindDemo bindDemo = injector.getInstance(Key.get(BindDemo.class, Names.named("B")));

        // Make sure the type of instance is 'BindDemoImplB'
        assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplB.class));
        logger(this).debug(bindDemo.getClass().getCanonicalName());
    }
}
