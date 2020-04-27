package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

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
    protected Resume getElement(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage.set((Integer) searchKey, r);
    }

    @Override
    protected void saveElement(Resume r, Object searchKey) {
        int insertIndex = -(Integer) searchKey - 1;
        storage.add(insertIndex, r);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return Collections.binarySearch(storage, resume, SortedArrayStorage.RESUME_COMPARATOR);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }
}
