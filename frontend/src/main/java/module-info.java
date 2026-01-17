module com.rohianon.library.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.rohianon.library.fx to javafx.fxml;
    opens com.rohianon.library.fx.model to com.fasterxml.jackson.databind;
    exports com.rohianon.library.fx;
    exports com.rohianon.library.fx.model;
    exports com.rohianon.library.fx.service;
    exports com.rohianon.library.fx.view;
}
