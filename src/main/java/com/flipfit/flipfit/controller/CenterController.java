package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CenterController {

    @Autowired private CenterService centerService;

    public Center addCenter(Center center) {
        return centerService.addCenter(center);
    }
}
