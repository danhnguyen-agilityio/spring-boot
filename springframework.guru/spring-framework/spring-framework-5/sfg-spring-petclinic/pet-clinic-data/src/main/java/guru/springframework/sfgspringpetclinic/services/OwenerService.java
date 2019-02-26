package guru.springframework.sfgspringpetclinic.services;

import guru.springframework.sfgspringpetclinic.model.Owner;

import java.util.Set;

public interface OwenerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
