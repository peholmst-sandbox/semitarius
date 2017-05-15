package net.pkhapps.semitarius.server.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documentation annotation used to annotate default constructors that only exist because JPA requires them to (i.e.
 * they should never be used in real code).
 */
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.SOURCE)
public @interface ConstructorUsedByJPAOnly {
}
