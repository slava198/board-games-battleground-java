package by.vyun.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"owners", "meetings"})
@EqualsAndHashCode(exclude = {"owners", "meetings"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@boardGameId")
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String logo;
    String title;
    String description;
    Integer age = 0;
    Integer rating = 0;
    boolean active = true;

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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "games_owners",
        joinColumns = {@JoinColumn(name = "game_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    //@JsonBackReference
    Set<User> owners;

    @OneToMany(mappedBy = "game")
    //@JsonManagedReference
    Set<Meeting> meetings;

    public void clearOwnersList() {
        owners.clear();

    }




}
