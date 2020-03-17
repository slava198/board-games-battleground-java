package by.vyun.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;


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
    @JsonManagedReference
    List<BoardGame> gameCollection;

    @ManyToMany
    @JoinTable(
            name = "meetings_members",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id")}
    )
    @JsonBackReference
    List<Meeting> meetingSet;


    public void addGameToCollection(BoardGame game) {
        gameCollection.add(game);
    }

    public void deleteGameFromCollection(BoardGame game) {
        gameCollection.remove(game);

    }



}
