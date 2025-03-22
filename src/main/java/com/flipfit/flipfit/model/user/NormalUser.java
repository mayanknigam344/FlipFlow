package com.flipfit.flipfit.model.user;

public class NormalUser extends User {
    public NormalUser(Long userId, String userName){
        this.userId = userId;
        this.userName = userName;
        this.userType = UserType.FK_NORMAL_USER;
    }
}
