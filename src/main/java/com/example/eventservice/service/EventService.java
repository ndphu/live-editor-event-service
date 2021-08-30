package com.example.eventservice.service;


import com.example.eventservice.model.EditorEvent;
import com.example.eventservice.model.EventListener;

import java.util.Optional;

public interface EventService {

    void broadcastEvent(EditorEvent event);

    EventListener createListener(String userId);

    void removeListener(String listenerId);

    Optional<EventListener> getListenerById(String listenerId);
}
