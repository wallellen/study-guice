package edu.alvin.guice.singleton;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Named;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonProviderTest {

    @Inject
    private SingletonInterface singleton1;

    @Inject
    private SingletonInterface singleton2;

    @Inject
    private Injector injector;

    @Before
    public void setup() {
        Guice.createInjector(new Module() {
            @Override
            public void configure(Binder binder) {
            }

            @Named("Value")
            @Provides
            public String providerStringValue() {
                return "Lily";
            }

            @Inject
            @Singleton
            @Provides
            public SingletonInterface providerSingletonInterface(@Named("Value") String value) {
                return new SingletonInterfaceImpl(value);
            }
        }).injectMembers(this);
    }

    @Test
    public void testSingletonInterface() {
        singleton1.showValue();
        assertThat(singleton1, is(singleton2));
        assertThat(singleton2, is(injector.getInstance(SingletonInterface.class)));
    }
}
