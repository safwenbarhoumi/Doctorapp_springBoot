package tn.esprit.tp1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.Admin;
import tn.esprit.tp1.repositories.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            return "Email already in use!";
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return "Admin registered successfully!";
    }

    public String loginAdmin(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        return admin.map(a -> a.getPassword().equals(password) ? "Login successful" : "Invalid credentials")
                .orElse("User not found");
    }
}
