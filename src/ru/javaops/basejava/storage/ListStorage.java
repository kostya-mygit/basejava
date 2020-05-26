package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        return new ArrayList<>(storage);
    }

    @Override
    protected void deleteElement(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected void updateElement(Resume r, Integer searchKey) {
        storage.set(searchKey, r);
    }

    @Override
    protected void saveElement(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}
