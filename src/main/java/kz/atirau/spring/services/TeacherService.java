package kz.atirau.spring.services;

import kz.atirau.spring.models.Student;
import kz.atirau.spring.models.Teacher;
import kz.atirau.spring.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;
    private final String FOLDER_PATH = "C:/Users/Ilyos/Desktop/MyFiles/";

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public Teacher saveTeacher(Teacher teacher){
        teacher.setRole("ROLE_USER");
        return teacherRepository.save(teacher);
    }

    @Transactional
    public Teacher getTeacher(Long id){
        return teacherRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Teacher> getAll(){
        return teacherRepository.findAll();
    }

    @Transactional
    public void delete(Long id){
        teacherRepository.deleteById(id);
    }

    @Transactional
    public Teacher update(Long id, Teacher updateteacher){
        Teacher teacher = getTeacher(id);

        teacher.setName(updateteacher.getName());
        teacher.setIin(updateteacher.getIin());
        teacher.setIdDocument(updateteacher.getIdDocument());
        teacher.setRegisteredAddress(updateteacher.getRegisteredAddress());
        teacher.setResidentialAddress(updateteacher.getResidentialAddress());
        teacher.setContactNumber(updateteacher.getContactNumber());
        teacher.setEmail(updateteacher.getEmail());
        teacher.setPublicationType(updateteacher.getPublicationType());
        teacher.setPublicationName(updateteacher.getPublicationName());
        teacher.setPublicationEdition(updateteacher.getPublicationEdition());
        teacher.setPublicationYear(updateteacher.getPublicationYear());
        teacher.setPublicationNumber(updateteacher.getPublicationNumber());
        teacher.setCoAuthors(updateteacher.getCoAuthors());
        teacher.setSupportingDocuments(updateteacher.getSupportingDocuments());
        teacher.setAwardType(updateteacher.getAwardType());
        teacher.setHonoraryTitle(updateteacher.getHonoraryTitle());
        teacher.setAwardDate(updateteacher.getAwardDate());
        teacher.setTrainingForm(updateteacher.getTrainingForm());
        teacher.setCity(updateteacher.getCity());
        teacher.setOrganizationName(updateteacher.getOrganizationName());
        teacher.setStartDate(updateteacher.getStartDate());
        teacher.setEndDate(updateteacher.getEndDate());
        teacher.setTopic(updateteacher.getTopic());
        teacher.setSupportingDocumentsType(updateteacher.getSupportingDocumentsType());
        return saveTeacher(teacher);
    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Teacher teacher = teacherRepository.save(Teacher.builder()
                .name(file.getOriginalFilename())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if(teacher != null){
            return "file uploaded successfully " + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String name) throws IOException {
        Optional<Teacher> teacher =teacherRepository.findByNamePicture(name);
        String filePath = teacher.get().getFilePath();

        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
