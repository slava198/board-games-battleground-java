package by.vyun.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
@ToString(exclude = {"gameCollection", "meetingSet"})
@EqualsAndHashCode(exclude = {"gameCollection", "meetingSet"})
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@userId")
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_owners",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")}
    )
    //@JsonManagedReference
    List<BoardGame> gameCollection;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "meetings_members",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id")}
    )
    //@JsonBackReference
    List<Meeting> meetingSet;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(referencedColumnName = "id")
    @JoinTable(
            name = "meet_creators",
            joinColumns = {@JoinColumn(name = "creator_id")},
            inverseJoinColumns = {@JoinColumn(name = "meet_id")}
    )
    List<Meeting> createdMeets;





    public void addGameToCollection(BoardGame game) {
        gameCollection.add(game);
    }

    public void deleteGameFromCollection(BoardGame game) {
        gameCollection.remove(game);

    }

    public void addMeeting(Meeting meeting) {
        meetingSet.add(meeting);
    }

    public void deleteMeeting(Meeting meeting) {
        meetingSet.remove(meeting);

    }

}
