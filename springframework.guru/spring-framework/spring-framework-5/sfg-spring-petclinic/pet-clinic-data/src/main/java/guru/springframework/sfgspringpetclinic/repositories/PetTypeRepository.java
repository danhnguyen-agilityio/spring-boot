package guru.springframework.sfgspringpetclinic.repositories;

import guru.springframework.sfgspringpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
