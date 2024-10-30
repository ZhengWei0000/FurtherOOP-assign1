package reflection.gson;

import com.google.gson.Gson;
import java.util.Map;
import java.util.IdentityHashMap;

class MyObject {
    MyObject ref;
    String name;

    MyObject(String name) {
        this.name = name;
    }

    void setRef(MyObject ref) {
        this.ref = ref;
    }
}

public class CyclicGsonExample {

    public static class CyclicGraphException extends RuntimeException {
        public CyclicGraphException() {
            super("Gson cannot serialize cyclic graph");
        }
    }

    public static String serializeObject(MyObject obj) {
        // todo: fix this to handle cyclic references
        // so that it throws a CyclicGraphException when it encounters a cyclic reference
        Map<MyObject, Boolean> visited = new IdentityHashMap<>();
        if (hasCycle(obj, visited)) {
            throw new CyclicGraphException();
        }
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    private static boolean hasCycle(MyObject obj, Map<MyObject, Boolean> visited) {
        if (obj == null) {
            return false;
        }
        if (visited.containsKey(obj)) {
            return true; // 如果已经访问过该对象，说明有循环
        }
        visited.put(obj, true);
        return hasCycle(obj.ref, visited);
    }

    public static void main(String[] args) {
        // Non-cyclic case
        MyObject obj1 = new MyObject("obj1");
        MyObject obj2 = new MyObject("obj2");
        obj1.setRef(obj2);

        try {
            String json = serializeObject(obj1);
            System.out.println("Non-cyclic serialization: " + json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Cyclic case
        obj2.setRef(obj1);

        try {
            String json = serializeObject(obj1);
            System.out.println("Cyclic serialization: " + json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
