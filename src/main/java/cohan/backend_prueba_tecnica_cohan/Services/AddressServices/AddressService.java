package cohan.backend_prueba_tecnica_cohan.Services.AddressServices;

import cohan.backend_prueba_tecnica_cohan.Models.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {

    List<Address> findAll();

    Map<String, Object> save(Address address);

    Map<String, Object> update(Address address, Long id);

    Map<String, Object> delete(Long id);
}
