package ru.javaops.basejava.model;

import java.util.Objects;

public class Section<T> {

    private SectionType type;
    private T content;

    public Section(SectionType type, T content) {
        this.type = type;
        this.content = content;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section<?> section = (Section<?>) o;
        return type == section.type &&
                content.equals(section.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, content);
    }

    @Override
    public String toString() {
        return "Section{" +
                "type=" + type +
                ", content=" + content +
                '}';
    }
}
