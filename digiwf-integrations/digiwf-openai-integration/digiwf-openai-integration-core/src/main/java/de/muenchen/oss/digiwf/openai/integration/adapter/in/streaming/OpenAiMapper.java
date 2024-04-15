package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.PromptDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OpenAiMapper {


    @Mapping(source = "prompt", target = "prompt")
    OpenAiRequest dto2Model(PromptDto promptDto);

}
