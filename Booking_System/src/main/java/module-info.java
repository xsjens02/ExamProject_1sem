module com.example.booking_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;


    exports com.example.booking_system.Controller.ControllerService.PubSub;
    opens com.example.booking_system.Controller.ControllerService.PubSub to javafx.fxml;
    exports com.example.booking_system.Controller.ControllerService.Managers;
    opens com.example.booking_system.Controller.ControllerService.Managers to javafx.fxml;
    exports com.example.booking_system.Controller.Utilities;
    opens com.example.booking_system.Controller.Utilities to javafx.fxml;
    exports com.example.booking_system.Controller.Controllers;
    opens com.example.booking_system.Controller.Controllers to javafx.fxml;
}