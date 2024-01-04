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
        String regex = "<div class=\"YMlKec fxKbKc\">([0-9]+\\.[0-9]+)</div>";

        Matcher matcher = Pattern.compile(regex).matcher(exchangeRateValue);

        String extractedNumber = matcher.find() ? matcher.group(1) : "";

        return Double.valueOf(extractedNumber);
    }



    public static void roter() {
        System.out.println("Hello, JavaFX Application!");

    }


    @FXML
    private Button btn_test;

    @FXML
    void btn_test_click(MouseEvent event) {
        btn_test.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");
        sdde.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");
    }

    @FXML
    void dasdas(MouseEvent event) {

    }


    @FXML
    void BtnSubmitClick(MouseEvent event) throws IOException {
        System.out.println(exchangeRateGetter("EUR", "USD"));
        roter();



    }
    @FXML
    private ComboBox<?> A123;
    @FXML
    private ComboBox<?> B123;
    @FXML
    private TextField sdde;

}