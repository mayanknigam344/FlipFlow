package com.flipfit.flipfit.exception;

public class UserBookingMaxLimitReachedForAGivenCenter extends RuntimeException{
    public UserBookingMaxLimitReachedForAGivenCenter(String e){
        super(e);
    }
}
