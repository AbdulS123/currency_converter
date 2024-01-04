module vekt.currency_converter {
    requires javafx.controls;
    requires javafx.fxml;


    opens vekt.currency_converter to javafx.fxml;
    exports vekt.currency_converter;
}