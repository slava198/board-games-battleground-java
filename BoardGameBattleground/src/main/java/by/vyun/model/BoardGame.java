package by.vyun.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"owners", "meetings"})
@EqualsAndHashCode(exclude = {"owners", "meetings"})
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String logo;
    String title;
    String description;
    Integer age = 0;
    Integer rating = 0;

    public Integer getNumberOfOwners() {
        if(owners == null) {
            return 0;
        }
        return owners.size();
    }

    public Integer getNumberOfMeetings() {
        if(meetings == null) {
            return 0;
        }
        return meetings.size();
    }

//    @ManyToMany(mappedBy = "gameCollection")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "games_owners",
        joinColumns = {@JoinColumn(name = "game_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    Set<User> owners;

    @OneToMany(mappedBy = "game")
    Set<Meeting> meetings;

    public void clearOwnersList() {
        owners.clear();

    }




}
