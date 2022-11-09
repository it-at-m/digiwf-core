package io.muenchendigital.digiwf.cleanup.services.calculation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Implementation of the calculation strategy of removal time
 * based on the endTime of a process instance.
 *
 * @author martin.dietrich
 */
@Component
@ConditionalOnProperty(
        prefix = "camunda.bpm.generic-properties.properties",
        name = "history-removal-time-strategy",
        havingValue = "end")
public class EndTimeCleanupCalculationStrategy implements CleanupCalculationStrategy{

    @Override
    public Date calculateRemovalTime(Integer ttl, Date startTime, Date endTime) {
        if (endTime == null){
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(endTime);
        cal.add(Calendar.DAY_OF_YEAR, ttl);
        return cal.getTime();
    }

    @Override
    public boolean canCalculate(Date startTime, Date endTime) {
        return endTime != null;
    }
}
