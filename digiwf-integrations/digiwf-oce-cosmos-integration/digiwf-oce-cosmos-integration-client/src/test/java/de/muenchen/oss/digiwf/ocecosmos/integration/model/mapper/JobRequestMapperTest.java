package de.muenchen.oss.digiwf.ocecosmos.integration.model.mapper;

import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.DataTypes;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.DeliveryTypes;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.JobRequest;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

public class JobRequestMapperTest {

    private final JobRequestMapper jobRequestMapper = Mappers.getMapper(JobRequestMapper.class);

    @Test
    public void mapJobsTest() {
        var request = getPrefilledRequest();

        var jobs = jobRequestMapper.mapToJobs(request);

        assertThat(jobs.getClient(), is(request.getClientId()));
        assertThat(jobs.getTitle(), is(String.format("%s.%s.%s", request.getApplicationName(), request.getTemplateName(), request.getJobType())));
        assertThat(jobs.getTextStatus(), is(String.format("Empfange Datensätze aus %s", request.getApplicationName())));
        assertThat(jobs.getDataType(), is(request.getDataType().toString()));
        assertThat(jobs.getInputChannel(), is("RESTAPI"));
        assertThat(jobs.getCustomField(), is(request.getJobType()));
        assertThat(jobs.getCustomField1(), is(request.getTemplateName()));
        assertThat(jobs.getCustomField2(), is(request.getDeliveryType().toString()));
    }

    @Test
    public void mapJobAttributesAllFieldsTest() {
        var request = getPrefilledRequest();

        var attributes = jobRequestMapper.mapToJobAttributes(request, 123L);

        MatcherAssert.assertThat(attributes, everyItem(hasProperty("jobId", is(123L))));
        assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("FORM_NAME")),
                hasProperty("attributeValue", is(request.getTemplateName()))
        )));
        assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("DEBITOR")),
                hasProperty("attributeValue", is(request.getDebtor()))
        )));
        assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("USER")),
                hasProperty("attributeValue", is(request.getApplicationName()))
        )));
        MatcherAssert.assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("ERST_DATUM")),
                hasProperty("attributeValue", startsWith(String.valueOf(LocalDateTime.now().getYear()))),
                hasProperty("attributeValue", matchesPattern("\\d{4}-\\d{2}-\\d{2}.\\d{6}")))
        ));
        assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("EMAIL")),
                hasProperty("attributeValue", is(request.getEmail()))
        )));
        assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("ABHOL")),
                hasProperty("attributeValue", is(request.getPickupLocation()))
        )));
        MatcherAssert.assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("DRUCKEN")),
                hasProperty("attributeValue", is("1"))
        )));
        MatcherAssert.assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("MAILEN")),
                hasProperty("attributeValue", is("1"))
        )));
        MatcherAssert.assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("ARCHIVIEREN")),
                hasProperty("attributeValue", is("1"))
        )));
    }

    @Test
    public void mapJobAttributesPostalTest() {
        var request = getPrefilledRequest();
        request.setDeliveryType(DeliveryTypes.POSTVERSAND);

        var attributes = jobRequestMapper.mapToJobAttributes(request, 123L);

        MatcherAssert.assertThat(attributes, not(hasItem(
                hasProperty("attributeName", is("ABHOL"))
        )));
    }

    @Test
    public void mapJobAttributesNoMailTest() {
        var request = getPrefilledRequest();
        request.setMailJob(false);

        var attributes = jobRequestMapper.mapToJobAttributes(request, 123L);

        MatcherAssert.assertThat(attributes, hasItem(allOf(
                hasProperty("attributeName", is("MAILEN")),
                hasProperty("attributeValue", is("0"))
        )));
        MatcherAssert.assertThat(attributes, not(hasItem(
                hasProperty("attributeName", is("EMAIL"))
        )));
    }

    private JobRequest getPrefilledRequest() {
        return JobRequest.builder()
                .clientId("client123")
                .applicationName("Test-App")
                .templateName("test-template")
                .jobType("SAMMELJOB")
                .deliveryType(DeliveryTypes.DIENSTVERSAND)
                .dataType(DataTypes.JSON)
                .debtor("testdebtor")
                .email("test@test.de")
                .mailJob(true)
                .printJob(true)
                .archiveJob(true)
                .pickupLocation("test-location")
                .build();
    }

}
