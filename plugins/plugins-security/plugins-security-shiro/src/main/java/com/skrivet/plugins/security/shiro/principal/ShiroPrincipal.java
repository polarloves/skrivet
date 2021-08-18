package com.skrivet.plugins.security.shiro.principal;

import com.skrivet.plugins.security.core.entity.User;

import java.io.Serializable;

public class ShiroPrincipal implements Serializable {
    private User user;

    public ShiroPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
