package pojo;



import DTO.ConferenceDetailDTO;
import utils.Utils;

import java.sql.Timestamp;

public class Conference {
    private int id;
    private String name;
    private Room room;
    private String generalDesc;
    private String detailDesc;
    private Timestamp time;
    private int maxNumberAttendees;

    public Conference(){}

    public Conference(int id,String name, Room room, String generalDesc, String detailDesc,
                      Timestamp time, int maxNumberAttendees) {
        this.id =id;
        this.name = name;
        this.room = room;
        this.generalDesc = generalDesc;
        this.detailDesc = detailDesc;
        this.time = time;
        this.maxNumberAttendees = maxNumberAttendees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    public String getGeneralDesc() {
        return generalDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getMaxNumberAttendees() {
        return maxNumberAttendees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setGeneralDesc(String generalDesc) {
        this.generalDesc = generalDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setMaxNumberAttendees(int numberAttendees) {
        this.maxNumberAttendees = numberAttendees;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", room=" + room +
                ", generalDesc='" + generalDesc + '\'' +
                ", detailDesc='" + detailDesc + '\'' +
                ", time=" + time +
                ", maxNumberAttendees=" + maxNumberAttendees +
                '}';
    }

    public ConferenceDetailDTO getConferenceDetail(){
        String place = Utils.convertUTF8IntoString("Sảnh: ")+ room.getName()+
                ", "+room.getPlace().getName();
         String add = room.getPlace().getAddress();
        String newTime = Utils.TimeFormat(time);

        return new ConferenceDetailDTO(id,name,place,add,generalDesc,
                detailDesc,newTime,maxNumberAttendees);

    }
}
