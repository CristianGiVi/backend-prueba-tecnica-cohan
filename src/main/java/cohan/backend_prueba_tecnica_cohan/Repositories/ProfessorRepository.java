package cohan.backend_prueba_tecnica_cohan.Repositories;

import cohan.backend_prueba_tecnica_cohan.Models.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}
