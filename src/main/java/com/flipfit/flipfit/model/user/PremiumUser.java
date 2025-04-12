package com.flipfit.flipfit.model.user;

import java.util.ArrayList;

public class PremiumUser extends User {
    public PremiumUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.userType = UserType.FK_VIP_USER;
        this.bookings = new ArrayList<>();
    }
}
