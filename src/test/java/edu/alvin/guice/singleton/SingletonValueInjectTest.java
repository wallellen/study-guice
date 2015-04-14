package edu.alvin.guice.singleton;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Named;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonValueInjectTest {

    @Inject
    private SingletonValue singletonValue1;

    @Inject
    private SingletonValue singletonValue2;

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
                return "Alvin";
            }
        }).injectMembers(this);
    }

    @Test
    public void testSingletonAnnotation() {
        singletonValue1.showValue();
        assertThat(singletonValue1, is(singletonValue2));
        assertThat(singletonValue2, is(injector.getInstance(SingletonValue.class)));
    }
}