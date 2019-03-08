package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    // A large object for the recipeNotes that will get a clob field inside
    // to persist the Notes and without having to worry about limitations on the size of that String
    @Lob
    private String recipeNotes;

}
