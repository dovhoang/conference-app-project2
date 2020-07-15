package pojo;

public class Room {
    private int id;
    private String name;
    private Place place;
    private int capacity;

    public Room(){};

    public Room(int id, String name, Place place, int capacity) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
