package de.muenchen.oss.digiwf.task.service.adapter.out.link;

import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.links.TaskLinkResolverPort;
import de.muenchen.oss.digiwf.task.service.domain.TaskLink;
import io.holunda.polyflow.view.Task;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * An adapter for on-the-fly resolution of the external references of the tasks.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OnTheFlyTaskLinkResolverAdapter implements TaskLinkResolverPort {

    private final TaskLinkConfigurationProperties taskLinkConfigurationProperties;
    private Map<String, TaskLinkTypeConfig> config;
    private Map<String, Pattern> transformers;

    private static final String TYPE_URL = "url";
    private static final Pattern MD_URL_LINK_REGEX = Pattern.compile("^\\[(?<label>[\\w\\s]+)]\\((?<url>https?://[\\w./?=#\\-+]+)\\)$");
    private static final Pattern MD_TYPED_LINK_REGEX = Pattern.compile("^\\[(?<label>[\\w\\s]+)]\\((?<id>[\\w.]+)\\)$");

    @PostConstruct
    public void initConfiguration() {
        this.config = taskLinkConfigurationProperties
            .getTaskLinkTypes()
            .stream()
            .collect(Collectors.toMap(TaskLinkTypeConfig::getLinkType, Function.identity()));
        this.transformers = taskLinkConfigurationProperties
            .getTaskLinkTypes()
            .stream()
            .filter(config -> config.getIdTransformerRegex() != null)
            .collect(Collectors.toMap(TaskLinkTypeConfig::getLinkType, (config) -> Pattern.compile(config.getIdTransformerRegex())));

        // sanitizing config
        transformers.forEach((key, value) -> {
            if (!value.pattern().contains(TaskLinkTypeConfig.ID_TRANSFORMER_REGEX_GROUP)) {
                throw new IllegalArgumentException("Wrong configuration of TaskLinkResolver. The link type config for " + key + " contains the pattern " + value.pattern() + " which must contained named capturing group 'id', but it was not.");
            }
        });

        log.info("Configured task list resolver for {} types", this.config.size());
    }

    @Override
    public List<TaskLink> apply(Task task) {
        val links = Objects.requireNonNull(task.getPayload().getOrDefault(TaskVariables.TASK_EXTERNAL_LINKS.getName(), Collections.emptyList()));
        //noinspection unchecked
        return ((List<Map<String, String>>)links).stream()
            .map(map -> resolve(map.get("type"), map.get("identity")))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private TaskLink resolve(String type, String id) {
        if (TYPE_URL.equals(type)) {
            return resolveUrl(id);
        } else if (config.containsKey(type)) {
            return resolveTypedLink(config.get(type), id);
        } else {
            return null;
        }
    }

    private TaskLink resolveUrl(String id) {
        val matcher = MD_URL_LINK_REGEX.matcher(id);
        final String url;
        final String label;
        if (matcher.matches()) {
            url = matcher.group("url");
            label = matcher.group("label");
        } else {
            label = null;
            url = id;
        }
        return new TaskLink(TYPE_URL, url, label, null, null);
    }

    /**
     *
     * resolves typed link by using TaskLinkTypeConfig
     *
     * @param config
     * @param id
     * @return the generated TaskLink
     */
    private TaskLink resolveTypedLink(TaskLinkTypeConfig config, String id) {
        val matcher = MD_TYPED_LINK_REGEX.matcher(id);

        String label = null;
        final String cleanId;

        // parse label and URL from parameter
        if (matcher.matches()) {
            cleanId = matcher.group("id");
            label = matcher.group("label");
        } else {
            cleanId = id;
        }

        final String transformedId;
        if (transformers.containsKey(config.getLinkType())) {
            val transformerMatcher = transformers.get(config.getLinkType()).matcher(cleanId);
            if (transformerMatcher.matches()) {
                transformedId = transformerMatcher.group(TaskLinkTypeConfig.ID_TRANSFORMER_REGEX_GROUP);
            } else {
                transformedId = cleanId;
            }
        } else {
            transformedId = cleanId;
        }

        final String url = MessageFormat.format(config.getUrlTemplate(), transformedId);

        // if no label so far, try to get one from template
        if (label == null && config.getLabelTemplate() != null) {
            label = MessageFormat.format(config.getLabelTemplate(), transformedId);
        }

        return new TaskLink(config.getLinkType(), url, label, null, null);
    }
}
