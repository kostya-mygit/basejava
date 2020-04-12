package ru.javaops.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("The resume with uuid " + uuid + " already exists.", uuid);
    }
}
