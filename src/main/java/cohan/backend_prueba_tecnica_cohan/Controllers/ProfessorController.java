package cohan.backend_prueba_tecnica_cohan.Controllers;

import cohan.backend_prueba_tecnica_cohan.Models.Professor;
import cohan.backend_prueba_tecnica_cohan.Models.Student;
import cohan.backend_prueba_tecnica_cohan.Services.ProfessorServices.ProfessorServiceImpl;
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
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorServiceImpl professorService;

    @GetMapping()
    public ResponseEntity<?> getAllStudents(){
        List<Professor> professors = professorService.findAll();
        return ResponseEntity.ok(professors);
    }

    @PostMapping("/new-professor")
    public ResponseEntity<?> generateProfessor(@Valid @RequestBody Professor professor, BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        try{
            if(bindingResult.hasFieldErrors()){
                return validation(bindingResult);
            }

            Map<String, Object> result = professorService.save(professor);
            if ((Boolean) result.get("status")){
                response.put("status", true);
                response.put("student", result.get("result"));
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            response.put("status", false);
            response.put("message", result.get("result"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/edit-professor/{id}")
    public ResponseEntity<?> editProfessor(@PathVariable Long id, @Valid @RequestBody Professor professor,
                                         BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        try{
            if(bindingResult.hasFieldErrors()){
                return validation(bindingResult);
            }

            Map<String, Object> result = professorService.update(professor, id);
            if ((Boolean) result.get("status")){
                response.put("status", true);
                response.put("professor", result.get("result"));
                return ResponseEntity.ok(response);
            }
            response.put("status", false);
            response.put("message", result.get("result"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @DeleteMapping("/delete-professor/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            Map<String, Object> result = professorService.delete(id);
            if ((Boolean) result.get("status")){
                response.put("status", true);
                response.put("message", result.get("result"));
                return ResponseEntity.ok(response);
            }
            response.put("status", false);
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
