package com.anaplan.interview.dao;


import com.anaplan.interview.domain.CustomerEvent;
import com.anaplan.interview.domain.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerEventRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
