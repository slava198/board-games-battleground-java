package by.vyun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import sun.util.resources.Bundles;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String login;
    String password;
    int age;
    String location;
    double rating;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    Set<BoardGame> gameCollection;

    @ManyToMany(mappedBy = "members")
    Set<Meeting> meetingSet;


}
