package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

abstract public class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        Resume resume = getElement(r.getUuid());
        if (resume == null) {
            saveElement(r);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void update(Resume r) {
        Resume resume = getElement(r.getUuid());
        if (resume != null) {
            updateElement(r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = getElement(uuid);
        if (resume != null) {
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Resume resume = getElement(uuid);
        if (resume != null) {
            deleteElement(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Resume getElement(String uuid);

    protected abstract void deleteElement(String uuid);

    protected abstract void updateElement(Resume r);

    protected abstract void saveElement(Resume r);
}
