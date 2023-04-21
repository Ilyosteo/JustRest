package kz.atirau.spring.controllers;

import kz.atirau.spring.models.Admin;
import kz.atirau.spring.security.JWTutil;
import kz.atirau.spring.services.adminService.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class ControllerAdmin {

    private AdminService adminService;
    private JWTutil jwTutil;
    private final ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;

    @Autowired
    public ControllerAdmin(AdminService adminService, JWTutil jwTutil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.adminService = adminService;
        this.jwTutil = jwTutil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public Map<String, String> addAdmin(@RequestBody @Valid Admin admin, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Map.of("message", "Ошибка");
        }
        adminService.saveAdmin(admin);

        String token = jwTutil.generateToken(admin.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody Admin admin){
        adminService.onlyAdmin();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(admin.getUsername()
        , admin.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            return Map.of("message", "Incorrect credentails");
        }

        String token = jwTutil.generateToken(admin.getUsername());

        return Map.of("jwt-token", token);
    }

}
