package by.vyun.model;

import lombok.*;
//import sun.util.resources.Bundles;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"gameCollection", "meetingSet"})
@EqualsAndHashCode(exclude = {"gameCollection", "meetingSet"})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String login;
    String password;
    Integer age = 0;
    String location;
    Integer rating = 0;
    //    @JoinColumn(name = "game_id")

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_owners",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")}
    )
    List<BoardGame> gameCollection;

    @ManyToMany(mappedBy = "members")
    Set<Meeting> meetingSet;

    public void addGameToCollection(BoardGame game) {
        gameCollection.add(game);
    }


    public void deleteGameById(Integer id) {

    }



}
