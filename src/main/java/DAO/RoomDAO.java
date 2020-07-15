package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pojo.Place;
import pojo.Room;
import utils.HibernateUtils;

public class RoomDAO {
    public static ObservableList<Room> getRoomsListByPlace(Place place){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            String hql = "select r from Room r where r.place = " + place.getId();
            Query<Room> query = session.createQuery(hql);
            ObservableList<Room> list = FXCollections.observableArrayList(query.list());
            return list;
        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
}
