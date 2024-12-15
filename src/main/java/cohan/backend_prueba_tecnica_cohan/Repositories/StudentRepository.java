package cohan.backend_prueba_tecnica_cohan.Repositories;

import cohan.backend_prueba_tecnica_cohan.Models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByStudentNumber(String studentNumber);
}
