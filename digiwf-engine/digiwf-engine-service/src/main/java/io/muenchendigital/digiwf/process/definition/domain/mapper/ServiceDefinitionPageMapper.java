package io.muenchendigital.digiwf.process.definition.domain.mapper;

import io.muenchendigital.digiwf.process.definition.domain.model.ServiceDefinition;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ServiceDefinitionPageMapper {

    public Page<ServiceDefinition> toPage(
            final List<ServiceDefinition> definitions,
            final int page,
            final int size,
            @Nullable final String query
    ) {
        val filteredDefinitions = filterByQuery(definitions, query);
        return listToPage(filteredDefinitions, page, size);
    }

    private Page<ServiceDefinition> listToPage(
            final List<ServiceDefinition> definitions,
            final int page,
            final int size) {

        val from = page * size;
        val to = Math.min((page + 1) * size, definitions.size());
        val pageContent = definitions.subList(from, to);
        return new PageImpl<ServiceDefinition>(pageContent, PageRequest.of(page, size), definitions.size());
    }

    private List<ServiceDefinition> filterByQuery(
            final List<ServiceDefinition> definitions,
            @Nullable final String query
    ) {
        final String lowerCaseQuery = query == null ? "" : query.toLowerCase();
        return lowerCaseQuery.isBlank()
                ? definitions
                : definitions.stream().filter(
                it ->
                        StringUtils.containsIgnoreCase(it.getKey(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getName(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDescription(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getVersionTag(), lowerCaseQuery)
        ).collect(Collectors.toList());
    }
}
