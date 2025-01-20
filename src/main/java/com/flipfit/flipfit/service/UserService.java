package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.UserAlreadyPresentException;
import com.flipfit.flipfit.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final List<User> users;

    public User addUser(User user) {
        if(users.contains(user)) {
            throw new UserAlreadyPresentException("User already present");
        }
        users.add(user);
        log.info("Added user:{}", user.getUserId());
        return user;
    }

    public Map<Slot,Boolean> viewAllSlotsForAGivenCenterAndUser(Center center, Date date, User user){
        List<Slot> slotsAtASpecificDate = center.getSlots()
                .stream()
                .filter(slot -> slot.getSlotDate().equals(date))
                .toList();

        if(user.getUserType().equals(UserType.FK_VIP_USER))
            return slotsAtASpecificDate
                    .stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.PREMIUM_SLOT))
                    .collect(Collectors.toMap(slot -> slot, Slot::isAvailable));
        else
            return slotsAtASpecificDate
                    .stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.NORMAL_SLOT))
                    .collect(Collectors.toMap(slot -> slot, Slot::isAvailable));
    }
}
