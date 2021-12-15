package com.example.fastapi.classes;

public class modal {

    private boolean published;
    private String title;
    private String content;
    private String created_at;

    public modal(boolean published, String title, String content, String created_at) {

        this.title = title;
        this.content = content;
        this.published = published;
        this.created_at = created_at;
    }

    public boolean isPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
