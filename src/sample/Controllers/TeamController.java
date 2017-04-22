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

/**
 * Created by blackhatt on 10/04/2017.
 */
public class TeamController {

    @FXML
    Button createBtn, searchBtn, updateBtn, deleteBtn, homeBtn, exitBtn;


    @FXML
    public void exitAction(ActionEvent actionEvent) {

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

    }

    public void createMenu(ActionEvent actionEvent) throws IOException{

        Parent player_parent = FXMLLoader.load(getClass().getResource("create_team.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }

    public void searchMenu(ActionEvent actionEvent) throws IOException{
        Parent player_parent = FXMLLoader.load(getClass().getResource("search_team.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }

    public void deleteMenu(ActionEvent actionEvent) throws IOException{

        Parent player_parent = FXMLLoader.load(getClass().getResource("delete_team.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }

    public void updateMenu(ActionEvent actionEvent) throws IOException{

        Parent player_parent = FXMLLoader.load(getClass().getResource("update_team.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }

    public void homeMenu(ActionEvent actionEvent) throws IOException{

        Parent player_parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }
}
