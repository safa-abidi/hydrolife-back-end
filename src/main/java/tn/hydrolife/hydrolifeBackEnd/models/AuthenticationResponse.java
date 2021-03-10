package tn.hydrolife.hydrolifeBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private final String jwt;
}
