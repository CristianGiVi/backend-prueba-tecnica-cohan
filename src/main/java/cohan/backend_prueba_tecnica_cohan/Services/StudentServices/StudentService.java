package cohan.backend_prueba_tecnica_cohan.Services.StudentServices;

import cohan.backend_prueba_tecnica_cohan.Models.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Student> findAll();

    Map<String, Object> save(Student student);
}
