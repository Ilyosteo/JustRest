package kz.atirau.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long iin;
    private Long idDocument;
    private String registeredAddress;
    private String residentialAddress;
    private Long contactNumber;
    @Email
    private String email;
    private String publicationType;
    private String publicationName;
    private String publicationEdition;
    private String publicationYear;
    private String publicationNumber;
    private String coAuthors;
    private String supportingDocuments;
    private String awardType;
    private String honoraryTitle;
    private String awardDate;
    private String trainingForm;
    private String city;
    private String organizationName;
    private String startDate;
    private String endDate;
    private String topic;
    private String supportingDocumentsType;
    @Column(name = "file_path")
    private String filePath;
    private String namePicture;
    @Column(name = "role")
    private String role;

}