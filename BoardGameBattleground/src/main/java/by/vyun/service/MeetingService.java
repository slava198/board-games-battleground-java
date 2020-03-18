package by.vyun.service;


import by.vyun.model.BoardGame;
import by.vyun.model.Meeting;
import by.vyun.model.User;
import by.vyun.repo.BoardGameRepo;
import by.vyun.repo.MeetingRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MeetingService {
    MeetingRepo meetingRepo;


    public List<Meeting> getAllMeetings() {
        return meetingRepo.findAll();
    }

    public void createMeet(User creator, Meeting meeting) {
        meeting.setCreator(creator);
        meetingRepo.saveAndFlush(meeting);
    }





}
