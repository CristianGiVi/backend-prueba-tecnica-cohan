package cohan.backend_prueba_tecnica_cohan.Repositories;

import cohan.backend_prueba_tecnica_cohan.Models.Address;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findByPostalCodeAndStreetAndCountry(String postalCode, String street, String country);
}
