package ru.javaops.basejava.model;

public class TextContentSection extends AbstractSection<String> {

    private String content;

    public TextContentSection(SectionType type, String content) {
        super(type);
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextContentSection section = (TextContentSection) o;

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
        return content;
    }
}
