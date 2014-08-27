package br.com.jlameira.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Carlos Santos
 */
@Constraint(validatedBy = CPFEntityPropertyValidator.class)
@Target({ ElementType.METHOD,  ElementType.FIELD,  ElementType.ANNOTATION_TYPE,  ElementType.CONSTRUCTOR,  ElementType.PARAMETER })
//@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {

    String message() default "CPF inv\u00E1lido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
