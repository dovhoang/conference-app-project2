package pojo;

import DTO.ApprovalDTO;
import DTO.ConferenceDetailDTO;
import DTO.MyConferencesDTO;
import DTO.UserInfoInConferenceDTO;
import javafx.scene.control.Button;
import utils.Utils;

import java.sql.Timestamp;

public class Attends {
    private int id;
    private User user;
    private Conference conference;
    private int approval;

    public  Attends(){};

    public Attends(int id, User user, Conference conference, int approval) {
        this.id = id;
        this.user = user;
        this.conference = conference;
        this.approval = approval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }

    public MyConferencesDTO getMyConference(){
        String place = Utils.convertUTF8IntoString("Sảnh: ")+
                conference.getRoom().getName()+
                ", "+conference.getRoom().getPlace().getName();
        String time = Utils.TimeFormat(conference.getTime());
        String status;
        Timestamp cur = new Timestamp(System.currentTimeMillis());
        if (approval==1){
            if (conference.getTime().after(cur))
            status = Utils.convertUTF8IntoString("Đã duyệt");
            else status = Utils.convertUTF8IntoString("Đã tham gia");
        }else if (approval==0) status = Utils.convertUTF8IntoString("Đang chờ duyệt");
        else status = Utils.convertUTF8IntoString("Từ chối");
        return new MyConferencesDTO(id,conference.getId(),conference.getName(),
                time,place,status);

    }

    public ApprovalDTO getAttendsDetail(){
        return new ApprovalDTO(id,user.getUsername(),user.getName(),conference.getName());
    }

    public UserInfoInConferenceDTO getUserInfoInConference(){
        return new UserInfoInConferenceDTO(user.getName(), user.getUsername());
    }
}
