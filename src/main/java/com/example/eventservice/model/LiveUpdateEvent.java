package com.example.eventservice.model;

public class LiveUpdateEvent {
    private String editorId;
    private String authorId;
    private String content;
    private String sourceId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEditorId() {
        return editorId;
    }

    public void setEditorId(String editorId) {
        this.editorId = editorId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LiveUpdateEvent{");
        sb.append("editorId='").append(editorId).append('\'');
        sb.append(", authorId='").append(authorId).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
