package edu.alvin.guice.bind;

import com.google.inject.AbstractModule;
import edu.alvin.guice.annotations.A;
import edu.alvin.guice.annotations.B;
import edu.alvin.guice.interfaces.BindDemo;

/**
 * A Module to show how to use annotation to mark different class of an interface.
 */
public class AnnotationBindingModule extends AbstractModule {

    @Override
    protected void configure() {
        // bind class to interface with annotation A
        bind(BindDemo.class).annotatedWith(A.class).to(BindDemoImplA.class);
        // bind class to interface with annotation B
        bind(BindDemo.class).annotatedWith(B.class).to(BindDemoImplB.class);
    }

    /**
     * A class
     */
    static class BindDemoImplA implements BindDemo {
        @Override
        public String test() {
            return "";
        }
    }

    /**
     * B class
     */
    static class BindDemoImplB implements BindDemo {
        @Override
        public String test() {
            return "";
        }
    }
}