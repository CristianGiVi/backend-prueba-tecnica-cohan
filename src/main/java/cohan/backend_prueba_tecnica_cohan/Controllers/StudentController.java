package cohan.backend_prueba_tecnica_cohan.Controllers;

import cohan.backend_prueba_tecnica_cohan.Models.Student;
import cohan.backend_prueba_tecnica_cohan.Services.StudentServices.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public ResponseEntity<?> getAllStudents(){
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/new-student")
    public ResponseEntity<?> generateStudent(@RequestBody Student student){
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> result = studentService.save(student);
        if ((Boolean) result.get("state")){
            response.put("state", "success");
            response.put("student", result.get("result"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("state", "error");
        response.put("message", result.get("result"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }


}
