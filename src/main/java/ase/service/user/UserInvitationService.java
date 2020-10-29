package ase.service.user;

import ase.domain.Meeting;
import ase.domain.PCMemberRelation;
import ase.repository.*;
import ase.request.user.InvitationRepoRequest;
import ase.utility.contract.PCmemberRelationStatus;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInvitationService {
    private UserRepository userRepository;

    @Autowired
    public UserInvitationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseWrapper<?> undealedNotifications(String username) {
        Long userId = userRepository.findByUsername(username).getId();
        // todo PCMemberRelation Api
        //List<PCMemberRelation> relationList = pcMemberRelationRepository.findByPcmemberIdAndStatus(userId, PCmemberRelationStatus.undealed);
        List<PCMemberRelation> relationList = new ArrayList<>();
        HashMap<String, Set<HashMap<String, Object>>> body = new HashMap<>();
        Set<HashMap<String, Object>> response = new HashSet<>();
        for (PCMemberRelation relation : relationList) {
            // todo meeting api
//            Meeting meeting = meetingRepository.findById((long) relation.getMeetingId());
            Meeting meeting = null;
            HashMap<String, Object> invitationInfo = ResponseGenerator.generate(meeting,
                    new String[]{"meetingName", "chairName", "topic"}, null);
            response.add(invitationInfo);
        }
        body.put("invitations", response);
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }

    public ResponseWrapper<?> invitationRepo(InvitationRepoRequest request) {
        Long userId = userRepository.findByUsername(request.getUsername()).getId();
        // todo meeting api
//        Long meetingId = meetingRepository.findByMeetingName(request.getMeetingName()).getId();
        Long meetingId = 0L;
        // todo PCMemberRelation Api
//        List<PCMemberRelation> relationList = pcMemberRelationRepository.findByPcmemberIdAndMeetingId(userId, meetingId);
        List<PCMemberRelation> relationList = new ArrayList<>();

        for (PCMemberRelation relation : relationList) {
            if (relation.getStatus().equals(PCmemberRelationStatus.undealed)) {
                relation.setStatus(request.getResponse());
                if (request.getResponse().equals(PCmemberRelationStatus.accepted)) {
                    relation.setTopic(request.getTopics());
                }
                // todo PCMemberRelation Api
                // pcMemberRelationRepository.save(relation);
                break;
            }
        }
        return new ResponseWrapper<>(200, ResponseGenerator.success, null);
    }

    public ResponseWrapper<?> undealedNotificationsNum(String username) {
        Long userId = userRepository.findByUsername(username).getId();
        // todo PCMemberRelation Api
//        List<PCMemberRelation> relationList = pcMemberRelationRepository.findByPcmemberIdAndStatus(userId, PCmemberRelationStatus.undealed);
        List<PCMemberRelation> relationList = new ArrayList<>();

        HashMap<String, Object> body = new HashMap<>();
        body.put("undealedNotificationsNum", relationList.size());
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }

    public ResponseWrapper<?> alreadyDealedNotifications(String username) {
        Long userId = userRepository.findByUsername(username).getId();
        // todo PCMemberRelation Api
        //List<PCMemberRelation> relationList1 = pcMemberRelationRepository.findByPcmemberIdAndStatusNot(userId, PCmemberRelationStatus.undealed);
        List<PCMemberRelation> relationList1 = new ArrayList<>();

        HashMap<String, Set<HashMap<String, Object>>> body = new HashMap<>();
        Set<HashMap<String, Object>> response = new HashSet<>();
        for (PCMemberRelation relation : relationList1) {
            HashMap<String, Object> invitaionInfo = ResponseGenerator.generate(relation,
                    new String[]{"status"}, null);

            //todo meeting api
            //Meeting m =  meetingRepository.findById((long) relation.getMeetingId());
            Meeting m = null;

            invitaionInfo.put("meetingName", m.getMeetingName());
            invitaionInfo.put("chairName", m.getChairName());
            response.add(invitaionInfo);
        }
        body.put("alreadyDealedNotifications", response);
        return new ResponseWrapper<>(200, ResponseGenerator.success, body);
    }

}
