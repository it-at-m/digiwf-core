package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.ClassifyDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.ExtractDataDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.GenerateMailDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.MapJsonDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.PromptDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.SummarizeDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.TranslateDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ChatRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ClassifyRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ExtractDataRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.GenerateMailRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.MapJsonRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.SummarizeRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.TranslateRequest;
import org.mapstruct.Mapper;

@Mapper
public interface OpenAiMapper {


    ChatRequest dto2Model(PromptDto promptDto);

    TranslateRequest dto2Model(TranslateDto translateDto);

    SummarizeRequest dto2Model(SummarizeDto summarizeDto);

    GenerateMailRequest dto2Model(GenerateMailDto generateMailDto);

    MapJsonRequest dto2Model(MapJsonDto mapJsonDto);

    ExtractDataRequest dto2Model(ExtractDataDto extractDataDto);

    ClassifyRequest dto2Model(ClassifyDto classifyDto);

}
