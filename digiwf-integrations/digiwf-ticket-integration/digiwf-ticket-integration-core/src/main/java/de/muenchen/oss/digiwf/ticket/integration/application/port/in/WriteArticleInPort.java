package de.muenchen.oss.digiwf.ticket.integration.application.port.in;

import jakarta.validation.constraints.NotBlank;

public interface WriteArticleInPort {

    void writeArticle(@NotBlank String ticketId, @NotBlank String article, String status);
}
