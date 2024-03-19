package de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteArticleDto {

    private String ticketId;
    private String article;
    private String userId;
    private String status;
    private String filepaths;

    public List<String> getFilepaths() {
        if (filepaths == null)
            return List.of();
        return Arrays.asList(filepaths.split(";"));
    }

}
