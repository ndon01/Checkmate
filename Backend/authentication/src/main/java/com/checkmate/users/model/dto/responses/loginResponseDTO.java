package com.checkmate.users.model.dto.responses;

import lombok.Data;

@Data
public class loginResponseDTO {
    private boolean failure;
    private String failReason;
}
