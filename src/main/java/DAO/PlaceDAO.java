package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pojo.Place;
import utils.HibernateUtils;

import java.util.List;

public class PlaceDAO {
    public static ObservableList<Place> getPlacesList(){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.openSession();
        try{
            session.getTransaction().begin();
            String hql = "from Place";
            Query<Place> query = session.createQuery(hql);
            ObservableList<Place> list = FXCollections.observableArrayList(query.list());
            return list;
        }catch (Exception e ){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
}
