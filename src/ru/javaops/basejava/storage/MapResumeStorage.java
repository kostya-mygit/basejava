package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumes = new ArrayList<>(storage.values());
        Collections.sort(resumes, RESUME_COMPARATOR);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveElement(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
