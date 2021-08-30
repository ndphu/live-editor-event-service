package com.example.eventservice.amqphandler;

import com.example.eventservice.model.EditorEvent;
import com.example.eventservice.service.EventService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    private final EventService eventService;
    private final Gson gson;

    public EventHandler(EventService eventService, Gson gson) {
        this.eventService = eventService;
        this.gson = gson;
    }

    @RabbitListener(queues = "#{amqpConfig.getEditorEventsQueueName()}")
    public void receivedMessage(String message) {
        LOGGER.info("receivedMessage={}", message);
        EditorEvent event = gson.fromJson(message, EditorEvent.class);
        eventService.broadcastEvent(event);
    }
}
