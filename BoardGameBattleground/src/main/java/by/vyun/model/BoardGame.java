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
        return owners.size();
    }

    public Integer getNumberOfMeetings() {
        return meetings.size();
    }

    @ManyToMany(mappedBy = "gameCollection")
    Set<User> owners;

    @OneToMany(mappedBy = "game")
    Set<Meeting> meetings;




}
