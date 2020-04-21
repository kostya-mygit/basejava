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
    protected Resume getElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void updateElement(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveElement(Resume r) {
        storage.put(r.getUuid(), r);
    }
}
