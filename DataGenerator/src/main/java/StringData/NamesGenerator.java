package StringData;

import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class NamesGenerator {

    private NamesGenerator() {

    }

    public static List<String> generate(int count) {

        List<String> names = new ArrayList<>();
        while(names.size() != count) {
            String name = RandomStringUtils.random(15, true, false).toLowerCase();
            if(!names.contains(name)) {
                names.add(name);
            }
        }

        return names;
    }

}
