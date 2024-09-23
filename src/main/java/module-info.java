module com.arturoar.externalsortingalgorithms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens com.arturoar.externalsortingalgorithms to javafx.fxml;
    exports com.arturoar.externalsortingalgorithms;
    exports com.arturoar.main;
    opens com.arturoar.main to javafx.fxml;
}
