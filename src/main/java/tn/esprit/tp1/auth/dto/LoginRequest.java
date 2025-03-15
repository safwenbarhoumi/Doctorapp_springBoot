package tn.esprit.tp1.auth.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.tp1.entities.Role;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
    private Role role;
}
