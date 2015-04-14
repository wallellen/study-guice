package edu.alvin.guice.bind;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import edu.alvin.guice.interfaces.BindDemo;

/**
 * A Module to show how to use annotation to mark different class of an interface.
 */
public class AliensBindingModule extends AbstractModule {

    @Override
    protected void configure() {
        // bind class to interface with alien name A
        bind(BindDemo.class).annotatedWith(Names.named("A")).to(BindDemoImplA.class);

        // bind class to interface with alien name B
        bind(BindDemo.class).annotatedWith(Names.named("B")).to(BindDemoImplB.class);
    }

    /**
     * A class. mark as alien name 'A'
     */
    public static class BindDemoImplA implements BindDemo {
        @Override
        public String test() {
            return "";
        }
    }

    /**
     * B class. mark as alien name 'B'
     */
    public static class BindDemoImplB implements BindDemo {
        @Override
        public String test() {
            return "";
        }
    }
}
