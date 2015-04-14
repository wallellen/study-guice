package edu.alvin.guice.module;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import edu.alvin.guice.interfaces.BindDemo;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static edu.alvin.guice.module.InstallerModule.BindDemoImplA;
import static edu.alvin.guice.module.InstallerModule.BindDemoImplB;
import static edu.alvin.guice.module.InstallerModule.MODULE_A;
import static edu.alvin.guice.module.InstallerModule.MODULE_B;
import static edu.alvin.guice.module.InstallerModule.MODULE_NAME;
import static edu.alvin.log.TestLogger.logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Test inject instance by different modules.
 */
public class InstallerModuleTest {

    // Inject the instance of interface
    @Inject
    private BindDemo bindDemo;

    // Inject the module name by alien name 'MODULE_NAME'
    @Inject
    @Named(MODULE_NAME)
    private String moduleName;

    @Before
    public void setup() {
        // Get module name by random
        String moduleName = new Random().nextBoolean() ? MODULE_A : MODULE_B;
        // Get injector instance and inject things into current object with different module name
        Guice.createInjector(new InstallerModule(moduleName)).injectMembers(this);
    }

    /**
     * Test inject value by different modules
     */
    @Test
    public void test() {
        // Check the different instance by module name
        switch (moduleName) {
        case MODULE_A:
            // Make sure the type of instance is class 'BindDemoImplA'
            assertThat(bindDemo.getClass(), is(typeCompatibleWith(BindDemoImplA.class)));
            // Make sure the value of instance is 'MODULE_A'
            assertThat(bindDemo.test(), is(MODULE_A));
            break;
        case MODULE_B:
            // Make sure the type of instance is class 'BindDemoImplB'
            assertThat(bindDemo.getClass(), is(typeCompatibleWith(BindDemoImplB.class)));
            // Make sure the value of instance is 'MODULE_B'
            assertThat(bindDemo.test(), is(MODULE_B));
            break;
        default:
            fail();
            break;
        }
        logger(this).debug(bindDemo.getClass().getCanonicalName());
        logger(this).debug(bindDemo.test());
    }
}
