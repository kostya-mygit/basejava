package ru.javaops.basejava;

import ru.javaops.basejava.model.SectionType;

public class TestSingleton {
    public static void main(String[] args) {
        EagerSingleton instance1 = EagerSingleton.getINSTANCE();
        LazySingleton instance2 = LazySingleton.getINSTANCE();

        EnumSingleton instance3 = EnumSingleton.INSTANCE;
        System.out.println(instance3);
        EnumSingleton instance4 = EnumSingleton.valueOf("INSTANCE");
        System.out.println(instance4);

        for (SectionType type : SectionType.values()) {
            System.out.println(type.ordinal() + " " + type.getTitle());
        }
    }
}

class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getINSTANCE() {
        return INSTANCE;
    }
}

class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getINSTANCE() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}

enum EnumSingleton {
    INSTANCE
}

