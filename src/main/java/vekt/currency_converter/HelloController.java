package vekt.currency_converter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {

    private int pointForEuro = 0;
    private int pointForDollar = 0;
    private int pointForBitcoin = 0;
    private int pointForDogecoin = 0;
    private final List<String> questions = List.of("Which currency is the most valuable?", "Which currency is the least valuable?", "Which currency is the most popular?", "Which currency is the least popular?");
    private int questionNumber = -1;
    private final int numberofquestion = 7;



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
        //(?:,)?
        String regex = "<div class=\"YMlKec fxKbKc\">(\\d+,?\\d?\\d?\\d?,?\\d?\\d?\\d?\\.\\d+)</div>";

        Matcher matcher = Pattern.compile(regex).matcher(exchangeRateValue);

        String extractedNumber = matcher.find() ? matcher.group(1) : "";


        return stringCleaner(extractedNumber);

    }
    public Double stringCleaner(String extractedNumber){
        String cleanedNumberString = extractedNumber.replace(",", "");
        return Double.parseDouble(cleanedNumberString);
    }
    @FXML
    void btn_convert_click(MouseEvent event) throws IOException {
        if(tf_amount.getText().isEmpty()){
            return;
        }
        Double currencyRate = exchangeRateGetter((String) cbx_from.getValue(), (String) cbx_to.getValue());
        String total_currency_value = String.format("%,.6f", Double.parseDouble(tf_amount.getText()) * currencyRate);
        lb_total_currency.setText(total_currency_value + " " + (String) cbx_to.getValue());


        lb_single_currency.setText("1 "+(String) cbx_from.getValue()+" ="+String.format("%,.6f", currencyRate+ (String) cbx_to.getValue()));
    }








    @FXML
    void btn_start_quiz_click(MouseEvent event) {
        grid_main_page.setVisible(false);
        grid_questions_page.setVisible(true);
        resetQuiz();
        System.out.println(lb_amount.getFont());
        System.out.println(lb_amount.getBackground());
    }

    public void nextQuestion() {
        reset_answer_btn_color();
        if(questionNumber == numberofquestion){
            grid_questions.setVisible(false);
            grid_questions_result.setVisible(true);
            return;
        }
        pb_quiz.setProgress((double)(1/numberofquestion)*questionNumber);
        reset_answer_btn_color();
        questionChecker();
        lb_quiz_question_text.setText(questions.get(questionNumber++));
        lb_quiz_question_text.setText(String.valueOf(questionNumber));
    }
    public void questionChecker() {
        if(btn_first_answer.getStyle().contains("#29bc1e")){
            pointForEuro++;
        } else if (btn_second_answer.getStyle().contains("#29bc1e")){
            pointForDollar++;
        } else if (btn_third_answer.getStyle().contains("#29bc1e")){
            pointForBitcoin++;
        } else if (btn_fourth_answer.getStyle().contains("#29bc1e")){
            pointForDogecoin++;
        }
    }

    @FXML
    void btn_second_answer_click(MouseEvent event) {
        if(btn_second_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_second_answer.setStyle("-fx-background-color: #29bc1e; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #29bc1e; -fx-border-width: 2px;");
        }
        else{ reset_answer_btn_color();}
    }
    @FXML
    void btn_third_answer_click(MouseEvent event) {
        if(btn_third_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_third_answer.setStyle("-fx-background-color: #29bc1e; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #29bc1e; -fx-border-width: 2px;");
        }
        else{ reset_answer_btn_color();}
    }
    @FXML
    void btn_fourth_answer_click(MouseEvent event) {
        if(btn_fourth_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_fourth_answer.setStyle("-fx-background-color: #29bc1e; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #29bc1e; -fx-border-width: 2px;");
        }
        else{ reset_answer_btn_color();}
    }
    @FXML
    void btn_first_answer_click(MouseEvent event) {
        if(btn_first_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_first_answer.setStyle("-fx-background-color: #29bc1e; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #29bc1e; -fx-border-width: 2px;");
        }
        else{ reset_answer_btn_color();}
    }

    public void reset_answer_btn_color() {
        btn_first_answer.setStyle("-fx-background-color: #e9e9e9; -fx-text-fill: #444951; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        btn_second_answer.setStyle("-fx-background-color: #e9e9e9; -fx-text-fill: #444951; -fx-font-bold: true; -fx-font-size: 16px;-fx-border-radius: 10px; -fx-background-radius: 10px;");
        btn_third_answer.setStyle("-fx-background-color: #e9e9e9; -fx-text-fill: #444951; -fx-font-bold: true; -fx-font-size: 16px;-fx-border-radius: 10px; -fx-background-radius: 10px;");
        btn_fourth_answer.setStyle("-fx-background-color: #e9e9e9; -fx-text-fill: #444951; -fx-font-bold: true; -fx-font-size: 16px;-fx-border-radius: 10px; -fx-background-radius: 10px;");
    }
    public void resetQuiz() {
        pointForEuro = 0;
        pointForDollar = 0;
        pointForBitcoin = 0;
        pointForDogecoin = 0;
        questionNumber = -1;
        reset_answer_btn_color();
    }
    public void goBackMainPage() {
        grid_main_page.setVisible(true);
        grid_questions_page.setVisible(false);
    }






    @FXML
    private Label lb_quiz_question_text;
    @FXML
    private GridPane grid_questions;
    @FXML
    private GridPane grid_main_page;;
    @FXML
    private GridPane grid_questions_page;
    @FXML
    private GridPane grid_questions_result;
    @FXML
    private Button btn_first_answer;
    @FXML
    private Button btn_second_answer;
    @FXML
    private Button btn_third_answer;
    @FXML
    private Button btn_fourth_answer;
    @FXML
    private ProgressBar pb_quiz;




    @FXML
    private GridPane grid_underground;




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
        private Label lb_single_currency;
        @FXML
        private Label lb_to;
        @FXML
        private Label lb_total_currency;
        @FXML
        private TextField tf_amount;
        @FXML
        private ComboBox<?> cbx_from;
        @FXML
        private ComboBox<?> cbx_to;

}