module com.example.booking_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;


    exports com.example.booking_system.Controller.System.PubSub;
    opens com.example.booking_system.Controller.System.PubSub to javafx.fxml;
    exports com.example.booking_system.Controller.System.Managers;
    opens com.example.booking_system.Controller.System.Managers to javafx.fxml;
    exports com.example.booking_system.Controller.ControllerService;
    opens com.example.booking_system.Controller.ControllerService to javafx.fxml;
    exports com.example.booking_system.Controller.Controllers;
    opens com.example.booking_system.Controller.Controllers to javafx.fxml;
}