package guru.springframework.sfgspringpetclinic.services.map;

import guru.springframework.sfgspringpetclinic.model.Owner;
import guru.springframework.sfgspringpetclinic.model.Pet;
import guru.springframework.sfgspringpetclinic.model.PetType;
import guru.springframework.sfgspringpetclinic.services.OwnerService;
import guru.springframework.sfgspringpetclinic.services.PetService;
import guru.springframework.sfgspringpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;
    private final PetTypeService petTypeService;

    public OwnerServiceMap(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner save(Owner owner) {
        if (owner != null) {
            if (owner.getPets() != null) {
                owner.getPets().forEach(pet -> {
                    if (pet.getPetType() != null) {
                        if (pet.getPetType().getId() == null) {
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    } else {
                        throw new RuntimeException("Pet Type is required");
                    }

                    if (pet.getId() == null) {
                        Pet savedPet =petService.save(pet);
                        pet.setId(savedPet.getId());
                    }
                });
            }

            return super.save(owner);
        } else {
            return null;
        }

    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
