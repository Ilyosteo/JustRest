package kz.atirau.spring.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public Admin() {
    }

    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }

}
