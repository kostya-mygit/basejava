package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            insertElement(r, index);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return Collections.binarySearch(storage, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    protected void setElement(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int insertIndex = -index - 1;
        storage.add(insertIndex, r);
    }

    @Override
    protected void removeElement(int index) {
        storage.remove(index);
    }
}
