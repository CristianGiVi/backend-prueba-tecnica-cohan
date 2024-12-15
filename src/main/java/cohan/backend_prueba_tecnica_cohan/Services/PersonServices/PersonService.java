package cohan.backend_prueba_tecnica_cohan.Services.PersonServices;

import cohan.backend_prueba_tecnica_cohan.Models.Person;

import java.util.List;
import java.util.Map;

public interface PersonService<T> {

    List<T> findAll();

    Map<String, Object> save(T t);

    Map<String, Object> update(T t, Long id);

    Map<String, Object> delete(Long id);
}
