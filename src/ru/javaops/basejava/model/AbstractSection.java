package ru.javaops.basejava.model;

public abstract class AbstractSection<C> implements Section<C> {

    protected SectionType type;

    public AbstractSection(SectionType type) {
        this.type = type;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }
}
