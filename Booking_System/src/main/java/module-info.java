module com.example.booking_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.booking_system to javafx.fxml;
    exports com.example.booking_system;
}