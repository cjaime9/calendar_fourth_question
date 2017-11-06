package com.anaplan.interview.service;

import java.util.Date;
import java.util.List;

import com.anaplan.interview.dao.CustomerEventDao;
import com.anaplan.interview.dao.EventDao;
import com.anaplan.interview.domain.CustomerEvent;
import com.anaplan.interview.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventService {
    @Autowired
    private CustomerEventDao customerEventDao;

    @Autowired
    private EventDao eventDao;

    public int addEventForCustomer(long customerId, long eventId) {
       return 0;
    }
}
