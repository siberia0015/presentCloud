package fz.cs.daoyun.utils.dto;


import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRoleWrapper {
    private User user;
    private List<String> roles = new ArrayList<String>();

    @Override
    public String toString() {
        return "UserRoleWrapper{" +
                "user=" + user +
                ", roles=" + roles +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
