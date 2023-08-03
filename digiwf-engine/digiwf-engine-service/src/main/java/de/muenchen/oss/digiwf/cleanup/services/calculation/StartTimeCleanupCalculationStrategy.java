package de.muenchen.oss.digiwf.cleanup.services.calculation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Implementation of the calculation strategy of removal time
 * based on the startTime of a process instance.
 *
 * @author martin.dietrich
 */
@Component
@ConditionalOnProperty(
        prefix = "camunda.bpm.generic-properties.properties",
        name = "history-removal-time-strategy",
        havingValue = "start")
public class StartTimeCleanupCalculationStrategy implements CleanupCalculationStrategy{

    @Override
    public Date calculateRemovalTime(Integer ttl, Date startTime, Date endTime) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(startTime);
        cal.add(Calendar.DAY_OF_YEAR, ttl);
        return cal.getTime();
    }

    @Override
    public boolean canCalculate(Date startTime, Date endTime) {
        return startTime != null;
    }

}
