package stats;

public class TestMostRecentObject {
    public static void main(String[] args){
        MostRecentObject<String> mro = new MostRecentObject<>();
        System.out.println(mro.getMostRecentObject());
        mro.add("Hello");
        mro.add("World");
        System.out.println(mro.getMostRecentObject());
        String str = mro.getMostRecentObject();
        System.out.println(str);

    }

}
