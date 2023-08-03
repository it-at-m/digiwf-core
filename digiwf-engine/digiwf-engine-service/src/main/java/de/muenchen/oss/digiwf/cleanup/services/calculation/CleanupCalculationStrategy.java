package de.muenchen.oss.digiwf.cleanup.services.calculation;

import java.util.Date;

/**
 * Interface to calculate the removal time of a process instance based on its ttl
 * and start- or endTime.
 *
 * @author martin.dietrich
 */
public interface CleanupCalculationStrategy {

    Date calculateRemovalTime(Integer ttl, Date startTime, Date endTime);

    boolean canCalculate(Date startTime, Date endTime);
}
