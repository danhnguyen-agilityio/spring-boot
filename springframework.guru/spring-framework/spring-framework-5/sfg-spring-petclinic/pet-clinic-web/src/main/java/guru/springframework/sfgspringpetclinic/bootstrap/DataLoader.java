package guru.springframework.sfgspringpetclinic.bootstrap;

import guru.springframework.sfgspringpetclinic.model.Owner;
import guru.springframework.sfgspringpetclinic.model.Pet;
import guru.springframework.sfgspringpetclinic.model.PetType;
import guru.springframework.sfgspringpetclinic.model.Vet;
import guru.springframework.sfgspringpetclinic.services.OwnerService;
import guru.springframework.sfgspringpetclinic.services.PetTypeService;
import guru.springframework.sfgspringpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        owner1.setAddress("123 London");
        owner1.setCity("Miami");
        owner1.setTelephone("123321233");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");
        owner1.getPets().add(mikesPet);

        owenerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenas");
        owner1.setAddress("123 Brish");
        owner1.setCity("Miami");
        owner1.setTelephone("123321233");

        Pet fionasCat = new Pet();
        fionasCat.setPetType(savedCatPetType);
        fionasCat.setOwner(owner2);
        fionasCat.setBirthDate(LocalDate.now());
        fionasCat.setName("Leader");
        owner2.getPets().add(fionasCat);

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
