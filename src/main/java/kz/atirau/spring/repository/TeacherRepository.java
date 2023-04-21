package kz.atirau.spring.repository;

import kz.atirau.spring.models.Student;
import kz.atirau.spring.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    void deleteById(Long id);

    Optional<Teacher> findByNamePicture(String name);
}
