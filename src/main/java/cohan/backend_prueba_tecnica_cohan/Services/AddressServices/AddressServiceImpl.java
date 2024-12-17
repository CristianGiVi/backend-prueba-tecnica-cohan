package cohan.backend_prueba_tecnica_cohan.Services.AddressServices;

import cohan.backend_prueba_tecnica_cohan.Models.Address;
import cohan.backend_prueba_tecnica_cohan.Models.Professor;
import cohan.backend_prueba_tecnica_cohan.Models.Student;
import cohan.backend_prueba_tecnica_cohan.Repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Address> findAll() {
        return (List<Address>) addressRepository.findAll();
    }

    @Transactional
    @Override
    public Map<String, Object> save(Address address) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (validateUniqueAddress(address.getPostalCode(), address.getStreet(), address.getCountry())){
                response.put("status", false);
                response.put("result", "La direccion ya esta registrada");
                return response;
            }

            Address newAddress = addressRepository.save(address);
            response.put("status", true);
            response.put("result", newAddress);
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Map<String, Object> update(Address address, Long id) {
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Address> currentAddress = addressRepository.findById(id);
            if(currentAddress.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe la direccion ingresada");
                return response;
            }

            Address addressDB = currentAddress.get();

            addressDB.setCountry(address.getCountry());
            addressDB.setState(address.getState());
            addressDB.setCity(address.getCity());
            addressDB.setStreet(address.getStreet());
            addressDB.setPostalCode(address.getPostalCode());

            Address updatedAddress = addressRepository.save(addressDB);
            response.put("status", true);
            response.put("result", updatedAddress);

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
            Optional<Address> foundAddress = addressRepository.findById(id);
            if(foundAddress.isEmpty()){
                response.put("status", false);
                response.put("result", "No existe la direccion ingresada");
                return response;
            }
            addressRepository.deleteById(id);
            response.put("status", true);
            response.put("result", "Direcccion eliminada con exito");
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Ocurrió un error interno: " + e.getMessage());
        }
    }

    public boolean validateUniqueAddress(String postalCode, String street, String country){
        Optional<Address> foundAddress = addressRepository.findByPostalCodeAndStreetAndCountry(postalCode, street, country);
        return foundAddress.isPresent();
    }
}
