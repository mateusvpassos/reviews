package br.com.mateus.crud.endpoint.thread;

import br.com.mateus.crud.endpoint.domain.User;

public class UserThreadLocal {
    private static ThreadLocal<User> userThread = new ThreadLocal<>();

    public static User getUser() {
        return userThread.get();
    }

    public static void setUser(User user) {
        UserThreadLocal.userThread.set(user);
    }

    public static void removeUser() {
        userThread.remove();
    }
}
