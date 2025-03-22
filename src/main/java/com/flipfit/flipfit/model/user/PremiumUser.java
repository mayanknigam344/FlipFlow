package com.flipfit.flipfit.model.user;

public class PremiumUser extends User {
    public PremiumUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.userType = UserType.FK_VIP_USER;
    }
}
