package ase.service.user;

import ase.domain.Article;
import ase.domain.Meeting;
import ase.domain.PCMemberRelation;
import ase.repository.*;
import ase.utility.contract.MeetingStatus;
import ase.utility.contract.PCmemberRelationStatus;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserMeetingService {
    private UserRepository userRepository;

    @Autowired
    public UserMeetingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseWrapper<?> chairMeeting(String username) {
        // todo meeting api
//        List<Meeting> meetingList = meetingRepository.findByChairName(username);
        List<Meeting> meetingList = new ArrayList<>();

        HashMap<String, Set<HashMap<String, Object>>> body = new HashMap<>();
        Set<HashMap<String, Object>> response = new HashSet<>();
        for (Meeting meeting : meetingList) {
            HashMap<String, Object> meetingInfo = ResponseGenerator.generate(meeting,
                    new String[]{"meetingName", "acronym", "conferenceDate", "topic"}, null);
            response.add(meetingInfo);
        }
        body.put("meetings", response);
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }

    @Transactional
    public ResponseWrapper<?> pcMemberMeeting(String username) {
        Long userId = userRepository.findByUsername(username).getId();

        // todo pcmemberrelation api
//        List<PCMemberRelation> relationList = pcMemberRelationRepository.findByPcmemberIdAndStatus(userId, PCmemberRelationStatus.accepted);
        List<PCMemberRelation> relationList = new ArrayList<>();

        HashMap<String, Set<HashMap<String, Object>>> body = new HashMap<>();
        Set<HashMap<String, Object>> response = new HashSet<>();
        for (PCMemberRelation relation : relationList) {

            // todo meeting api
//            Meeting meeting = meetingRepository.findById((long)relation.getMeetingId());
            Meeting meeting = null;

            HashMap<String, Object> meetingInfo = ResponseGenerator.generate(meeting,
                    new String[]{"meetingName", "acronym", "conferenceDate", "topic"}, null);
            response.add(meetingInfo);
        }
        body.put("meetings", response);
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }

    @Transactional
    public ResponseWrapper<?> authorMeeting(String username) {
        // todo article api
//        List<Article> articleList = articleRepository.findByContributorName(username);
        List<Article> articleList = new ArrayList<>();

        HashMap<String, Set<HashMap<String, Object>>> body = new HashMap<>();
        Set<HashMap<String, Object>> response = new HashSet<>();
        Set<Long> meetingCount = new HashSet<>();
        for (Article article : articleList) {
            // todo meeting api
//            Meeting meeting = meetingRepository.findByMeetingName(article.getMeetingname());
            Meeting meeting = null;

            if (meeting != null && !meetingCount.contains(meeting.getId())) {
                meetingCount.add(meeting.getId());
                response.add(ResponseGenerator.generate(meeting,
                        new String[]{"meetingName", "acronym", "submissionDeadlineDate", "topic"}, null));
            }
        }
        body.put("meetings", response);
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }

    @Transactional
    public ResponseWrapper<?> availableMeeting(String username) {
        // todo meeting api
//        List<Meeting> allMeeting = meetingRepository.findByStatusAndChairNameNot(MeetingStatus.submissionAvaliable, username);
        List<Meeting> allMeeting = new ArrayList<>();

        HashMap<String, Set<HashMap<String, Object>>> body = new HashMap<>();
        Set<HashMap<String, Object>> response = new HashSet<>();
        for (Meeting meeting : allMeeting) {
            response.add(ResponseGenerator.generate(meeting,
                    new String[]{"meetingName", "acronym", "submissionDeadlineDate", "topic"}, null));
        }
        body.put("meetings", response);
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }
}
