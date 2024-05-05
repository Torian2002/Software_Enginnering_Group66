module com.virtual_bank_g66 {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires jdk.jshell;

    opens com.virtual_bank_g66.demo to javafx.fxml;
    exports com.virtual_bank_g66.demo;
}