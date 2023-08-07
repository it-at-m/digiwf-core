package de.muenchen.oss.digiwf.process.instance.api.mapper;

import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ProcessInstancePageMapper {


    public Page<ServiceInstance> toPage(
            final List<ServiceInstance> definitions,
            final int page,
            final int size,
            @Nullable final String query
    ) {
        val filteredDefinitions = filterByQuery(definitions, query);
        return listToPage(filteredDefinitions, page, size);
    }

    private Page<ServiceInstance> listToPage(
            final List<ServiceInstance> definitions,
            final int page,
            final int size) {

        val from = page * size;
        val to = Math.min((page + 1) * size, definitions.size());
        val pageContent = definitions.subList(from, to);
        return new PageImpl<ServiceInstance>(pageContent, PageRequest.of(page, size), definitions.size());
    }

    private List<ServiceInstance> filterByQuery(
            final List<ServiceInstance> definitions,
            @Nullable final String query
    ) {
        final String lowerCaseQuery = query == null ? "" : query.toLowerCase();
        return lowerCaseQuery.isBlank()
                ? definitions
                : definitions.stream().filter(
                it ->
                        StringUtils.containsIgnoreCase(it.getId(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getInstanceId(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDescription(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDefinitionKey(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDefinitionName(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getStatusKey(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getStatus(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getStartTime() != null ? it.getStartTime().toString() : "", lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getEndTime() != null ? it.getEndTime().toString() : "", lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getRemovalTime() != null ? it.getRemovalTime().toString() : "", lowerCaseQuery)
        ).collect(Collectors.toList());
    }
}
