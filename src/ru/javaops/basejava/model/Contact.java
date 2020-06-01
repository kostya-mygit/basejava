package ru.javaops.basejava.model;

public class Contact {

    private ContactType type;
    private String content;

    public Contact(ContactType type, String content) {
        this.type = type;
        this.content = content;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

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

        Contact contact = (Contact) o;

        if (type != contact.type) return false;
        return content.equals(contact.content);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
