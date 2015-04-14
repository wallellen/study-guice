package edu.alvin.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import edu.alvin.guice.annotations.A;
import edu.alvin.guice.interfaces.BindDemo;

public class InstallerModule extends AbstractModule {
    public static final String MODULE_NAME = "MODULE_NAME";

    public static final String MODULE_A = "MODULE_A";
    public static final String MODULE_B = "MODULE_B";

    // The value to means which module would be install
    private String moduleName;

    // Set the module name to choose different child module instances
    public InstallerModule(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    protected void configure() {
        switch (moduleName) {
        case MODULE_A:
            install(new AbstractModule() {
                @Override
                protected void configure() {
                    // bind the interface to class 'BindDemoImplA'
                    bind(BindDemo.class).to(BindDemoImplA.class);
                }

                @A
                @Provides
                public String providerValue() {
                    return MODULE_A;
                }
            });
            break;
        case MODULE_B:
            install(new AbstractModule() {
                @Override
                protected void configure() {
                    // bind the interface to class 'BindDemoImplB'
                    bind(BindDemo.class).to(BindDemoImplB.class);
                }

                @A
                @Provides
                public String providerValue() {
                    return MODULE_B;
                }
            });
            break;
        default:
            throw new IllegalArgumentException("moduleName");
        }
    }

    @Provides
    @Named(MODULE_NAME)
    public String provideModuleName() {
        return moduleName;
    }

    /**
     * The class implement BindDemo interface and inject the value with annotation.
     */
    public static class BindDemoImplA implements BindDemo {
        private String value;

        @Inject
        public BindDemoImplA(@A String value) {
            this.value = value;
        }

        @Override
        public String test() {
            return value;
        }
    }

    /**
     * The class implement BindDemo interface and inject the value with annotation.
     */
    public static class BindDemoImplB implements BindDemo {
        private String value;

        @Inject
        public BindDemoImplB(@A String value) {
            this.value = value;
        }

        @Override
        public String test() {
            return value;
        }
    }
}
