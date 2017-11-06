package com.anaplan.interview.dao;

import com.anaplan.interview.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Event findByEventId(long eventId){
        String sql = "SELECT * FROM EVENTS WHERE ID = ?";
        Event event = (Event)jdbcTemplate.queryForObject(
                sql, new Object[] { eventId }, new EventRowMapper());
        return event;
    }

    public List<Event> getAllEvents() {
        String sql = "SELECT * FROM EVENTS";
        List<Event> events = jdbcTemplate.query(sql, new EventRowMapper());
        return events;
    }

    public int deleteEvent(long eventId) {
        String sql = "DELETE FROM EVENTS WHERE ID = " + eventId;
        return jdbcTemplate.update(sql);

    }

    public int createEvent(String eventName, long start, long end) {
        String sql = "INSERT INTO EVENTS(event_name, start_time, end_time) " +
                " VALUES('" + eventName + "'," + start +
                "," + end + ");";
        return jdbcTemplate.update(sql);
    }
}
