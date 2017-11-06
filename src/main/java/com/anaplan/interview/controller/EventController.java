package com.anaplan.interview.controller;

import com.anaplan.interview.dao.EventDao;
import com.anaplan.interview.domain.Event;
import com.anaplan.interview.domain.EventInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventDao eventDao;

    @RequestMapping("events/{id}")
    public Event getEventById(@PathVariable int id) {
        Event event = eventDao.findByEventId(id);
        System.out.println("EVENT IS " + event);
        return event;
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
    public String deleteEventById(@PathVariable int id) {
        int count = eventDao.deleteEvent(id);
        if (count > 0) {
            return "SUCCESS";
        }
        return "FAILURE";
    }

    @RequestMapping("events")
    public List<Event> getEvents() {
        List<Event> events = eventDao.getAllEvents();
        return events;
    }

    @RequestMapping(value = "events", method = RequestMethod.POST)
    public String createEvent(@RequestBody EventInput event) {
        int count = eventDao.createEvent(event.getEventName(), event.getStartTime().getTime(),
                event.getEndTime().getTime());
        if (count > 0) {
            return "SUCCESS";
        }
        return "FAILURE";
    }
}


