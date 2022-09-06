package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class PostCreated extends DomainEvent {
    private String title;
    private String author;

    public PostCreated() {
        super("posada.santiago.postcreated");
    }

    public PostCreated(String title, String author) {
        super("posada.santiago.postcreated");
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
