package com.example.eventservice.controller;

import com.example.eventservice.model.EditorEvent;
import com.example.eventservice.model.Event;
import com.example.eventservice.model.EventListener;
import com.example.eventservice.model.EventType;
import com.example.eventservice.model.LiveUpdateEvent;
import com.example.eventservice.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/editor/liveEvents")
    public void submitLiveEvent(@RequestBody LiveUpdateEvent updateEvent) {
        EditorEvent event = new EditorEvent();
        event.setEditorId(updateEvent.getEditorId());
        event.setEventType(EventType.EDITOR_CONTENT_LIVE_UPDATE);
        event.setEventId(UUID.randomUUID().toString());
        Map<String, String> data = new HashMap<>();
        data.put("content", updateEvent.getContent());
        data.put("sourceId", updateEvent.getSourceId());
        event.setData(data);
        eventService.broadcastEvent(event);
    }

    @PostMapping("/listeners")
    public EventListener createListener() {
        return eventService.createListener(getCurrentUser().getUserId());
    }

    @GetMapping("/listener/{listenerId}")
    public Event getEditorEvent(@PathVariable String listenerId) {
        Optional<EventListener> listener = eventService.getListenerById(listenerId);
        if (listener.isPresent()) {
            final AtomicReference<Event> event = listener.get().getEvent();
            synchronized (event) {
                try {
                    event.wait();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            return event.get();
        }
        throw new RuntimeException("Listener not found!");
    }
}
