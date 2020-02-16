package by.vyun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    int age;
    double rating;

    @ManyToMany(mappedBy = "gameCollection")
    Set<User> owners;

    @OneToMany(mappedBy = "game")
    Set<Meeting> meetings;


}
