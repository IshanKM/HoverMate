package com.example.hovermate;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

public class HoverMateApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Create an icon button
        Button iconButton = new Button("📂");
        iconButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: gray; -fx-padding: 5px;");

        // VBox to hold the file list
        VBox fileList = new VBox(5);
        fileList.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10px;");
        fileList.setAlignment(Pos.CENTER);
        fileList.setVisible(false); // Initially hidden

        // Get files from Downloads folder
        File downloadsFolder = new File(System.getProperty("user.home") + "/Downloads");
        if (downloadsFolder.exists() && downloadsFolder.isDirectory()) {
            for (File file : Objects.requireNonNull(downloadsFolder.listFiles())) {
                Label fileLabel = new Label(file.getName());
                fileLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5px;");
                fileList.getChildren().add(fileLabel);
            }
        } else {
            fileList.getChildren().add(new Label("No Downloads Found"));
        }

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), fileList);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), fileList);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> fileList.setVisible(false));

        // Show file list when hovering over button
        iconButton.setOnMouseEntered(event -> {
            fileList.setVisible(true);
            fadeIn.play();
        });

        // Keep file list visible while hovering over it
        fileList.setOnMouseEntered(event -> fileList.setVisible(true));

        // Hide file list only when mouse exits both button and list
        fileList.setOnMouseExited(event -> fadeOut.play());
        iconButton.setOnMouseExited(event -> {
            if (!fileList.isHover()) {
                fadeOut.play();
            }
        });

        // Layout
        StackPane root = new StackPane();
        root.getChildren().addAll(iconButton, fileList);
        StackPane.setAlignment(iconButton, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(fileList, Pos.TOP_CENTER);

        // Scene and Stage
        Scene scene = new Scene(root, 250, 400);
        stage.setTitle("HoverMate - Downloads");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
