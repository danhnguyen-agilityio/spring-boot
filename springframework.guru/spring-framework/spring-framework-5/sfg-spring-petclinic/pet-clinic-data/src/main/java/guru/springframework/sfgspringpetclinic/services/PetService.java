package guru.springframework.sfgspringpetclinic.services;

import guru.springframework.sfgspringpetclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Pet Pet);

    Set<Pet> findAll();
}
