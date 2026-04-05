module com.studentmgmt.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.base;

    opens com.studentmgmt to javafx.fxml;
    opens com.studentmgmt.controller to javafx.fxml;
    opens com.studentmgmt.model to javafx.base, com.fasterxml.jackson.databind;
    opens com.studentmgmt.service to javafx.base;

    exports com.studentmgmt;
}
