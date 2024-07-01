package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.openai.integration.domain.*;
import org.mapstruct.Mapper;

@Mapper
public interface OpenAiMapper {


    ChatRequest dto2Model(PromptDto promptDto);

    TranslateRequest dto2Model(TranslateDto translateDto);

    SummarizeRequest dto2Model(SummarizeDto summarizeDto);

    GenerateMailRequest dto2Model(GenerateMailDto generateMailDto);

    ExtractDataRequest dto2Model(ExtractDataDto extractDataDto);

    ClassifyRequest dto2Model(ClassifyDto classifyDto);

}
