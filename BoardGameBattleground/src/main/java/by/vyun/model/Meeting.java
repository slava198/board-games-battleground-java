package by.vyun.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    LocalDateTime dateTime;


    @ManyToOne(cascade = CascadeType.ALL)
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
