package vekt.currency_converter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {

    public Double exchangeRateGetter(String currencyFrom, String currencyTo) throws IOException {
        // Create a URL object with the endpoint you want to request
        URL url = new URL("https://www.google.com/finance/quote/"+currencyFrom+"-"+currencyTo);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method to GET
        connection.setRequestMethod("GET");

        // Read the response from the server
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return exchangeRateValueExtractor(response.toString());
    }

    public Double exchangeRateValueExtractor(String exchangeRateValue){
        String regex = "<div class=\"YMlKec fxKbKc\">([0-9]+(?:,)?[0-9]+\\.[0-9]+)</div>";

        Matcher matcher = Pattern.compile(regex).matcher(exchangeRateValue);

        String extractedNumber = matcher.find() ? matcher.group(1) : "";


        return stringCleaner(extractedNumber);

    }

public Double stringCleaner(String extractedNumber){
    // Remove the comma from the string
    String cleanedNumberString = extractedNumber.replace(",", "");
    // Parse the string into a Double
    return Double.parseDouble(cleanedNumberString);
}







    @FXML
    void btn_convert_click(MouseEvent event) throws IOException {
        Double currencyRate = exchangeRateGetter((String) cbx_from.getValue(), (String) cbx_to.getValue());
        lb_total_currency_value.setText(String.format("%,.6f", Double.parseDouble(tf_amount.getText()) * currencyRate));

        lb_total_currency_name.setText((String) cbx_to.getValue());
        lb_single_from_currency.setText("1 "+(String) cbx_from.getValue()+" =");
        lb_single_to_currency_name.setText((String) cbx_to.getValue());
        lb_single_to_currency.setText(String.format("%,.6f", currencyRate));


    }

    @FXML
    void btn_start_quiz_click(MouseEvent event) {




    }









        @FXML
        private Button btn_convert;
        @FXML
        private Button btn_start_quiz;
        @FXML
        private Label lb_amount;
        @FXML
        private Label lb_center_title;
        @FXML
        private Label lb_from;
        @FXML
        private Label lb_quiz;
        @FXML
        private Label lb_single_from_currency;
        @FXML
        private Label lb_single_to_currency;
        @FXML
        private Label lb_single_to_currency_name;
        @FXML
        private Label lb_to;
        @FXML
        private Label lb_total_currency_name;
        @FXML
        private Label lb_total_currency_value;
        @FXML
        private TextField tf_amount;
        @FXML
        private ComboBox<?> cbx_from;
        @FXML
        private ComboBox<?> cbx_to;
}