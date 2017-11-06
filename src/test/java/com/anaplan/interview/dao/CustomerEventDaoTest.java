package com.anaplan.interview.dao;

import com.anaplan.interview.Application;
import com.anaplan.interview.domain.CustomerEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
public class CustomerEventDaoTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerEventDaoTest.class);

    @Autowired
    private CustomerEventDao customerEventDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        jdbcTemplate.execute("DROP TABLE events IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE events(" +
                "id SERIAL, event_name VARCHAR(255), start_time BIGINT, end_time BIGINT )");

        jdbcTemplate.execute("DROP TABLE customers_events IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE customers_events(" +
                "customer_id BIGINT, event_id BIGINT)");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas breakfast', 1514214000000, 1514217600000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas lunch', 1514232000000, 1514235600000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas dinner', 1514257200000, 1514264400000)");

        jdbcTemplate.update("INSERT INTO customers_events VALUES(1,2);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(1,3);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(2,1);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(3,1);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(3,2);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(3,3);");

    }

    @Test
    public void testGetCustomerEventsForCustomer() {
        List<CustomerEvent> customerEvents1 = customerEventDao.getCustomerEventsForCustomer(1);
        assertEquals(customerEvents1.size() , 2);

        List<CustomerEvent> customerEvents2 = customerEventDao.getCustomerEventsForCustomer(2);
        assertEquals(customerEvents2.size() , 1);

        List<CustomerEvent> customerEvents3 = customerEventDao.getCustomerEventsForCustomer(3);
        assertEquals(customerEvents3.size() , 3);

        List<CustomerEvent> customerEvents4 = customerEventDao.getCustomerEventsForCustomer(4);
        assertEquals(customerEvents4.size() , 0);
    }

    @Test
    public void testGetConflictingEvents() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateInStringStart = "2017/12/25 07:59:00";
        String dateInStringEnd = "2017/12/25 23:59:00";
        Date dateStart = sdf.parse(dateInStringStart);
        Date dateEnd = sdf.parse(dateInStringEnd);
        List<CustomerEvent> events = customerEventDao.getNumberOfEventsUserHasAtCertainTime(3, dateStart, dateEnd);
        assertEquals(events.size(), 3);
    }
}
