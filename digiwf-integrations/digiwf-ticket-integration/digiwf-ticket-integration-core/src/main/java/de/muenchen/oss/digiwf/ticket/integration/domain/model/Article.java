package de.muenchen.oss.digiwf.ticket.integration.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@EqualsAndHashCode
public class Article {

    private String text;

    private String userId;

    public Article(String text, String userId) {
        setText(text);
        this.userId = userId;
    }

    private void setText(String text) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalStateException("Article cannot be blank");
        }
        this.text = text;
    }

}
