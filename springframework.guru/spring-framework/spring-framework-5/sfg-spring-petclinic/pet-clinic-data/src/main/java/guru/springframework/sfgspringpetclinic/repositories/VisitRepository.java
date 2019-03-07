package guru.springframework.sfgspringpetclinic.repositories;

import guru.springframework.sfgspringpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
