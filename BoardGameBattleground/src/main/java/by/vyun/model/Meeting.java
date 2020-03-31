package by.vyun.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"members"})
@EqualsAndHashCode(exclude = {"members"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@meetingId")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String location;
    String dateTime;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(referencedColumnName = "id")
    @JoinTable(
            name = "meet_creators",
            joinColumns = {@JoinColumn(name = "meet_id")},
            inverseJoinColumns = {@JoinColumn(name = "creator_id")}
    )
    User creator;


    @ManyToOne()
    @JoinColumn(name = "game_id")
    //@JsonBackReference
    BoardGame game;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "meetings_members",
            joinColumns = {@JoinColumn(name = "meeting_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    //@JsonManagedReference
    List<User> members;

    public int getNumberOfMembers() {
        if(members == null) {
            return 0;
        }
        return members.size();
    }




}
