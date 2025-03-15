package tn.esprit.tp1.auth.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.tp1.entities.Role;

@Getter
@Setter
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Role role;
}
