package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            printNotExistError(uuid);
            return null;
        }
    }

    protected abstract int getIndex(String uuid);

    protected void printNotExistError(String uuid) {
        System.out.printf("The resume with uuid %s doesn't exist.\n", uuid);
    }
}
