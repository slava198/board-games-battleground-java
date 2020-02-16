package by.vyun.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String location;
    LocalDateTime dateTime;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    BoardGame game;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Set<User> members;




}
