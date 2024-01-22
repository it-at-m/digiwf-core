package de.muenchen.oss.digiwf.ticket.integration.domain.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Ticket {

    private final String ticketId;

    private String status;

    private String article;

    public Ticket(String ticketId) {
        this.ticketId = ticketId;
    }

    public void updateStatus(String status) {
        if (StringUtils.isBlank(this.status)) {
            throw new IllegalStateException("Status cannot be blank");
        }
        this.status = status;
    }

    public void setArticle(String article) {
        if (StringUtils.isBlank(article)) {
            throw new IllegalStateException("Article cannot be blank");
        }

        this.article = article;
    }

}
