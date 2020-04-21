package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            super.save(r);
        } else {
            throw new StorageException("Unable to save the resume. The storage is full.", r.getUuid());
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getElement(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            return null;
        }
    }

    @Override
    protected void deleteElement(String uuid) {
        fillDeletedElement(getIndex(uuid));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateElement(Resume r) {
        storage[getIndex(r.getUuid())] = r;
    }

    @Override
    protected void saveElement(Resume r) {
        insertElement(r, getIndex(r.getUuid()));
        size++;
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract int getIndex(String uuid);
}
