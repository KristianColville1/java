import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        try {
            /**
             * Creates a new BufferedReader and FileReader to read the trades.txt file
             */
            BufferedReader br = new BufferedReader(new FileReader("./src/trades.txt"));
            String trade = br.readLine();
            br.close(); // closes the buffered reader

            /**
             * Creates a pattern and matcher object to inspect the trade from above and will
             * perform actions only if the pattern exists.
             * 
             * If the original currency matches the existing currency then it returns after
             * outputting
             * an error message.
             * 
             * Uses Regex to find the desired pattern needed to build the trades.csv file
             */
            Pattern pattern = Pattern.compile("([a-zA-Z]{3})([a-zA-Z]{3})(\\d+)-(\\d+)([ROro])");
            Matcher matcher = pattern.matcher(trade);

            if (matcher.matches()) {
                String originalCurrency = matcher.group(1);
                String transferCurrency = matcher.group(2);

                // Checks the original currency
                if (originalCurrency.length() != 3) {
                    System.out.println("Error: The originating currency must have 3 letters.");
                    return;
                }

                // Check the destination currency
                if (transferCurrency.length() != 3) {
                    System.out.println("Error: The destination currency must have 3 letters.");
                    return;
                }

                // Checks if the above are both the same
                if (originalCurrency.equals(transferCurrency)) {
                    System.out.println("Error: Originating and destination currency cannot be the same.");
                    return;
                }

                String transferCost = matcher.group(3); // the cost for making the trade
                String transferAmount = matcher.group(4); // the amount traded
                char transferType = matcher.group(5).charAt(0); // An “R” or a “O” at the end, which denotes a reversed
                                                                // trade

                /**
                 * An additional try catch block to check if we can write to the file.
                 * Let's the user know that there's an issue writing to the file.
                 */
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("trades.csv"));
                    bw.write(originalCurrency + "," + transferCurrency + "," + transferCost + "," + transferAmount + ","
                            + transferType);
                    bw.newLine();
                    bw.close(); // closes the buffered writer
                } catch (Exception e) {
                    System.out.println("Unable to write to the destination file: " + e);
                }

            } else {
                System.out.println("Error: Trade format is incorrect.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
