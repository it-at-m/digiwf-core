/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.process.saveschriftstuecke;

import io.muenchendigital.digiwf.legacy.dms.muc.process.mapper.MetadataProcessDataMapper;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;

import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

@Component
@RequiredArgsConstructor
public class SaveSchriftstueckeEndListener implements JavaDelegate {

    public static final VariableFactory<String> DOCUMENT_SUFFIX = stringVariable("dms_document_field_suffix");
    public static final VariableFactory<SaveSchriftstueckeResult[]> SCHRIFTSTUECKE = customVariable("dms_saved_schriftstuecke",
            SaveSchriftstueckeResult[].class);
    public static final VariableFactory<String> SCHRIFTSTUECKE_KEYS = stringVariable("dms_copy_schriftstuecke_coos");

    private final MetadataProcessDataMapper metadataMapper;

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {

        final SaveSchriftstueckeResult[] schriftstuecke = SCHRIFTSTUECKE.from(delegateExecution).getLocal();
        val suffix = DOCUMENT_SUFFIX.from(delegateExecution).getLocal();
        val keys = SCHRIFTSTUECKE_KEYS.from(delegateExecution).getLocal();

        Arrays.stream(schriftstuecke).forEach(obj -> {
                    delegateExecution.setVariable(obj.getFieldKey() + suffix, this.metadataMapper.map2HashMap(obj.getSchriftstuecke()));

                    //TODO in serializer auslagern
                    val summary = obj.getSchriftstuecke().stream()
                            .map(meta -> meta.getName() + " (" + meta.getUrl() + ")")
                            .collect(Collectors.joining("; "));
                    delegateExecution.setVariable(obj.getFieldKey() + suffix + "__detail_summary", summary);
                }
        );

        val savedSchrifstueckeKeys = Arrays.stream(schriftstuecke)
                .map(SaveSchriftstueckeResult::getFieldKey)
                .collect(Collectors.toList());

        Arrays.stream(keys.split(";"))
                .filter(key -> !savedSchrifstueckeKeys.contains(key))
                .forEach(key -> delegateExecution.setVariable(key + suffix, this.metadataMapper.map(new ArrayList<>())));
    }
}
