package com.jumboneeds.controllers;

import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.ScheduleBean;
import com.jumboneeds.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/getSchedule", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ScheduleBean getSchedule(@RequestBody IdBean idBean){
        return scheduleService.getSchedule(idBean);
    }

}