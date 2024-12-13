package cohan.backend_prueba_tecnica_cohan.Services.StudentServices;

import cohan.backend_prueba_tecnica_cohan.Models.Student;
import cohan.backend_prueba_tecnica_cohan.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public Map<String, Object> save(Student student) {
        Map<String, Object> response = new HashMap<>();
        Student newStudent = studentRepository.save(student);
        response.put("state", true);
        response.put("result", newStudent);
        return response;
    }
}
