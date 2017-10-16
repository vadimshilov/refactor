import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileDataConnection implements DataConnection {

  private String inputFileName;
  private String outputFileName;

  public FileDataConnection(String inputFileName, String outputFileName) {
    this.inputFileName = inputFileName;
    this.outputFileName = outputFileName;
  }

  @Override
  public Map<Integer, Double> loadData(int startYear, int endYear) throws DataConnectionException {
    Map<Integer, Integer> yearSum = new HashMap<>();
    List<Integer> yearList = IntStream.range(startYear, endYear).boxed().collect(Collectors.toList());
    yearList.forEach(year -> yearSum.put(year, 0));
    int[] count = new int[1];
    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
      reader.lines().forEach(line -> {
        count[0]++;
        String[] data = line.split(" ");
        yearList.forEach(year -> {
          String yearStr = year.toString();
          if (data[2].contains(yearStr)) {
            yearSum.put(year, yearSum.get(year) + Integer.parseInt(data[3]));
          }
        });
      });
    } catch (IOException e) {
      throw new DataConnectionException(e);
    }
    Map<Integer, Double> resultMap = new HashMap<>();
    yearSum.forEach((year, sum) -> {
      if (sum <= 0) {
        resultMap.put(year, 0.);
      } else {
        resultMap.put(year, (double) sum / count[0]);
      }
    });
    return resultMap;
  }

  @Override
  public void saveData(Map<Integer, ? extends Number> statistics) throws DataConnectionException {
    try {
      PrintWriter out = new PrintWriter(new FileOutputStream(outputFileName));
      List<Integer> years = new ArrayList<>(statistics.keySet());
      years.sort(Comparator.naturalOrder());
      for (int i = 0; i < years.size(); i++) {
        Integer year = years.get(i);
        out.printf("%d %d %d\n", i, year, statistics.get(year).intValue());
      }
      out.close();
    } catch (IOException e) {
      throw new DataConnectionException(e);
    }
  }
}
