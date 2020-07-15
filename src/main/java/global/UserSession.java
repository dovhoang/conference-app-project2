package global;

import pojo.User;

public final class UserSession {

    private static UserSession instance;

    private static User user;

    private UserSession(User user) {
        this.user = user;
    }

    public static UserSession getInstace(User user) {
        if(instance == null) {
            instance = new UserSession(user);
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static boolean isLogin(){
        return instance != null;
    }

    public static void cleanUserSession() {
        instance = null;
        user = null;
    }
}