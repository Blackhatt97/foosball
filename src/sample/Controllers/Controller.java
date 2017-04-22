package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.Statement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller{

    @FXML
    Button clsBtn, player, teamBtn, matchBtn, tournamentBtn;


    @FXML
    public void exitProgram(ActionEvent actionEvent) {

        Stage stage = (Stage) clsBtn.getScene().getWindow();
        stage.close();

    }


    public void playerMenu(ActionEvent actionEvent) throws IOException {


        Parent player_parent = FXMLLoader.load(getClass().getResource("playerms.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) player.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();


    }

    public void teamMenu(ActionEvent actionEvent) throws IOException {


        Parent player_parent = FXMLLoader.load(getClass().getResource("teamms.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) teamBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();


    }
    public void matchMenu(ActionEvent actionEvent) throws IOException {


        Parent player_parent = FXMLLoader.load(getClass().getResource("matchms.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) matchBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();


    }
    public void tournamentMenu(ActionEvent actionEvent) throws IOException {


        Parent player_parent = FXMLLoader.load(getClass().getResource("tournamentms.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) tournamentBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();


    }


}

