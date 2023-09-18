package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;

public interface ProcedureRepository {

    Procedure createProcedure(Procedure procedure, String user);

}
