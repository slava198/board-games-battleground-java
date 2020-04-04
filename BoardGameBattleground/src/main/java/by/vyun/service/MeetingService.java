package by.vyun.service;


import by.vyun.model.BoardGame;
import by.vyun.model.Meeting;
import by.vyun.model.User;
import by.vyun.repo.BoardGameRepo;
import by.vyun.repo.MeetingRepo;
import by.vyun.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MeetingService {
    MeetingRepo meetingRepo;
    UserRepo userRepo;


    public List<Meeting> getAllMeetings() {
        return meetingRepo.findAll();
    }

    public Meeting getMeetingById(int id) {
        return meetingRepo.getFirstById(id);
    }



    public void createMeet(int userId, Meeting meeting) {
        meeting.setCreator(userRepo.getFirstById(userId));
        meetingRepo.saveAndFlush(meeting);
    }

    public void removeMeet(int id) {
        meetingRepo.deleteById(id);
        meetingRepo.flush();
    }





}
