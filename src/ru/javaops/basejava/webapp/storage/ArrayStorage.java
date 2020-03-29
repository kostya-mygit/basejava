package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length) {
            if (get(r.getUuid()) == null) {
                storage[size] = r;
                size++;
            } else {
                System.out.printf("The resume with uuid %s already exists.\n", r.getUuid());
            }
        } else {
            System.out.println("Unable to save the resume. The storage is full.");
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) storage[index] = r;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        return index != -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            for (int j = index; j < size; j++) {
                if (j != size - 1) storage[j] = storage[j + 1];
                else storage[j] = null;
            }
            size--;
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        System.out.printf("The resume with uuid %s doesn't exist.\n", uuid);
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
