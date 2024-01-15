module com.charlymech.anyteeth {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.charlymech.anyteeth to javafx.fxml;
    exports com.charlymech.anyteeth;

    exports com.charlymech.anyteeth.gui;
    opens com.charlymech.anyteeth.gui to javafx.fxml;
    exports com.charlymech.anyteeth.controller;
    opens com.charlymech.anyteeth.controller to javafx.fxml;
}