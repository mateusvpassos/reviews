package br.com.mateus.crud.endpoint.thread;

import br.com.mateus.crud.endpoint.domain.User;

public class UserThreadLocalUtil {
    private static ThreadLocal<User> userThread = new ThreadLocal<>();

    public static User getUser() {
        return userThread.get();
    }

    public static void setUser(final User user) {
        UserThreadLocalUtil.userThread.set(user);
    }

    public static void removeUser() {
        userThread.remove();
    }
}
