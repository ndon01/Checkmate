package com.checkmate.authentication.model.dto.responses;

import lombok.Data;

@Data
public class registrationResponseDTO {
    private boolean failure;
    private String failReason;
}
