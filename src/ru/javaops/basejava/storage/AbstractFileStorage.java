package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        if (!directory.canRead()) throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable");
        if (!directory.canWrite()) throw new IllegalArgumentException(directory.getAbsolutePath() + "is not writable");
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected Resume getElement(File searchKey) {
        Resume resume;
        try {
            resume = doRead(searchKey);
        } catch (IOException e) {
            throw new StorageException("IO Error", searchKey.getName(), e);
        }
        return resume;
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        List<Resume> resumes = null;
        File[] files = directory.listFiles();
        if (files != null) {
            resumes = new ArrayList<>();
            for (File f : files) {
                try {
                    resumes.add(doRead(f));
                } catch (IOException e) {
                    throw new StorageException("IO Error", f.getName(), e);
                }
            }
        }
        return resumes;
    }

    @Override
    protected void updateElement(Resume r, File searchKey) {
        try {
            doWrite(r, searchKey);
        } catch (IOException e) {
            throw new StorageException("IO Error", searchKey.getName(), e);
        }
    }

    @Override
    protected void saveElement(Resume r, File searchKey) {
        try {
            searchKey.createNewFile();
            doWrite(r, searchKey);
        } catch (IOException e) {
            throw new StorageException("IO Error", searchKey.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File searchKey) {
        searchKey.delete();
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) f.delete();
        }
    }

    @Override
    public int size() {
        String[] files = directory.list();
        return files == null ? 0 : files.length;
    }

    protected abstract void doWrite(Resume r, File searchKey) throws IOException;

    protected abstract Resume doRead(File searchKey) throws IOException;
}
