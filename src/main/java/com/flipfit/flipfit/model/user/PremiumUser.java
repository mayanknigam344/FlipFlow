package com.flipfit.flipfit.model.user;

import java.util.ArrayList;

public class PremiumUser extends User {
    public PremiumUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.bookings = new ArrayList<>();
        this.userType = UserType.FK_VIP_USER;
    }

}
