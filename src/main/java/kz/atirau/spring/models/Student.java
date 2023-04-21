package kz.atirau.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "iin")
    private int iin;
    @Column(name = "id_document")
    private int idDocument;
    private String maritalStatus;
    private String typeOfEducation;
    private String registeredAddress;
    private String residentialAddress;
    private int contactNumber;
    @Email
    private String email;
    private int courseNumber;
    private String parents;
    private int parentsContactNumber;
    private String parentsWorkplace;
    private String certificatesOrAwards;
    private Boolean hasDisability;
    private String hobbies;
    private String religiousBeliefs;
    @Column(name = "file_path")
    private String filePath;
    private String namePicture;

    @Column(name = "role")
    private String role;

}

