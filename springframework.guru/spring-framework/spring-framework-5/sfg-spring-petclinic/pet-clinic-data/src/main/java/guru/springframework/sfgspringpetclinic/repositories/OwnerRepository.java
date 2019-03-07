package guru.springframework.sfgspringpetclinic.repositories;

import guru.springframework.sfgspringpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
