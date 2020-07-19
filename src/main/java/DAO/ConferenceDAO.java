package DAO;

import DTO.ConferenceDetailDTO;
import DTO.MyConferencesDTO;
import global.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pojo.Attends;
import pojo.Conference;
import pojo.User;
import utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDAO {

    public static List<Conference> getConferenceList(){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        List<Conference> list = new ArrayList<>();
        try {
            session.getTransaction().begin();
            String hql = "from Conference";
            Query<Conference> query = session.createQuery(hql);
            list = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return list;
    }

    public static boolean insertConference(Conference con){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.save(con);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }

    public static Conference getConferenceById(int id){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        List<Conference> list = new ArrayList<>();
        try {
            session.getTransaction().begin();
            String hql = "select c from Conference c where c.id =" + id ;
            Query<Conference> query = session.createQuery(hql);
            list = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return list.get(0);
    }

    public static int getMaxId(){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            String hql = "select max(c.id) from Conference c";
            Query<Integer> query = session.createQuery(hql);
            return query.list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return -1;
    }

    public static ObservableList<MyConferencesDTO> getMyConferencesByUser(){
        User user = UserSession.getInstance().getUser();
        List<Attends> attendsList= AttendsDAO.getAttendsByUser(user);
        List<MyConferencesDTO> cfrFullList = new ArrayList<>();
        for(Attends i :attendsList){
            cfrFullList.add(i.getMyConference());
        }
        return FXCollections.observableArrayList(cfrFullList);
    }

    public static ObservableList<MyConferencesDTO> searchMyConference(String keyword){
        User user = UserSession.getInstance().getUser();
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        List<Attends> attendsList = AttendsDAO.getAttendsByUser(user);
        List<MyConferencesDTO> cfrFullList = new ArrayList<>();
        int id=0;
        try{
            id = Integer.parseInt(keyword);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        for(Attends i :attendsList){
            if (i.getConference().getName().toLowerCase().contains(keyword.toLowerCase())
                    || i.getId() == id
                    || i.getConference().getRoom().getPlace().getName().toLowerCase()
                    .contains(keyword.toLowerCase())
                    || i.getConference().getRoom().getName().toLowerCase()
                    .contains(keyword.toLowerCase())){
                cfrFullList.add(i.getMyConference());
            }
        }
        return FXCollections.observableArrayList(cfrFullList);
    }


    public static ConferenceDetailDTO getConferenceDetailById(int id){
        Conference cfr= getConferenceById(id);
        return cfr.getConferenceDetail();
    }

    public static ObservableList<ConferenceDetailDTO> getConferencesDetail(){
        List<Conference> cfrList= getConferenceList();
        List<ConferenceDetailDTO> cfrFullList = new ArrayList<>();
        for(Conference i :cfrList){
            cfrFullList.add(i.getConferenceDetail());
        }
        return FXCollections.observableArrayList(cfrFullList);
    }

    public static void updateConference(Conference newcfr) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            session.update(newcfr);
            session.getTransaction().commit();
        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    public static ObservableList<ConferenceDetailDTO> searchConference(String keyword){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        List<Conference> list = new ArrayList<>();
        List<ConferenceDetailDTO> cfrFullList = new ArrayList<>();
        int id=0;
        try{
            id = Integer.parseInt(keyword);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        try {
            session.getTransaction().begin();
            String hql = "select c from Conference c where c.name like '%"+keyword+"%' or c.id = "+id;
            Query<Conference> query = session.createQuery(hql);
            list = query.list();
            for(Conference i :list){
                cfrFullList.add(i.getConferenceDetail());
            }
            return FXCollections.observableArrayList(cfrFullList);
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }

    }


}
