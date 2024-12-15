package cohan.backend_prueba_tecnica_cohan.Repositories;

import cohan.backend_prueba_tecnica_cohan.Models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByEmailAddress(String email);

    Optional<Person> findByAddress_Id(Long addresId);
}
