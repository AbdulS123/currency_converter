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

public class Controller {

    private int totalPoints;

    private final List<String> questions = List.of(
            "I am a very organized person.",
            "In unexpected problems I immediately look for solutions.",
            "I often spend my free time with friends or outdoors in nature.",
            "I adapt quickly to changes in my life.",
            "I accept my mistakes and see them as learning opportunities.",
            "I am interested in finances.",
            "I love traveling and discovering different cultures.",
            "I set aside a certain amount of money monthly.");
    private int currentQuestionNumber;

    @FXML
    public void initialize() {
        pb_quiz.setProgress(0);
        lb_quiz_question_text.setText(questions.get(0));
    }


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
    }

    @FXML
    void btn_start_quiz_click(MouseEvent event) {
        resetQuiz();
        grid_main_page.setVisible(false);
        grid_questions_page.setVisible(true);
        grid_questions.setVisible(true);
        grid_bitcoin_result.setVisible(false);
        grid_ethereum_result.setVisible(false);
        grid_litecoin_result.setVisible(false);
        grid_cardano_result.setVisible(false);
        System.out.println(lb_amount.getFont());
        System.out.println(lb_amount.getBackground());
        pb_quiz.setStyle("-fx-accent: #000924"); //Set color ProgressBar
    }

    public void nextQuestion() {
        reset_answer_btn_color();
        if(currentQuestionNumber == questions.size()) {
            grid_questions.setVisible(false);
            showResultPage();
            return;
        }
        //Progressbar
        double progress = (double) currentQuestionNumber / questions.size();
        pb_quiz.setProgress(progress);


        reset_answer_btn_color();
        questionChecker();
        String currentQuestion = questions.get(++currentQuestionNumber);
        lb_quiz_question_text.setText(String.valueOf(currentQuestionNumber+1) + ". " + currentQuestion);
    }


    public void questionChecker() {
        if(btn_first_answer.getStyle().contains("#000924")){
            totalPoints += 4;
        } else if (btn_second_answer.getStyle().contains("#000924")){
            totalPoints += 3;
        } else if (btn_third_answer.getStyle().contains("#000924")){
            totalPoints += 2;
        } else if (btn_fourth_answer.getStyle().contains("#000924")){
            totalPoints += 1;
        }
    }

    public void showResultPage() {
        if(totalPoints >= 1 && totalPoints <= 8) {
            showLitecoinResult();
        } else if (totalPoints >= 9 && totalPoints <= 16) {
            showCardanoResult();
        } else if (totalPoints >= 17 && totalPoints <= 24) {
            showEthereumResult();
        } else if(totalPoints >= 25 && totalPoints <= 32) {
            showBitcoinResult();
        }
    }

    private void showBitcoinResult() {
        grid_main_page.setVisible(false);
        grid_questions.setVisible(false);

        grid_bitcoin_result.setVisible(true);

        grid_ethereum_result.setVisible(false);
        grid_litecoin_result.setVisible(false);
        grid_cardano_result.setVisible(false);

    }

    private void showEthereumResult() {
        grid_main_page.setVisible(false);
        grid_questions.setVisible(false);

        //show result
        grid_ethereum_result.setVisible(true);

        //other results
        grid_bitcoin_result.setVisible(false);
        grid_litecoin_result.setVisible(false);
        grid_cardano_result.setVisible(false);
    }

    private void showLitecoinResult() {
        grid_main_page.setVisible(false);
        grid_questions.setVisible(false);

        //show result
        grid_litecoin_result.setVisible(true);

        //other results
        grid_bitcoin_result.setVisible(false);
        grid_ethereum_result.setVisible(false);
        grid_cardano_result.setVisible(false);
    }

    private void showCardanoResult() {
        grid_main_page.setVisible(false);
        grid_questions.setVisible(false);

        //show result
        grid_cardano_result.setVisible(true);

        //other results
        grid_bitcoin_result.setVisible(false);
        grid_ethereum_result.setVisible(false);
        grid_litecoin_result.setVisible(false);
    }


    @FXML
    void btn_second_answer_click(MouseEvent event) {
        if(btn_second_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_second_answer.setStyle("-fx-background-color: #000924; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #000924; -fx-border-width: 2px;");
            questionChecker();
        }
        else{ reset_answer_btn_color();}
    }
    @FXML
    void btn_third_answer_click(MouseEvent event) throws  IOException {
        if(btn_third_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_third_answer.setStyle("-fx-background-color: #000924; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #000924; -fx-border-width: 2px;");
            questionChecker();
        }
        else{ reset_answer_btn_color();}
    }
    @FXML
    void btn_fourth_answer_click(MouseEvent event) {
        if(btn_fourth_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_fourth_answer.setStyle("-fx-background-color: #000924; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #000924; -fx-border-width: 2px;");
            questionChecker();
        }
        else{ reset_answer_btn_color();}
    }
    @FXML
    void btn_first_answer_click(MouseEvent event) {
        if(btn_first_answer.getStyle().contains("#e9e9e9")){
            reset_answer_btn_color();
            btn_first_answer.setStyle("-fx-background-color: #000924; -fx-text-fill: #fff; -fx-font-bold: true; -fx-font-size: 16px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #000924; -fx-border-width: 2px;");
            questionChecker();
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
        totalPoints = 0;
        currentQuestionNumber =0;
        reset_answer_btn_color();
        pb_quiz.setProgress(0);
        String currentQuestion = questions.get(currentQuestionNumber);
        lb_quiz_question_text.setText(String.valueOf(currentQuestionNumber+1) + ". " + currentQuestion);

    }
    public void goBackMainPage() {
        grid_main_page.setVisible(true);
        grid_questions_page.setVisible(false);
    }

    public void lightMode() {
        grid_upper_background.setStyle("-fx-background-color: #76A5AF");
        grid_under_background.setStyle("-fx-background-color: #76A5AF");
        lb_quiz.setStyle("-fx-text-fill: #FFFFFF");
        btn_start_quiz.setStyle("-fx-background-color: #FFFFFF");
        btn_convert.setStyle("-fx-background-color: #76A5AF");
        btn_dark_mode.setVisible(true);
        btn_light_mode.setVisible(false);

        //Second page
        grid2_upper_background.setStyle("-fx-background-color: #76A5AF");
        grid2_under_background.setStyle("-fx-background-color: #76A5AF");
        btn_next_question.setStyle("-fx-background-color: #76A5AF; -fx-text-fill: #FFFFFF; -fx-background-radius: 30");
        btn_dark_mode2.setVisible(true);
        btn_light_mode2.setVisible(false);
        pb_quiz.setStyle("-fx-accent: #76A5AF");
    }

    public void darkMode() {
        grid_upper_background.setStyle("-fx-background-color: #000924");
        grid_under_background.setStyle("-fx-background-color: #000924");
        lb_quiz.setStyle("-fx-text-fill: #FFFFFF");
        btn_convert.setStyle("-fx-background-color: #000924; -fx-text-fill: #FFFFFF");
        btn_start_quiz.setStyle("-fx-background-color: #FFFFFF");
        btn_dark_mode.setVisible(false);
        btn_light_mode.setVisible(true);

        //Second page
        grid2_upper_background.setStyle("-fx-background-color: #000924");
        grid2_under_background.setStyle("-fx-background-color: #000924");
        btn_next_question.setStyle("-fx-background-color: #000924");
        lb_go_home.setStyle("-fx-text-fill: #FFFFFF");
        btn_dark_mode2.setVisible(false);
        btn_light_mode2.setVisible(true);
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
    private GridPane grid_bitcoin_result;
    @FXML
    private GridPane grid_ethereum_result;
    @FXML
    private GridPane grid_litecoin_result;
    @FXML
    private GridPane grid_cardano_result;
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
    private GridPane grid_loading;
    @FXML
    private GridPane grid_upper_background;
    @FXML
    private GridPane grid_under_background;
    @FXML
    private Button btn_light_mode;
    @FXML
    private Button btn_light_mode2;
    @FXML
    private Button btn_dark_mode;
    @FXML
    private Button btn_dark_mode2;
    @FXML
    private Label lb_go_home;
    @FXML
    private GridPane grid2_upper_background;
    @FXML
    private GridPane grid2_under_background;
    @FXML
    private Button btn_next_question;
    @FXML
    private ProgressIndicator pg_indicator;





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