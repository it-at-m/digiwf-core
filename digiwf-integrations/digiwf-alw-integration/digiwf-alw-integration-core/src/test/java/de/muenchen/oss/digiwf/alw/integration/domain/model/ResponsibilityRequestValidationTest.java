package de.muenchen.oss.digiwf.alw.integration.domain.model;


import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ResponsibilityRequestValidationTest {

  private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private final Validator validator = factory.getValidator();

  @Test
  void detects_invalid_azr_number() {

    assertThat(validator.validate(ResponsibilityRequest.builder().azrNummer(null).build()))
        .hasSize(1)
        .element(0)
        .extracting(ConstraintViolation::getMessage)
        .isEqualTo("AZR-Number must not be null or empty");

    assertThat(validator.validate(ResponsibilityRequest.builder().azrNummer("1234567890").build()))
        .hasSize(1)
        .element(0)
        .extracting(ConstraintViolation::getMessage)
        .isEqualTo("AZR-Number is not valid, it must contain 12 digits");

    assertThat(validator.validate(ResponsibilityRequest.builder().azrNummer("1234567890AB").build()))
        .hasSize(1)
        .element(0)
        .extracting(ConstraintViolation::getMessage)
        .isEqualTo("AZR-Number is not valid, it must contain 12 digits");


    assertThat(validator.validate(ResponsibilityRequest.builder().azrNummer("123456789012").build()))
        .hasSize(0);

  }
}