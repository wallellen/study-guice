package edu.alvin.guice.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation of C.
 */
@Documented
@BindingAnnotation      // This Annotation is used in guice binding
@Retention(RetentionPolicy.RUNTIME)
public @interface C {
}

