package com.anaplan.interview.dao;

        import com.anaplan.interview.domain.Customer;
        import com.anaplan.interview.domain.Event;
        import org.springframework.jdbc.core.RowMapper;

        import java.sql.ResultSet;
        import java.sql.SQLException;

public class EventRowMapper implements RowMapper{
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setId(rs.getLong("id"));
        event.setEventName(rs.getString("event_name"));
        event.setStartTime(rs.getLong("start_time"));
        event.setEndTime(rs.getLong("end_time"));
        return event;
    }
}
