package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void saveElement(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }
}
