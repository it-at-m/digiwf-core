package io.muenchendigital.digiwf.process.api;

import lombok.*;

import java.util.Map;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StartProcessEvent implements StartProcess{

    @NotBlank
    private String key;

    private Map<String, Object> data;

}
