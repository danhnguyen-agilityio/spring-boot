package guru.springframework.sfgspringpetclinic.services;

import guru.springframework.sfgspringpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Vet save(Vet Pet);

    Set<Vet> findAll();
}
