package de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteArticleDto {

    private String ticketId;
    private String article;
    private String userId;
    private String status;

}
