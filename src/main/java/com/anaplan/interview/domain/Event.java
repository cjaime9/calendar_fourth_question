package com.anaplan.interview.domain;

import com.anaplan.interview.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    private static final Logger log = LoggerFactory.getLogger(Event.class);

    private long id;
    private String eventName;
    private long startTime;
    private long endTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        log.info("START TIME IS " + getStartTime());
        log.info("AS DATE IT IS " + new Date((getStartTime())).toString());
        return new Date(getStartTime()).toString();
    }

    public String getEndDate() {
        log.info("END TIME IS " + getEndTime());
        log.info("AS DATE IT IS " + new Date(getEndTime()).toString());
        return new Date(getEndTime()).toString();
    }

}
