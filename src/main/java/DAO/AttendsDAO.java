package DAO;

import DTO.ApprovalDTO;
import DTO.ConferenceDetailDTO;
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

public class AttendsDAO {

    public static int getMaxIdAttends(){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            String hql = "select max(a.id) from Attends a";
            Query<Integer> query = session.createQuery(hql);
            session.getTransaction().commit();
            return query.list().get(0);

        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
            return -1;
        }
    }
    public static void insertAttends(User user, Conference conference){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            int id = getMaxIdAttends()+1;
            if (id!=0) {
                Attends attends = new Attends(id, user, conference, 0);
                session.save(attends);
            }
            session.getTransaction().commit();
        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    public static void updateAppvoralAttends(int id, int approval){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            String hql = "update Attends a set a.approval = :approval where a.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("approval",approval);
            query.setParameter("id",id);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    public static int checkAttend(User user, Conference conference){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            String hql= "select a from Attends a where a.user = " + user.getId() +
                    " and a.conference = " + conference.getId();
            Query<Attends> query = session.createQuery(hql);
            List<Attends> list = query.list();
            if (list.size()==0) return 0;
            else return list.get(0).getApproval();
        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
            return 0;
        }
    }

    public static List<Attends> getAttendsByUser(User user){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        List<Attends> list = new ArrayList<>();
        try{
            session.getTransaction().begin();
            String hql= "select a from Attends a where a.user = " + user.getId();
            Query<Attends> query = session.createQuery(hql);
            list= query.list();
            return list;

        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    public static long getNumberAttendeesForConference(Conference con){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            String hql= "select count(a.id) from Attends a where a.conference= " + con.getId() +
                    " and a.approval=1";
            Query<Long> query = session.createQuery(hql);
            return query.list().get(0);

        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
            return 0;
        }
    }

    public static List<Attends> getAttendsListNotApproval() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            String hql = "select a from Attends a where a.approval =0";
            Query<Attends> query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }


    public static ObservableList<ApprovalDTO> getAttendsListNotApprovalDetail(){
        List<Attends> cfrList= getAttendsListNotApproval();
        List<ApprovalDTO> cfrFullList = new ArrayList<>();
        for(Attends i :cfrList){
            cfrFullList.add(i.getAttendsDetail());
        }
        return FXCollections.observableArrayList(cfrFullList);
    }

}
