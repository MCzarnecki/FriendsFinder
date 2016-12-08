public class App {

    public static void main(String... args) {
        System.setProperty("hadoop.home.dir", "C:\\Users\\mczarnecki\\Desktop\\Winutils\\");
        Mapper mapper = new Mapper();
        mapper.mapAndSave("Data\\I1000.txt");
    }
}