module com.example.repaircompany {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.repaircompany to javafx.fxml;
    exports com.example.repaircompany;
}