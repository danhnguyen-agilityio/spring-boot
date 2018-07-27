package collection;

import javafx.beans.binding.ObjectBinding;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesDemo {
    public static void main(String[] args) throws Exception {
        FileReader reader = new FileReader("./src/collection/db.properties");
        Properties p = new Properties();
        p.load(reader);
        System.out.println("User: " + p.getProperty("user"));
        System.out.println("Password: " + p.getProperty("password"));

        Properties properties = System.getProperties();
        Set set = properties.entrySet();

        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }

        Properties properties1 = new Properties();
        properties1.setProperty("name", "david");
        properties1.setProperty("age", "23");
        properties1.store(new FileWriter("./src/collection/info.properties"), "Java properties example");
    }
}
