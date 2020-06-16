package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.storage.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        if (!directory.isDirectory())
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        if (!directory.canRead()) throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable");
        if (!directory.canWrite()) throw new IllegalArgumentException(directory.getAbsolutePath() + " is not writable");

        this.serializer = serializer;
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
            resume = serializer.doRead(new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File read error", searchKey.getName(), e);
        }
        return resume;
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File f : files) {
            resumes.add(getElement(f));
        }
        return resumes;
    }

    @Override
    protected void updateElement(Resume r, File searchKey) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File write error", searchKey.getName(), e);
        }
    }

    @Override
    protected void saveElement(Resume r, File searchKey) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("File create error", searchKey.getName(), e);
        }
        updateElement(r, searchKey);
    }

    @Override
    protected void deleteElement(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("File delete error", searchKey.getName());
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                deleteElement(f);
            }
        }
    }

    @Override
    public int size() {
        String[] files = directory.list();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        return files.length;
    }
}
