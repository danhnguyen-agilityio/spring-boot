package guru.springframework.sfgspringpetclinic.bootstrap;

import guru.springframework.sfgspringpetclinic.model.*;
import guru.springframework.sfgspringpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService owenerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService owenerService, VetService vetService, PetTypeService petTypeService,
                      SpecialityService specialityService, VisitService visitService) {
        this.owenerService = owenerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        // Demo builder
        Owner.builder().address("Tam Ky").build();

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        Speciality saveddentistry = specialityService.save(dentistry);

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

        Visit catVisit = new Visit();
        catVisit.setPet(fionasCat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        System.out.println("Loader owner...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Alex");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loader vet...");

    }
}
