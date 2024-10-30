package lists;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class convenientlyTest {

    private static final int TEST_SIZE = 100000000; // 可以在这里更改 n 的大小
    private static final boolean INCLUDE_INT_ARRAY_LIST = true;
    private static final boolean INCLUDE_INT_LINKED_LIST = true;
    private static final boolean INCLUDE_EFFICIENT_INT_ARRAY_LIST = true;
    private static final boolean INCLUDE_EFFICIENT_INT_LINKED_LIST = true;
    private static final boolean INCLUDE_GENERIC_LINKED_LIST = true;
    private static final boolean INCLUDE_GENERIC_ARRAY_LIST = true;
    private static final boolean INCLUDE_GENERIC_LINKED_LIST_RECORD = true;

    public static void main(String[] args) {
        List<Class<? extends ListTestable>> listsToTest = new ArrayList<>();

        if (INCLUDE_INT_ARRAY_LIST) {
            listsToTest.add(IntArrayList.class);
        }

        if (INCLUDE_INT_LINKED_LIST) {
            listsToTest.add(IntLinkedList.class);
        }

        if (INCLUDE_EFFICIENT_INT_ARRAY_LIST) {
            listsToTest.add(EfficientIntArrayList.class);
        }

        if (INCLUDE_EFFICIENT_INT_LINKED_LIST) {
            listsToTest.add(EfficientIntLinkedList.class);
        }

        if (INCLUDE_GENERIC_LINKED_LIST) {
            listsToTest.add(GenericLinkedList.class);
        }

        if (INCLUDE_GENERIC_ARRAY_LIST) {
            listsToTest.add(GenericArrayList.class);
        }

        if (INCLUDE_GENERIC_LINKED_LIST_RECORD) {
            listsToTest.add(GenericLinkedListRecord.class);
        }

        // 遍历类对象，传递给 runSpeedTest
        for (Class<? extends ListTestable> listClass : listsToTest) {
            runSpeedTest(listClass);
        }
    }

    private static void runSpeedTest(Class<? extends ListTestable> listClass) {
        int[] testSizes = {1000000, 1200000, 1400000, 1600000, 1800000, 2000000};
        String listName = listClass.getSimpleName();  // 通过参数 listClass 获取列表名称

        for (int size : testSizes) {
            try {
                // 使用反射创建新的列表实例
                ListTestable list = listClass.getDeclaredConstructor().newInstance();

                long startTime, endTime;

                // 测试 append 操作
                startTime = System.nanoTime();
                for (int i = 0; i < size; i++) {
                    list.append(i);
                }
                endTime = System.nanoTime();
                double appendTime = (endTime - startTime) / 1e6;

                // 测试 contains 操作
                startTime = System.nanoTime();
                list.contains(size / 2);
                endTime = System.nanoTime();
                double containsTime = (endTime - startTime) / 1e6;

                // 将结果保存到 CSV 文件中
                try (FileWriter writer = new FileWriter("/Users/weizheng/IdeaProjects/FurtherOOP-assign1/list_performance5.csv", true)) {
                    writer.append(listName).append(",").append(String.valueOf(size)).append(",")
                            .append(String.valueOf(appendTime)).append(",")
                            .append(String.valueOf(containsTime)).append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    interface ListTestable {
        void append(int value);

        boolean contains(int value);
    }

    static class IntArrayList implements ListTestable {
        private int[] values = new int[10];
        private int len = 0;

        @Override
        public void append(int value) {
            if (len == values.length) {
                int[] newValues = new int[values.length * 2];
                System.arraycopy(values, 0, newValues, 0, len);
                values = newValues;
            }
            values[len++] = value;
        }

        @Override
        public boolean contains(int value) {
            for (int i = 0; i < len; i++) {
                if (values[i] == value) {
                    return true;
                }
            }
            return false;
        }
    }

    static class IntLinkedList implements ListTestable {
        private IntNode head;
        private IntNode tail;
        private int len = 0;

        @Override
        public void append(int value) {
            IntNode newNode = new IntNode(value);
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            len++;
        }

        @Override
        public boolean contains(int value) {
            IntNode current = head;
            while (current != null) {
                if (current.value == value) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        static class IntNode {
            int value;
            IntNode next;

            IntNode(int value) {
                this.value = value;
                this.next = null;
            }
        }
    }

    static class EfficientIntArrayList implements ListTestable {
        private int[] values = new int[10];
        private int len = 0;

        @Override
        public void append(int value) {
            if (len == values.length) {
                int[] newValues = new int[values.length * 2];
                System.arraycopy(values, 0, newValues, 0, len);
                values = newValues;
            }
            values[len++] = value;
        }

        @Override
        public boolean contains(int value) {
            for (int i = 0; i < len; i++) {
                if (values[i] == value) {
                    return true;
                }
            }
            return false;
        }
    }

    static class EfficientIntLinkedList implements ListTestable {
        private IntNode head;
        private IntNode tail;
        private int len = 0;

        @Override
        public void append(int value) {
            IntNode newNode = new IntNode(value);
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            len++;
        }

        @Override
        public boolean contains(int value) {
            IntNode current = head;
            while (current != null) {
                if (current.value == value) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        static class IntNode {
            int value;
            IntNode next;

            IntNode(int value) {
                this.value = value;
                this.next = null;
            }
        }
    }

    static class GenericLinkedList<T> implements ListTestable {
        private GenericNode<T> head;
        private GenericNode<T> tail;
        private int len = 0;

        @Override
        public void append(int value) {
            GenericNode<T> newNode = new GenericNode<>((T) Integer.valueOf(value));
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            len++;
        }

        @Override
        public boolean contains(int value) {
            GenericNode<T> current = head;
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        static class GenericNode<T> {
            T value;
            GenericNode<T> next;

            GenericNode(T value) {
                this.value = value;
                this.next = null;
            }
        }
    }

    static class GenericArrayList<T> implements ListTestable {
        private T[] values = (T[]) new Object[10];
        private int len = 0;

        @Override
        public void append(int value) {
            if (len == values.length) {
                T[] newValues = (T[]) new Object[values.length * 2];
                System.arraycopy(values, 0, newValues, 0, len);
                values = newValues;
            }
            values[len++] = (T) Integer.valueOf(value);
        }

        @Override
        public boolean contains(int value) {
            for (int i = 0; i < len; i++) {
                if (values[i].equals(value)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class GenericLinkedListRecord<T> implements ListTestable {
        GenericNodeRecord<T> head;
        int len;

        @Override
        public void append(int value) {
            head = appendRecord(head, (T) Integer.valueOf(value));
            len++;
        }

        private GenericNodeRecord<T> appendRecord(GenericNodeRecord<T> node, T value) {
            if (node == null) {
                return new GenericNodeRecord<>(value, null);
            } else {
                GenericNodeRecord<T> current = node;
                while (current.next() != null) {
                    current = current.next();
                }
                current = new GenericNodeRecord<>(current.value(), new GenericNodeRecord<>(value, null));
                return node;
            }
        }


        @Override
        public boolean contains(int value) {
            GenericNodeRecord<T> current = head;
            while (current != null) {
                if (current.value().equals(value)) {
                    return true;
                }
                current = current.next();
            }
            return false;
        }
    }

    record GenericNodeRecord<T>(T value, GenericNodeRecord<T> next) {
    }
}