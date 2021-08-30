package com.example.eventservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class AmqpConfig {
    @Value(value = "${exchange.fanout.editor.events}")
    private String editorEventsFanout;

    private final String editorEventsQueueName = "/queue/editor/events/"+UUID.randomUUID().toString();

    @Bean
    public FanoutExchange editorEventsFanoutExchange() {
        return new FanoutExchange(editorEventsFanout);
    }

    @Bean
    public Queue editorEventsQueue() {
        return new Queue(editorEventsQueueName, false, false, true);
    }

    @Bean
    public Binding bindingEditorEvents(FanoutExchange editorEventsFanoutExchange, Queue editorEventsQueue) {
        return BindingBuilder.bind(editorEventsQueue).to(editorEventsFanoutExchange);
    }

    public String getEditorEventsFanout() {
        return editorEventsFanout;
    }

    public void setEditorEventsFanout(String editorEventsFanout) {
        this.editorEventsFanout = editorEventsFanout;
    }

    public String getEditorEventsQueueName() {
        return editorEventsQueueName;
    }

}
