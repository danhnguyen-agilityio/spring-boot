package com.agility.springannotation.qualifier.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// The @Target annotation tells where the annotation can be applied.
// In our case, it can be applied to fields, methods, and parameters.
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
// The @Retention annotation specifies how the marked annotation is stored.
// With RetentionPolicy.RUNTIME the marked annotation is retained by the JVM so it can be used by the runtime environment.
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface PersonQ {

    String value();
}
