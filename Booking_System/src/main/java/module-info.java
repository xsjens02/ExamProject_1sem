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
    exports com.example.booking_system.ControllerService.PubSub;
    opens com.example.booking_system.ControllerService.PubSub to javafx.fxml;
    exports com.example.booking_system.ControllerService.Managers;
    opens com.example.booking_system.ControllerService.Managers to javafx.fxml;
    exports com.example.booking_system.ControllerService.Utilities;
    opens com.example.booking_system.ControllerService.Utilities to javafx.fxml;
}