package de.muenchen.oss.digiwf.email.integration.domain.model.paths;

import de.muenchen.oss.digiwf.email.integration.domain.model.BasicMail;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BasicMailPaths extends BasicMail {
    @NotBlank
    private String fileContext;
    private String filePaths;

    public BasicMailPaths(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, String fileContext, String filePaths) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo);
        this.fileContext = fileContext;
        this.filePaths = filePaths;
    }

    public List<String> parseFilePaths() {
        if (StringUtils.isBlank(filePaths))
            return List.of();
        return List.of(filePaths.split("[,;]"));
    }
}
