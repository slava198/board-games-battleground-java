package by.vyun.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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

public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String location;
    String dateTime;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "meet_creators",
            joinColumns = {@JoinColumn(name = "meet_id")},
            inverseJoinColumns = {@JoinColumn(name = "creator_id")}
    )
    User creator;


    @ManyToOne()
    @JoinColumn(name = "game_id")
    BoardGame game;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "meetings_members",
            joinColumns = {@JoinColumn(name = "meeting_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonManagedReference
    List<User> members;




}
