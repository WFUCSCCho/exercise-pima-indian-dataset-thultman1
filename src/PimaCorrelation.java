import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PimaCorrelation {

    // Method to calculate correlation coefficient
    public static double Correlation(ArrayList<Double> xs, ArrayList<Double> ys) {
        if (xs == null || ys == null || xs.size() != ys.size()) {
            throw new IllegalArgumentException("Input arrays must not be null and must have the same length.");
        }

        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = xs.size();

        for (int i = 0; i < n; ++i) {
            double x = xs.get(i);
            double y = ys.get(i);

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        double cov = sxy / n - sx * sy / (n * n);
        double sigmax = Math.sqrt(sxx / n - sx * sx / (n * n));
        double sigmay = Math.sqrt(syy / n - sy * sy / (n * n));

        return cov / (sigmax * sigmay);
    }

    public static void main(String[] args) {

        ArrayList<Double> X = new ArrayList<>(); // BMI
        ArrayList<Double> Y = new ArrayList<>(); // Diabetes outcome

        String file = "diabetes.csv";  // Make sure it's in your project folder

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String header = br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                double bmi = Double.parseDouble(parts[5]);      // BMI column
                double outcome = Double.parseDouble(parts[8]);  // Outcome column

                // Skip missing BMI entries (BMI = 0)
                if (bmi > 0) {
                    X.add(bmi);
                    Y.add(outcome);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Compute correlation
        double corr = Correlation(X, Y);
        System.out.printf("Correlation between BMI and diabetes: %f\n", corr);
    }
}
