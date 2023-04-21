package kz.atirau.spring.controllers;


import kz.atirau.spring.models.Student;
import kz.atirau.spring.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class ControllerStudent {

    private StudentService studentService;

    @Autowired
    public ControllerStudent(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> findAll(){
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/name")
    public Optional<List<Student>> getStudentByName(@RequestParam("name") String name){
        return studentService.getStudentByName(name);
    }

    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        studentService.createStudent(student);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
        studentService.updateStudent(id, student);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedStudent(@PathVariable("id")Long id){
        studentService.delete(id);
        return ResponseEntity.ok().body("Студент успешно удален");
    }

    @PostMapping("/download")
    public ResponseEntity<?> upload(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage =studentService.uploadImageToFileSystem(file);

        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/download/{fileSys}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileSys")String name) throws IOException {
        byte[] imageData =studentService.downloadImageFromFileSystem(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageData);
    }

}
