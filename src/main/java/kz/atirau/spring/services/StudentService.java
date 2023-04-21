package kz.atirau.spring.services;

import kz.atirau.spring.models.Student;
import kz.atirau.spring.repository.StudentRepository;
import kz.atirau.spring.repository.adminRepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;
    private String FOLDER_PATH = "C:/Users/Ilyos/Desktop/MyFiles/";


    @Autowired
    public StudentService(StudentRepository studentRepository, AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public List<Student> getAll(){
        return studentRepository.findAll();
    }

    @Transactional
    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Student createStudent(Student student){
        student.setRole("ROLE_USER");
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long id, Student updateStudent){
        Student student = getStudentById(id);

        student.setId(student.getId());
        student.setName(updateStudent.getName());
        student.setIin(updateStudent.getIin());
        student.setMaritalStatus(updateStudent.getMaritalStatus());
        student.setTypeOfEducation(updateStudent.getTypeOfEducation());
        student.setRegisteredAddress(updateStudent.getRegisteredAddress());
        student.setResidentialAddress(updateStudent.getResidentialAddress());
        student.setContactNumber(updateStudent.getContactNumber());
        student.setEmail(updateStudent.getEmail());
        student.setCourseNumber(updateStudent.getCourseNumber());
        student.setParents(updateStudent.getParents());
        student.setParentsContactNumber(student.getParentsContactNumber());
        student.setParentsWorkplace(updateStudent.getParentsWorkplace());
        student.setCertificatesOrAwards(updateStudent.getCertificatesOrAwards());
        student.setHasDisability(updateStudent.getHasDisability());
        student.setReligiousBeliefs(updateStudent.getReligiousBeliefs());
        student.setRole(student.getRole());

        return studentRepository.save(student);
    }

    @Transactional
    public void delete(Long id){
        studentRepository.deleteById(id);
    }

    public Optional<List<Student>> getStudentByName(String name){
        return studentRepository.findByName(name);
    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Student student = studentRepository.save(Student.builder()
                .namePicture(file.getOriginalFilename())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if(student != null){
            return "file uploaded successfully " + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String name) throws IOException {
        Optional<Student> student =studentRepository.findByNamePicture(name);
        String filePath = student.get().getFilePath();

        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
