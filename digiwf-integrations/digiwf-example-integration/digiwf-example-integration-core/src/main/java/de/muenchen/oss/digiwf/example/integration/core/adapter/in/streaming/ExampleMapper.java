package de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.example.integration.core.domain.ExampleModel;
import org.mapstruct.Mapper;

@Mapper
public interface ExampleMapper {

    ExampleModel toModel(ExampleDto exampleDto);

}
