package io.muenchendigital.digiwf.task.service.adapter.in.rest.mapper;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

class DateMapperTest {

  private final DateMapper mapper = new DateMapperImpl();

  @Test
  public void should_convert_using_system_tz() {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
    Instant instant = Instant.parse("2023-06-30T23:59:59Z");
    OffsetDateTime asDateTime = mapper.to(instant);
    assertThat(asDateTime.toString()).isEqualTo("2023-07-01T01:59:59+02:00");
  }

  @Test
  public void should_convert_using_system_tz2() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    Instant instant = Instant.parse("2023-06-30T23:59:59Z");
    OffsetDateTime asDateTime = mapper.to(instant);
    assertThat(asDateTime.toString()).isEqualTo("2023-06-30T23:59:59Z");
  }

}
