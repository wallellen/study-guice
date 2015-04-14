package edu.alvin.guice.bind;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import edu.alvin.guice.annotations.A;
import edu.alvin.guice.annotations.B;
import edu.alvin.guice.interfaces.BindDemo;
import org.junit.Before;
import org.junit.Test;

import static edu.alvin.guice.bind.AnnotationBindingModule.BindDemoImplA;
import static edu.alvin.guice.bind.AnnotationBindingModule.BindDemoImplB;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

public class AnnotationBindingModuleTest {

    private Injector injector;

    @Before
    public void setup() {
        // create new injector instance.
        injector = Guice.createInjector(new AnnotationBindingModule());
    }

    /**
     * Test get instance of 'BindDemo' interface with annotation 'A'
     */
    @Test
    public void test_getInstanceByAnnotationA() {
        // Get instance by annotation A
        BindDemo bindDemo = injector.getInstance(Key.get(BindDemo.class, A.class));

        // Make sure the type of instance is 'BindDemoImplA'
        assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplA.class));
        logger(this).debug(bindDemo.getClass().getCanonicalName());
    }

    /**
     * Test get instance of 'BindDemo' interface with annotation 'B'
     */
    @Test
    public void test_getInstanceByAnnotationB() {
        // Get instance by annotation B
        BindDemo bindDemo = injector.getInstance(Key.get(BindDemo.class, B.class));

        // Make sure the type of instance is 'BindDemoImplB'
        assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplB.class));
        logger(this).debug(bindDemo.getClass().getCanonicalName());
    }
}
