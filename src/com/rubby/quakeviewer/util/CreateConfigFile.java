package com.rubby.quakeviewer.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class CreateConfigFile {
    public void createConfigFile(File configFile) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("配置文件缺失");
        alert.setHeaderText(null);
        alert.setContentText("配置文件缺失!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Create the configuration file and get values from the user
            createAndPopulateConfigFile(configFile);
        } else {
            // User clicked Cancel or closed the dialog
            System.exit(0);
        }
    }

    private void createAndPopulateConfigFile(File configFile) {
        TextInputDialog dialog = new TextInputDialog();
        TextInputDialog dialog2 = new TextInputDialog();
        dialog.setTitle("API Configuration");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter apikey:");

        Optional<String> apikeyResult = dialog.showAndWait();
        if (apikeyResult.isPresent()) {
            String apikey = apikeyResult.get();

            dialog.getDialogPane().getChildren().clear();
            dialog2.setTitle("API Configuration");
            dialog2.setHeaderText(null);
            dialog2.setContentText("Enter maxSize:");

            Optional<String> maxSizeResult = dialog2.showAndWait();
            if (maxSizeResult.isPresent()) {
                String maxSize = maxSizeResult.get();

                try (FileWriter writer = new FileWriter(configFile)) {
                    writer.write("apikey=" + apikey + "\n");
                    writer.write("maxSize=" + maxSize + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
