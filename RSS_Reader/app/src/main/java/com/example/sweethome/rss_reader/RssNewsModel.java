package com.example.sweethome.rss_reader;

public class RssNewsModel {
    private String title;
    private String description;
    public RssNewsModel(String inputTitle,String inputDescription){
        this.title=inputTitle;
        this.description=inputDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
