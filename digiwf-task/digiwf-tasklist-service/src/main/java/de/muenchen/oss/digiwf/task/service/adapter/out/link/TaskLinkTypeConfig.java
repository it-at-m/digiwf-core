package de.muenchen.oss.digiwf.task.service.adapter.out.link;

import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * Configuration for a link type.
 */
@Data
public class TaskLinkTypeConfig {

    /**
     * Name of the group to capture the id from matching id transformer regex.
     */
    public static final String ID_TRANSFORMER_REGEX_GROUP = "id";

    /**
     * Mandatory link type.
     */
    @NonNull
    private final String linkType;
    /**
     * Mandatory url template, using {0} for replacement of the id.
     */
    @NonNull
    private final String urlTemplate;
    /**
     * Optional label template, using {0} for replacement of the id, used only if the reference is not providing its own label.
     */
    private final String labelTemplate;
    /**
     * Optional id transformer regex expression, used to transform the id before used in templates.
     * If matches, the {@link #ID_TRANSFORMER_REGEX_GROUP} is extracted.
     * For example: "^(?>LHM)?(?<id>[\d]+)$" would not capture the first group, and the second group is called "id".
     */
    private final String idTransformerRegex;
}
