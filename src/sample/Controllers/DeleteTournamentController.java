package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DBConn;

import java.io.IOException;
import java.sql.*;

/**
 * Created by blackhatt on 19/04/2017.
 */
public class DeleteTournamentController {

    @FXML
    Button exitBtn, searchBtn, homeBtn, deleteBtn;

    @FXML
    TextArea searchWindow;

    @FXML
    TextField idField;

    public void exitAction(ActionEvent actionEvent) {


        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

    }
    @FXML
    public void homeMenu(ActionEvent actionEvent) throws IOException {

        Parent player_parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }

    public void deleteAction(ActionEvent actionEvent) {
        String query = "DELETE FROM `tournaments` WHERE `tournament_id` = " + idField.getText();

        Connection con = null;
        try{
            con = DBConn.getConn();
            PreparedStatement stmt = con.prepareStatement(query);
            int rs = stmt.executeUpdate();
            if(rs != 0)
                searchWindow.setText("The tournament has been deleted.");
            else
                searchWindow.setText("The tournament does not exist.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void searchAction(ActionEvent actionEvent) {



        String id = idField.getText();

        String tournamentQuery = "";
        if (!id.matches("[0-9]+") && !id.equals("")) {
            popUp("Failed", "Insert a valid id", "Close");
        }

        else if (!id.equals("")){
            tournamentQuery = "SELECT * FROM `tournaments` WHERE `tournament_id` = " + id + ";";

        }
        else {

            tournamentQuery = "SELECT * FROM `tournaments` ;";
        }
        Connection con = null;
        try{

            System.out.println("/"+tournamentQuery+"/");
            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(tournamentQuery);
            String resultset = "";
            while(rs.next()){
                String player = rs.getString("tournament_id") + " "
                        + rs.getString("name") + " Winner: "
                        + rs.getString("winner");
                resultset = resultset + player +"\n";

            }
            if(resultset.equals("")){

                searchWindow.setText("No tournament with the given id...");
            }
            else
                searchWindow.setText(resultset);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void popUp(String title, String text, String button){

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle(title);

        Label label1 = new Label(text);

        Button button1 = new Button(button);

        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

}
