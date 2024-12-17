package cohan.backend_prueba_tecnica_cohan.Services.StudentServices;

import cohan.backend_prueba_tecnica_cohan.Models.Address;
import cohan.backend_prueba_tecnica_cohan.Models.Person;
import cohan.backend_prueba_tecnica_cohan.Models.Student;
import cohan.backend_prueba_tecnica_cohan.Repositories.AddressRepository;
import cohan.backend_prueba_tecnica_cohan.Repositories.PersonRepository;
import cohan.backend_prueba_tecnica_cohan.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findStudent( Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Student> currentStudent = studentRepository.findById(id);
            if(currentStudent.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe el estudiante ingresado");
                return response;
            }
            response.put("status", true);
            response.put("result", currentStudent.get());
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Map<String, Object> save(Student student) {
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Person> personFoundByEmailAddress = personRepository.findByEmailAddress(student.getEmailAddress());
            if (personFoundByEmailAddress.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe una persona registrada con esta direccion de correo");
                return response;
            }

            Optional<Person> personFoundByPhoneNumber = personRepository.findByPhoneNumber(student.getPhoneNumber());
            if (personFoundByPhoneNumber.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe una persona registrada con este numero de celular");
                return response;
            }

            Optional<Student> studentFoundByStudentNumber = studentRepository.findByStudentNumber(student.getStudentNumber());
            if (studentFoundByStudentNumber.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe un estudiante registrado con esta identificacion");
                return response;
            }

            Optional<Address> foundAddress = addressRepository.findById(student.getAddress().getId());
            if(foundAddress.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe la direccion ingresada");
                return response;
            }
            Optional<Person> foundUsedAddress = personRepository.findByAddress_Id(student.getAddress().getId());
            if(foundUsedAddress.isPresent()){
                response.put("status", false);
                response.put("result", "Ya existe una persona registrada en esta direccion");
                return response;
            }
            Student newStudent = studentRepository.save(student);
            response.put("status", true);
            response.put("result", newStudent);
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Map<String, Object> update(Student student, Long id) {
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Student> currentStudent = studentRepository.findById(id);
            if(currentStudent.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe el estudiante ingresado");
                return response;
            }

            Optional<Person> personFoundByEmailAddress = personRepository.findByEmailAddress(student.getEmailAddress());
            if (personFoundByEmailAddress.isPresent()){

                if(!personFoundByEmailAddress.get().getEmailAddress().equals(currentStudent.get().getEmailAddress())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada con esta direccion de correo");
                    return response;
                }
            }

            Optional<Person> personFoundByPhoneNumber = personRepository.findByPhoneNumber(student.getPhoneNumber());
            if (personFoundByPhoneNumber.isPresent()){
                if(!personFoundByPhoneNumber.get().getPhoneNumber().equals(currentStudent.get().getPhoneNumber())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada con este numero de celular");
                    return response;
                }
            }

            Optional<Student> studentFoundByStudentNumber = studentRepository.findByStudentNumber(student.getStudentNumber());
            if (studentFoundByStudentNumber.isPresent()){
                if(!studentFoundByStudentNumber.get().getStudentNumber().equals(currentStudent.get().getStudentNumber())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada con esta identificacion");
                    return response;
                }

            }

            Optional<Address> foundAddress = addressRepository.findById(student.getAddress().getId());
            if(foundAddress.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe la direccion ingresada");
                return response;
            }
            Optional<Person> personFoundByAddressId = personRepository.findByAddress_Id(student.getAddress().getId());
            if(personFoundByAddressId.isPresent()){
                if(!currentStudent.get().getAddress().getId().equals(student.getAddress().getId())){
                    response.put("status", false);
                    response.put("result", "Ya existe una persona registrada en esta direccion");
                    return response;
                }
            }

            Student studentDB = currentStudent.get();

            studentDB.setStudentNumber(student.getStudentNumber());
            studentDB.setAverageMark(student.getAverageMark());
            studentDB.setAddress(student.getAddress());
            studentDB.setEmailAddress(student.getEmailAddress());
            studentDB.setPhoneNumber(student.getPhoneNumber());
            studentDB.setName(student.getName());

            Student updatedStudent = studentRepository.save(studentDB);
            response.put("status", true);
            response.put("result", updatedStudent);
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
            Optional<Student> foundStudent = studentRepository.findById(id);
            if(foundStudent.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe el estudiante ingresado");
                return response;
            }
            studentRepository.deleteById(id);
            addressRepository.deleteById(foundStudent.get().getAddress().getId());
            response.put("status", true);
            response.put("result", "Estudiante eliminado con exito");
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

}
