package ru.javaops.basejava.storage;

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
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage.get(index);
        } else {
            return null;
        }
    }

    @Override
    protected void deleteElement(String uuid) {
        storage.remove(getIndex(uuid));
    }

    @Override
    protected void updateElement(Resume r) {
        storage.set(getIndex(r.getUuid()), r);
    }

    @Override
    protected void saveElement(Resume r) {
        int index = getIndex(r.getUuid());
        int insertIndex = -index - 1;
        storage.add(insertIndex, r);
    }

    protected int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return Collections.binarySearch(storage, resume);
    }
}
