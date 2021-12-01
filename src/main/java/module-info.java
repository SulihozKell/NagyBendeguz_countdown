module com.example.nagybendeguz_countdown {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nagybendeguz_countdown to javafx.fxml;
    exports com.example.nagybendeguz_countdown;
}