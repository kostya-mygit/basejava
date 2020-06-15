package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.strategy = strategy;
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory))
            throw new IllegalArgumentException(dir + " is not directory");
        if (!Files.isReadable(directory)) throw new IllegalArgumentException(dir + " is not readable");
        if (!Files.isWritable(directory)) throw new IllegalArgumentException(dir + " is not writable");
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected Resume getElement(Path searchKey) {
        Resume resume;
        try {
            resume = strategy.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File read error", searchKey.toString(), e);
        }
        return resume;
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> resumes.add(getElement(path)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return resumes;
    }

    @Override
    protected void updateElement(Resume r, Path searchKey) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File write error", searchKey.toString(), e);
        }
    }

    @Override
    protected void saveElement(Resume r, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("File create error", searchKey.toString(), e);
        }
        updateElement(r, searchKey);
    }

    @Override
    protected void deleteElement(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error", searchKey.toString());
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}
