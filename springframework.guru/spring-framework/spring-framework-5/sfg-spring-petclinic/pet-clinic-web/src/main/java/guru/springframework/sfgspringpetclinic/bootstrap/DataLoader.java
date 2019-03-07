package guru.springframework.sfgspringpetclinic.bootstrap;

import guru.springframework.sfgspringpetclinic.model.Owner;
import guru.springframework.sfgspringpetclinic.model.PetType;
import guru.springframework.sfgspringpetclinic.model.Vet;
import guru.springframework.sfgspringpetclinic.services.OwnerService;
import guru.springframework.sfgspringpetclinic.services.PetTypeService;
import guru.springframework.sfgspringpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService owenerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService owenerService, VetService vetService, PetTypeService petTypeService) {
        this.owenerService = owenerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michale");
        owner1.setLastName("Westen");

        owenerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenas");

        owenerService.save(owner2);

        System.out.println("Loader owner...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Alex");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vetService.save(vet2);

        System.out.println("Loader vet...");
    }
}
