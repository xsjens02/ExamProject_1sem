module com.example.booking_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.booking_system to javafx.fxml;
    exports com.example.booking_system;
    exports com.example.booking_system.Controller;
    opens com.example.booking_system.Controller to javafx.fxml;
    exports com.example.booking_system.ControllerService;
    opens com.example.booking_system.ControllerService to javafx.fxml;
}