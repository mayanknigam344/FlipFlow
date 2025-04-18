package com.flipflow.model.user;

import java.util.ArrayList;

public class NormalUser extends User {
    public NormalUser(String userId, String userName){
        this.userId = userId;
        this.userName = userName;
        this.userType = UserType.FK_NORMAL_USER;
        this.bookings = new ArrayList<>();
    }
}
