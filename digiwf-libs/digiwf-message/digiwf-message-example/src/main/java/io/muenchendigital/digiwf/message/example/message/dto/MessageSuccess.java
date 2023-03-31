package io.muenchendigital.digiwf.message.example.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageSuccess {
    private boolean success;
    private String message;
}
