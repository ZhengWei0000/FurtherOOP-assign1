
package stats;
import java.util.Comparator;


public class StringSelectors {
    // implement convenience methods for selecting strings
    public Selector<String> longestString() {
        return new Selector<>(Comparator.comparingInt(String::length));

        // todo: implement this method

    }

    public Selector<String> shortestString() {
        // todo: implement this method
        return new Selector<>((s1, s2) -> Integer.compare(s2.length(), s1.length()));
    }
}


