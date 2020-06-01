package ru.javaops.basejava.model;

import java.util.Objects;

public class Entry {

    private String title;
    private String from;
    private String to;
    private String description;

    public Entry(String title, String from, String to, String description) {
        this.title = title;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return title.equals(entry.title) &&
                from.equals(entry.from) &&
                to.equals(entry.to) &&
                description.equals(entry.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, from, to, description);
    }

    @Override
    public String toString() {
        return title + "\n" + from + " - " + to + "\n" + description;
    }
}
