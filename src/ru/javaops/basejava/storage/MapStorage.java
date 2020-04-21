package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new TreeMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(searchKey);
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
        return storage.get(searchKey) != null;
    }
}
