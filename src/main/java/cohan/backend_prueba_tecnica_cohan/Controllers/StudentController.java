package cohan.backend_prueba_tecnica_cohan.Controllers;

import cohan.backend_prueba_tecnica_cohan.Models.Student;
import cohan.backend_prueba_tecnica_cohan.Services.StudentServices.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping()
    public ResponseEntity<?> getAllStudents(){
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/new-student")
    public ResponseEntity<?> generateStudent(@Valid @RequestBody Student student, BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        try{
            if(bindingResult.hasFieldErrors()){
                return validation(bindingResult);
            }
            Map<String, Object> result = studentService.save(student);
            if ((Boolean) result.get("status")){
                response.put("status", "success");
                response.put("student", result.get("result"));
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            response.put("status", "error");
            response.put("message", result.get("result"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PutMapping("/edit-student/{id}")
    public ResponseEntity<?> editStudent(@PathVariable Long id, @Valid @RequestBody Student student,
                                         BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        try{
            if(bindingResult.hasFieldErrors()){
                return validation(bindingResult);
            }

            Map<String, Object> result = studentService.update(student, id);
            if ((Boolean) result.get("status")){
                response.put("status", "success");
                response.put("student", result.get("result"));
                return ResponseEntity.ok(response);
            }
            response.put("status", "error");
            response.put("message", result.get("result"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            Map<String, Object> result = studentService.delete(id);
            if ((Boolean) result.get("status")){
                response.put("status", "success");
                response.put("message", result.get("result"));
                return ResponseEntity.ok(response);
            }
            response.put("status", "error");
            response.put("message", result.get("result"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            String message = "El campo " + err.getField() + " " + err.getDefaultMessage();
            errors.put(err.getField(), message);
        });

        return ResponseEntity.badRequest().body(errors);
    }


}
