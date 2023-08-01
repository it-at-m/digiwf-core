package io.muenchendigital.digiwf.example.integration.core.adapter;

import io.muenchendigital.digiwf.example.integration.core.domain.ExampleModel;
import org.mapstruct.Mapper;

@Mapper
public interface ExampleMapper {

    ExampleModel toModel(ExampleDto exampleDto);

}
