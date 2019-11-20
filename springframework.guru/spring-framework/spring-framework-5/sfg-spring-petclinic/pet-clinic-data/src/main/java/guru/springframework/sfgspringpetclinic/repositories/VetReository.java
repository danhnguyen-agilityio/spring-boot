package guru.springframework.sfgspringpetclinic.repositories;

import guru.springframework.sfgspringpetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetReository extends CrudRepository<Vet, Long> {
}
