package guru.springframework.sfgspringpetclinic.services.springdatajpa;

import guru.springframework.sfgspringpetclinic.model.Vet;
import guru.springframework.sfgspringpetclinic.repositories.VetReository;
import guru.springframework.sfgspringpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSDJpaService implements VetService {

    private final VetReository vetReository;

    public VetSDJpaService(VetReository vetReository) {
        this.vetReository = vetReository;
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();

        vetReository.findAll().forEach(vets::add);

        return vets;
    }

    @Override
    public Vet findById(Long aLong) {
        return vetReository.findById(aLong).orElse(null);
    }

    @Override
    public Vet save(Vet object) {
        return vetReository.save(object);
    }

    @Override
    public void delete(Vet object) {
        vetReository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        vetReository.deleteById(aLong);
    }
}
