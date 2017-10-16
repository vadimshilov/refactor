import java.util.Map;

interface DataConnection {

  Map<Integer, Double> loadData(int startYear, int endYear) throws DataConnectionException;

  void saveData(Map<Integer, ? extends Number> statistics) throws DataConnectionException;
}