package ru.javaops.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("The resume with uuid " + uuid + " doesn't exist.", uuid);
    }
}
