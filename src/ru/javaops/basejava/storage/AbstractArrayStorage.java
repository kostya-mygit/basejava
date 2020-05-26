package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected void deleteElement(Integer searchKey) {
        fillDeletedElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateElement(Resume r, Integer searchKey) {
        storage[searchKey] = r;
    }

    @Override
    protected void saveElement(Resume r, Integer searchKey) {
        if (size < STORAGE_LIMIT) {
            insertElement(r, searchKey);
            size++;
        } else {
            throw new StorageException("Unable to save the resume. The storage is full.", r.getUuid());
        }
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
