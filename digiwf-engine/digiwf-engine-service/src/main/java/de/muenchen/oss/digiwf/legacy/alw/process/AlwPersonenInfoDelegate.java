/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.alw.process;

import de.muenchen.oss.digiwf.legacy.alw.domain.AlwService;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to resovle the responsible LDAP Group for the given AZR Number
 *
 * @author externer.dl.horn
 */
@Slf4j
@RequiredArgsConstructor
public class AlwPersonenInfoDelegate implements JavaDelegate {

    private final AlwService kvrService;

    public static final VariableFactory<String> AZR_NUMMER = stringVariable("kvr_azr_nummer");
    public static final VariableFactory<String> RESPONSIBLE_GROUP = stringVariable("kvr_responsible_group");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val azrNummer = AZR_NUMMER.from(execution).getLocalOptional();

        if (!azrNummer.isPresent()) {
            RESPONSIBLE_GROUP.on(execution).setLocal(null);
            return;
        }

        //PROCESSING
        try {
            val responsibleGroup = this.kvrService.getResponsibleLdapGroup(azrNummer.get());

            RESPONSIBLE_GROUP.on(execution).setLocal(responsibleGroup);

        } catch (final Exception error) {
            log.error("Personeninfo could no be loaded AZRNummer: " + azrNummer, error);
            RESPONSIBLE_GROUP.on(execution).setLocal(null);
        }

    }

}
