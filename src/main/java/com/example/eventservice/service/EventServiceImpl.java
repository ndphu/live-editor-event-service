package com.example.eventservice.service;

import com.example.eventservice.model.EditorEvent;
import com.example.eventservice.model.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void broadcastEvent(EditorEvent event) {
        synchronized (listeners) {
            listeners.forEach(l->{
                l.getEvent().set(event);
                synchronized (l.getEvent()) {
                    l.getEvent().notify();
                }
            });
        }
    }

    @Override
    public EventListener createListener(String userId) {
        EventListener listener = new EventListener();
        listener.setListenerId(UUID.randomUUID().toString());
        listener.setUserId(userId);
        synchronized (listeners) {
            listeners.add(listener);
        }
        LOGGER.info("Created event listener {}", listener);
        return listener;
    }

    @Override
    public void removeListener(String listenerId) {
        synchronized (listeners) {
            listeners.removeIf(l->l.getListenerId().equals(listenerId));
        }
    }

    @Override
    public Optional<EventListener> getListenerById(String listenerId) {
        return listeners.stream().filter(l -> l.getListenerId().equals(listenerId)).findAny();
    }
}
