package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        this.directory = Paths.get(dir);
        if (!Files.isDirectory(directory))
            throw new IllegalArgumentException(dir + " is not directory");
        if (!Files.isReadable(directory)) throw new IllegalArgumentException(dir + " is not readable");
        if (!Files.isWritable(directory)) throw new IllegalArgumentException(dir + " is not writable");

        this.streamSerializer = streamSerializer;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    @Override
    protected Resume getElement(Path searchKey) {
        Resume resume;
        try {
            resume = streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File read error", getFileName(searchKey), e);
        }
        return resume;
    }

    @Override
    protected List<Resume> getCopyOfAllElements() {
        return getFilesList().map(this::getElement).collect(Collectors.toList());
    }

    @Override
    protected void updateElement(Resume r, Path searchKey) {
        try {
            streamSerializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File write error", getFileName(searchKey), e);
        }
    }

    @Override
    protected void saveElement(Resume r, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("File create error", getFileName(searchKey), e);
        }
        updateElement(r, searchKey);
    }

    @Override
    protected void deleteElement(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(searchKey), e);
        }
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::deleteElement);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}
