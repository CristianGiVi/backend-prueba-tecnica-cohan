package cohan.backend_prueba_tecnica_cohan.Services.ProfessorServices;

import cohan.backend_prueba_tecnica_cohan.Models.Address;
import cohan.backend_prueba_tecnica_cohan.Models.Person;
import cohan.backend_prueba_tecnica_cohan.Models.Professor;
import cohan.backend_prueba_tecnica_cohan.Repositories.AddressRepository;
import cohan.backend_prueba_tecnica_cohan.Repositories.PersonRepository;
import cohan.backend_prueba_tecnica_cohan.Repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Professor> findAll() {
        return (List<Professor>) professorRepository.findAll();
    }

    @Transactional
    @Override
    public Map<String, Object> save(Professor professor) {
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Person> personFoundByEmailAddress = personRepository.findByEmailAddress(professor.getEmailAddress());
            if (personFoundByEmailAddress.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe una persona registrada con esta direccion de correo");
                return response;
            }

            Optional<Person> personFoundByPhoneNumber = personRepository.findByPhoneNumber(professor.getPhoneNumber());
            if (personFoundByPhoneNumber.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe una persona registrada con este numero de celular");
                return response;
            }

            Optional<Address> foundAddress = addressRepository.findById(professor.getAddress().getId());
            if(foundAddress.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe la direccion ingresada");
                return response;
            }
            Optional<Person> foundUsedAddress = personRepository.findByAddress_Id(professor.getAddress().getId());
            if(foundUsedAddress.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe una persona registrada en esta direccion");
                return response;
            }

            Professor newProfessor = professorRepository.save(professor);
            response.put("status", true);
            response.put("result", newProfessor);
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Map<String, Object> update(Professor professor, Long id) {
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Professor> currentProfessor = professorRepository.findById(id);
            if(currentProfessor.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe el profesor ingresado");
                return response;
            }

            Optional<Person> personFoundByEmailAddress = personRepository.findByEmailAddress(professor.getEmailAddress());
            if (personFoundByEmailAddress.isPresent()){

                if(!personFoundByEmailAddress.get().getEmailAddress().equals(currentProfessor.get().getEmailAddress())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada con esta direccion de correo");
                    return response;
                }
            }

            Optional<Person> personFoundByPhoneNumber = personRepository.findByPhoneNumber(professor.getPhoneNumber());
            if (personFoundByPhoneNumber.isPresent()){
                if(!personFoundByPhoneNumber.get().getPhoneNumber().equals(currentProfessor.get().getPhoneNumber())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada con este numero de celular");
                    return response;
                }

            }

            Optional<Address> foundAddress = addressRepository.findById(professor.getAddress().getId());
            if(foundAddress.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe la direccion ingresada");
                return response;
            }
            Optional<Person> personFoundByAddressId = personRepository.findByAddress_Id(professor.getAddress().getId());
            if(personFoundByAddressId.isPresent()){
                if(!currentProfessor.get().getAddress().getId().equals(professor.getAddress().getId())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada en esta direccion");
                    return response;
                }
            }

            Professor professorDB = currentProfessor.get();

            professorDB.setAddress(professor.getAddress());
            professorDB.setEmailAddress(professor.getEmailAddress());
            professorDB.setPhoneNumber(professor.getPhoneNumber());
            professorDB.setName(professor.getName());
            professorDB.setSalary(professor.getSalary());

            Professor updatedProfessor = professorRepository.save(professorDB);
            response.put("status", true);
            response.put("result", updatedProfessor);
            return response;

        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Map<String, Object> delete(Long id) {
        try{
            Map<String, Object> response = new HashMap<>();
            Optional<Professor> foundProfessor = professorRepository.findById(id);
            if(foundProfessor.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe el profesor ingresado");
                return response;
            }
            professorRepository.deleteById(id);
            response.put("status", true);
            response.put("result", "Profesor eliminado con exito");
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }
}
