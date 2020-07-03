package ru.javaops.basejava;

//https://habr.com/ru/post/27108/
//https://habr.com/ru/post/129494/
public class TestSingleton {
    public static void main(String[] args) {
        EagerSingleton eagerSingleton = EagerSingleton.getINSTANCE();
        LazySingleton lazySingleton = LazySingleton.getINSTANCE();
        OnDemandHolderSingleton onDemandHolderSingleton = new OnDemandHolderSingleton();
        SynchronizedSingleton synchronizedSingleton = new SynchronizedSingleton();
        DoubleCheckedLockingWithVolatileSingleton doubleCheckedLockingWithVolatileSingleton = new DoubleCheckedLockingWithVolatileSingleton();

        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE;
        System.out.println(enumSingleton1);
        EnumSingleton enumSingleton2 = EnumSingleton.valueOf("INSTANCE");
        System.out.println(enumSingleton2);
    }

    //works in multithreading
    private static class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {
        }

        public static EagerSingleton getINSTANCE() {
            return INSTANCE;
        }
    }

    //doesn't work in multithreading
    private static class LazySingleton {
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

    //works in multithreading
    private static class OnDemandHolderSingleton {
        private OnDemandHolderSingleton() {
        }

        private static class SingletonHolder {
            private static final OnDemandHolderSingleton INSTANCE = new OnDemandHolderSingleton();
        }

        public static OnDemandHolderSingleton getINSTANCE() {
            return SingletonHolder.INSTANCE;
        }
    }

    //works in multithreading
    private static class SynchronizedSingleton {
        private static SynchronizedSingleton instance;

        private SynchronizedSingleton() {
        }

        public static synchronized SynchronizedSingleton getInstance() {
            if (instance == null) {
                instance = new SynchronizedSingleton();
            }
            return instance;
        }
    }

    //works in multithreading
    private static class DoubleCheckedLockingWithVolatileSingleton {
        private static volatile SynchronizedSingleton instance;

        private DoubleCheckedLockingWithVolatileSingleton() {
        }

        public static SynchronizedSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedLockingWithVolatileSingleton.class) {
                    if (instance == null) {
                        instance = new SynchronizedSingleton();
                    }
                }
            }
            return instance;
        }
    }

    //works in multithreading
    private enum EnumSingleton {
        INSTANCE
    }
}

