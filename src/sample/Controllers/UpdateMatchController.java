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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by blackhatt on 22/04/2017.
 */
public class UpdateMatchController {




    @FXML
    Button exitBtn, homeBtn, searchBtn, updateBtn;

    @FXML
    TextArea matchBox;

    @FXML
    TextField idField, scoreA, scoreB,teamA, teamB;



    @FXML
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


    public void updateAction(ActionEvent actionEvent) {

        String teama = teamA.getText();
        String teamb = teamB.getText();
        String scorea = scoreA.getText();
        String scoreb = scoreB.getText();
        String id = idField.getText();
        System.out.println(teama + teamb+scorea+scoreb+id);

        if(teama.equals("") || teamb.equals("") || scorea.equals("") || scoreb.equals("") || id.equals("")){

            popUp("Failed", "Fill in all the fields", "Close");
        }
        if (!id.matches("[0-9]+")) {
            popUp("Failed", "Insert a valid id", "Close");
        }else   if (!teama.matches("[0-9]+")) {
            popUp("Failed", "Insert a valid id", "Close");
        }else   if (!teamb.matches("[0-9]+")) {
            popUp("Failed", "Insert a valid id", "Close");
        }else   if (!scorea.matches("[0-9]+")) {
            popUp("Failed", "Insert a valid id", "Close");
        }else   if (!scoreb.matches("[0-9]+")) {
            popUp("Failed", "Insert a valid id", "Close");
        } else{

        String updateQ ="UPDATE `matches` SET `score_a` = " + scorea + ", `score_b` = " + scoreb + " WHERE `team_id_a` = " +
                teama + " AND `team_id_b` = " + teamb + " AND `tournament_id_fk` = " + id + " ;";
        String updateQ2 ="UPDATE `matches` SET `score_a` = " + scoreb + ", `score_b` = " + scorea + " WHERE `team_id_a` = " +
                teamb + " AND `team_id_b` = " + teama + " AND  `tournament_id_fk` = " + id + ";";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(updateQ);
            int rs2 = stmt.executeUpdate(updateQ2);
            if(rs == 0 && rs2 ==0)
                popUp("Failed", "Something went wrong", "Close");
            else
                popUp("Congrats", "The score has been updated", "Close");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

    }

    public void searchAction(ActionEvent actionEvent) {

        String id = idField.getText();


        String tournamentQuery = "";
        if (!id.matches("[0-9]+")) {
            popUp("Failed", "Insert a valid id", "Close");
        }
        else {

            tournamentQuery = "SELECT * FROM `team_tournament` , `teams` WHERE `team_tournament`.`tournament_id_fk` = "
                    + id + " AND `team_tournament`.`team_id_fk` = `teams`.`team_id` ;";
        }
        Connection con = null;
        try{

            System.out.println("/"+tournamentQuery+"/");
            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(tournamentQuery);
            String resultset = "";
            while(rs.next()){
                String player = rs.getString("teams.team_id") + " "
                        + rs.getString("name");
                resultset = resultset + player +"\n";

            }
            if(resultset.equals("")){

                matchBox.setText("No tournament with the given id...");
            }
            else{
            rs.close();
            String query = "SELECT * FROM `matches` WHERE `tournament_id_fk` = " + id + " ;";
            rs = stmt.executeQuery(query);
            String res = "";
            while(rs.next()){

                res = res + rs.getInt("team_id_a") + " vs. " + rs.getInt("team_id_b") +
                        "\nSCORE: " + rs.getInt("score_a") + " -- " + rs.getInt("score_b") + "\n";


            }
            matchBox.setText(res);}
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
