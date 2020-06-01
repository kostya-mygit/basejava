package ru.javaops.basejava.model;

import java.util.List;

public class ListContentSection<T> extends AbstractSection<List<T>> {

    private List<T> content;

    public ListContentSection(SectionType type, List<T> content) {
        super(type);
        this.content = content;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListContentSection<?> section = (ListContentSection<?>) o;

        if (type != section.type) return false;
        return content.equals(section.content);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T c : content) {
            builder.append(c);
            builder.append("\n");
        }
        return builder.toString();
    }
}
