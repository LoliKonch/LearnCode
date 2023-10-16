module chat.local.javalocalchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires pgplib;
    requires annotations;

    opens com.infobezdari.learncode to javafx.fxml;
    exports com.infobezdari.learncode;
}