package com.flipfit.flipfit.model.user;

import java.util.ArrayList;

public class NormalUser extends User {
    public NormalUser(Long userId, String userName){
        this.userId = userId;
        this.userName = userName;
        this.bookings = new ArrayList<>();
        this.userType = UserType.FK_NORMAL_USER;
    }
}
