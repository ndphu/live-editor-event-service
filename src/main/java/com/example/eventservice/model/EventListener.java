package com.example.eventservice.model;

import java.util.concurrent.atomic.AtomicReference;

public class EventListener {
    private String userId;
    private String listenerId;
    private final AtomicReference<Event> event = new AtomicReference<>(null);

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListenerId() {
        return listenerId;
    }

    public void setListenerId(String listenerId) {
        this.listenerId = listenerId;
    }

    public AtomicReference<Event> getEvent() {
        return event;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventListener{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", listenerId='").append(listenerId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
