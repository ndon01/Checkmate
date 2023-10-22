package com.checkmate.users.model.dto.responses;

import lombok.Data;

@Data
public class registrationResponseDTO {
    private boolean failure;
    private String failReason;
}
