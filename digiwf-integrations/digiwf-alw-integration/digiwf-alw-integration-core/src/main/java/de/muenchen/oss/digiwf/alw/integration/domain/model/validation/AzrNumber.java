package de.muenchen.oss.digiwf.alw.integration.domain.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates format of the AZR Number.
 */
@Target({METHOD, CONSTRUCTOR, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@NotEmpty(message = "AZR-Number must not be null or empty")
@Pattern(regexp = "\\d{12}", message = "AZR-Number is not valid, it must contain 12 digits")
@Constraint(validatedBy = {})
public @interface AzrNumber {
  String message() default "{de.muenchen.oss.digiwf.alw.integration.domain.model.validation.AzrNumber.message}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
