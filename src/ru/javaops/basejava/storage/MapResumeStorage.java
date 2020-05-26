package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected Resume getElement(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void deleteElement(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void updateElement(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveElement(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}
