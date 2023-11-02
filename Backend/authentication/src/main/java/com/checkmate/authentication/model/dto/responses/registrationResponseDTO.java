package com.checkmate.authentication.model.dto.responses;

import lombok.Data;

import java.util.HashMap;

@Data
public class registrationResponseDTO {
    private boolean failure;
    private HashMap<String, String> fields;

    public registrationResponseDTO() {
        failure = false;
        fields = new HashMap<>();
    }
}
