package kz.atirau.spring.controllers;

import kz.atirau.spring.models.Teacher;
import kz.atirau.spring.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "*")
public class ControllerTeacher {

    private TeacherService service;

    @Autowired
    public ControllerTeacher(TeacherService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Teacher> addTeachers(@RequestBody Teacher teacher){
        service.saveTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping()
    public List<Teacher> getAllTeachers(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Teacher findByIdTeacher(@PathVariable("id") Long id){
        return service.getTeacher(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long id, @RequestBody Teacher teacher){
        service.update(id, teacher);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().body("Учитель успешно удален");
    }

    @PostMapping("/download")
    public ResponseEntity<?> upload(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage =service.uploadImageToFileSystem(file);

        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/download/{fileSys}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileSys")String name) throws IOException {
        byte[] imageData =service.downloadImageFromFileSystem(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageData);
    }

}
