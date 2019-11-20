package guru.springframework.sfgspringpetclinic.repositories;

import guru.springframework.sfgspringpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
