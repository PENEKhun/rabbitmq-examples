package kr.huni.signup.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Annotation to mark a class as an event handler.
 * This is used to identify classes that handle events in the transaction outbox pattern.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EventHandler {
}
