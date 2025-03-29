package com.flipfit.flipfit.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {
    String userId;
    String userName;
    UserType userType;
}
