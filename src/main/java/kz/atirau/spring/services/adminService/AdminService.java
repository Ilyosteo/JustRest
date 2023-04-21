package kz.atirau.spring.services.adminService;


import kz.atirau.spring.models.Admin;
import kz.atirau.spring.repository.adminRepository.AdminRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminService {

    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void onlyAdmin(){
        System.out.println("Здесь только админ");
    }


    @Transactional
    public Admin saveAdmin(Admin admin){
        admin.setRole("ROLE_ADMIN");
        adminRepository.save(admin);
        return admin;
    }


}
