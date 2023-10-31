package com.checkmate.users.model.dto.responses;

import lombok.Data;

import java.util.HashMap;

@Data
public class registrationResponseDTO {
    private boolean failure;
    private HashMap<String, String> fields;
}
