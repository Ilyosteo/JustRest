package kz.atirau.spring.services.adminService;

import kz.atirau.spring.models.Admin;
import kz.atirau.spring.repository.adminRepository.AdminRepository;
import kz.atirau.spring.security.AdminDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);

        if(admin.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new AdminDetails(admin.get());
    }
}
