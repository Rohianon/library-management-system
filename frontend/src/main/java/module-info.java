module com.rohianon.library.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.rohianon.library.fx to javafx.fxml;
    exports com.rohianon.library.fx;
}
