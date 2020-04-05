package by.vyun.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@ToString(exclude = {"gameCollection", "meetingSet", "createdMeets"})
@EqualsAndHashCode(exclude = {"gameCollection", "meetingSet", "createdMeets"})
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String login;
    String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;
    String location;
    Integer rating = 0;
//    @Column(columnDefinition = "true")
    Boolean isActive = true;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "games_owners",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")}
    )
    List<BoardGame> gameCollection;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "meetings_members",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id")}
    )
    List<Meeting> meetingSet;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    List<Meeting> createdMeets;



    public boolean checkPassword(String password) {
        return this.getPassword().equals(password);
    }

    public void addGameToCollection(BoardGame game) {
        gameCollection.add(game);
    }

    public void deleteGameFromCollection(BoardGame game) {
        gameCollection.remove(game);

    }

    public void addMeeting(Meeting meeting) {
        meetingSet.add(meeting);
    }

    public void leaveMeeting(Meeting meeting) {
        meetingSet.remove(meeting);
    }

    public void deleteMeeting(Meeting meeting) {
        meetingSet.remove(meeting);
        createdMeets.remove(meeting);
    }

}
