package de.muenchen.oss.digiwf.alw.integration.adapter.out.orgstructure;

import de.muenchen.oss.digiwf.alw.integration.application.port.out.OrgStructureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class OrgStructureMapperAdapter implements OrgStructureMapper {

  private final Map<String, String> sachbearbeiterMap;

  @Override
  public String map(@NonNull String sachbearbeiter) {
    return sachbearbeiterMap.get(sachbearbeiter);
  }
}
