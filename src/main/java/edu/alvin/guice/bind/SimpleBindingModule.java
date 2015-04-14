package edu.alvin.guice.bind;

import com.google.inject.Binder;
import com.google.inject.Module;
import edu.alvin.guice.interfaces.BindDemo;

/**
 * A module that bind a interface to it's implemented class
 */
public class SimpleBindingModule implements Module {

    @Override
    public void configure(Binder binder) {
        // bind interface to it's implemented class
        binder.bind(BindDemo.class).to(BindDemoImpl.class);
    }

    /**
     * A class witch implemented 'BindDemo' interface
     */
    public static class BindDemoImpl implements BindDemo {
        @Override
        public String test() {
            return "";
        }
    }
}