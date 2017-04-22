package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 * Created by blackhatt on 20/04/2017.
 */
public class CreateMatchController {

    @FXML
    Button exitBtn, homeBtn, searchBtn, createBtn, removeBtn;

    @FXML
    TextArea teamWindow, matchWindow;

    @FXML
    TextField idField, teamOne, teamTwo;

    @FXML
    DatePicker date;


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

    public void createAction(ActionEvent actionEvent) {

        String team1 = teamOne.getText();
        String team2 = teamTwo.getText();
        String dates = String.valueOf(date.getValue());

        String tournamentQuery = "SELECT * FROM `team_tournament` , `teams` WHERE `team_tournament`.`tournament_id_fk` = " +
                idField.getText() + " AND `team_tournament`.`team_id_fk` = `teams`.`team_id` ;";
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

                teamWindow.setText("Invalid teams...");
            }
            else{

                if(verifyTeams(idField.getText(),team1,team2)){
                    if(dates != "null"){
                String matchQuery = "INSERT INTO  `sql11167728`.`matches` (" +
                        "`match_id` ,\n" +
                        "`date` ,\n" +
                        "`team_id_a`,\n"+
                        "`team_id_b`, \n"+
                        "`score_a` ,\n" +
                        "`score_b` ,\n" +
                        "`tournament_id_fk`\n" +
                        ")\n" +
                        "VALUES (" +
                        "NULL ,  '"+ date.getValue()+ "','"+team1+"','" + team2 + "', NULL , NULL , " + idField.getText() + ");";
                stmt.executeUpdate(matchQuery);
                popUp("Congrats", "Match created", "Close");

                }
                else
                    popUp("failed","please insert a date", "close");

                }
                else{
                    popUp("Failed", "Match exists", "Close");
                }

        }

        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }
    public boolean verifyTeams(String id, String teamOne, String teamTwo){

        String query = "SELECT * FROM `matches` WHERE `tournament_id_fk` = " + id + " AND `team_id_a` = " + teamOne + " AND " +
                " `team_id_b` = "+teamTwo+"  OR `team_id_a` = " + teamTwo + " AND " +
                " `team_id_b` = "+teamOne+" ";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultSet = "";
            while(rs.next()) {
                resultSet = resultSet + rs.getString("tournament_id_fk");

                System.out.println("mu" +resultSet + "mu");
                if (!resultSet.equals(""))
                    return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
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

                teamWindow.setText("No tournament with the given id...");
            }
            else
                teamWindow.setText(resultset);
            rs.close();
            String query = "SELECT * FROM `matches` WHERE `tournament_id_fk` = " + id + " ;";
            rs = stmt.executeQuery(query);
            String res = "";
            while(rs.next()){

                    res = res + rs.getInt("team_id_a") + " vs. " + rs.getInt("team_id_b") +
                            "\nSCORE: " + rs.getInt("score_a") + " -- " + rs.getInt("score_b") + "\n";


            }
            matchWindow.setText(res);
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

    public void removeMatch(ActionEvent actionEvent) {
        String team1 = teamOne.getText();
        String team2 = teamTwo.getText();

       String query = "DELETE FROM `matches` WHERE `tournament_id_fk` = " + idField.getText() + " AND `team_id_a` = " + teamOne.getText() + " AND " +
        " `team_id_b` = "+ teamTwo.getText() + "  OR `team_id_a` = " + teamTwo.getText() + " AND " +
                " `team_id_b` = "+teamOne.getText()+" ";


        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
             int change = stmt.executeUpdate(query);
            if(change != 0){

                popUp("Congrats", "Match deleted", "Close");
            }
            else

                popUp("Failed", "No match with the given teams", "Close");

            }


        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
