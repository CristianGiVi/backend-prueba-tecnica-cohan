package cohan.backend_prueba_tecnica_cohan.Controllers;

import cohan.backend_prueba_tecnica_cohan.Models.Address;
import cohan.backend_prueba_tecnica_cohan.Services.AddressServices.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<?> getAllAdresses(){
        List<Address> addresses = addressService.findAll();
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/new-address")
    public ResponseEntity<?> generateAddress(@Valid @RequestBody Address address, BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        try{
            if(bindingResult.hasFieldErrors()){
                return validation(bindingResult);
            }
            Map<String, Object> result = addressService.save(address);
            if ((Boolean) result.get("state")){
                response.put("state", "success");
                response.put("address", result.get("result"));
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("state", "error");
                response.put("message", result.get("result"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("state", false);
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
