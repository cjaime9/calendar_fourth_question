package com.anaplan.interview.dao;

import com.anaplan.interview.domain.Customer;
import com.anaplan.interview.domain.CustomerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CustomerEventDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addCustomerToEvent(long customerId, long eventId) {
        String sql = "INSERT INTO customers_events(customer_id, event_id) VALUES(" + customerId + ","
                + eventId + ");";
        return jdbcTemplate.update(sql);
    }

    public List<CustomerEvent> getNumberOfEventsUserHasAtCertainTime(long customerId, Date startTime, Date endTime) {
        String sqlStartTime = "SELECT events.id, events.event_name, events.start_time, events.end_time, " +
                " customers.id, customers.first_name, customers.last_name from events" +
                " INNER JOIN customers_events on events.id = customers_events.event_id" +
                " INNER JOIN customers on customers.id = customers_events.customer_id" +
                " WHERE customers.id = " + customerId +
                " AND  (" + startTime.getTime() +
                " BETWEEN  events.start_time AND events.end_time" +
                " OR  " + endTime.getTime() +
                " BETWEEN  events.start_time AND events.end_time)";


        String sqlBetween = "SELECT events.id, events.event_name, events.start_time, events.end_time, " +
                " customers.id, customers.first_name, customers.last_name from events" +
                " INNER JOIN customers_events on events.id = customers_events.event_id" +
                " INNER JOIN customers on customers.id = customers_events.customer_id" +
                " WHERE customers.id = " + customerId +
                " AND events.start_time BETWEEN " + startTime.getTime() + " AND " + endTime.getTime() +
                " AND events.end_time BETWEEN " + startTime.getTime() + " AND " + endTime.getTime();


        List<CustomerEvent> conflictingEventsStart = jdbcTemplate.query(sqlStartTime, new CustomerEventRowMapper());
        List<CustomerEvent> conflictingEventsBetween = jdbcTemplate.query(sqlBetween, new CustomerEventRowMapper());
        List<CustomerEvent> conflictingEvents = new ArrayList<>();
        conflictingEvents.addAll(conflictingEventsStart);
        conflictingEvents.addAll(conflictingEventsBetween);
        return conflictingEvents;
    }


}
