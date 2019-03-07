package guru.springframework.sfgspringpetclinic.repositories;

import guru.springframework.sfgspringpetclinic.model.Specialty;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepository extends CrudRepository<Specialty, Long> {
}
