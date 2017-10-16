import java.util.Comparator;
import java.util.Map;

public class Main {

  private static final int startYear = 1990;
  private static final int endYear = 2020;

  public static void main(String[] args) {
    DataConnectionFactory factory = new DataConnectionFactory();
    DataConnection dataConnection = factory.getDataConnection();

    System.out.println("app v.1.13");
    try {
      Map<Integer, Double> data = dataConnection.loadData(startYear, endYear);
      data.entrySet().stream()
          .sorted(Comparator.comparing(Map.Entry::getKey))
          .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
      dataConnection.saveData(data);
      System.out.println("Success");
    } catch (DataConnectionException e) {
      System.out.println("Error occurred " + e.getMessage());
      e.printStackTrace();
    }
  }
}
