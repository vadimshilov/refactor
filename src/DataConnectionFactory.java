public class DataConnectionFactory {

  public DataConnection getDataConnection() {
    return new FileDataConnection("1.txt", "statistics.txt");
  }

}
