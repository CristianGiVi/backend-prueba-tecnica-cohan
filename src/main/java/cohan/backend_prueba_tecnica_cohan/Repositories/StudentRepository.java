package cohan.backend_prueba_tecnica_cohan.Repositories;

import cohan.backend_prueba_tecnica_cohan.Models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
