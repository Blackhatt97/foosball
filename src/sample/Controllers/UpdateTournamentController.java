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
public class UpdateTournamentController {



    @FXML
    Button exitBtn, homeBtn, searchBtn, updateBtn;

    @FXML
    TextArea displayAvTeams, displayTorTeams;

    @FXML
    TextField idField, tourId, torRank;


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

    String teamRank = "";
    public void searchTournament(){

        String query = "SELECT * " +
                "FROM `teams`, `team_tournament`\n" +
                "WHERE `team_tournament`.`tournament_id_fk` =  " + tourId.getText() +
                " AND `team_tournament`.`team_id_fk` = `teams`.`team_id`";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultSet = "";
            while(rs.next()) {
                String player = rs.getString("team_tournament.id") + " "
                        + rs.getString("team_tournament.team_id_fk") + " "
                        + rs.getString("teams.name");
                resultSet = resultSet + player + "\n";
            }
            if(!resultSet.equals("")){


                String queryPlayers = "SELECT * FROM `team_tournament`, `players` WHERE " +
                        "`team_tournament`.`tournament_id_fk` = " + tourId.getText() +" AND" +
                        " `team_tournament`.`team_id_fk` = `players`.`team_id_fk`;";
                ResultSet rs2 = stmt.executeQuery(queryPlayers);
                int ok = 0;
                while(rs2.next()){
                    teamRank = rs2.getString("players.rank");
                }

                rs.close();
                rs2.close();
                String queryAvTeams = "SELECT * \n" +
                        "FROM `teams`, `players`\n" +
                        "WHERE `players`.`rank` LIKE  '"+teamRank+"'\n" +
                        "AND `teams`.`team_id` = `players`.`team_id_fk`\n" +
                        "LIMIT 0 , 30";
                rs = stmt.executeQuery(queryAvTeams);
                String results = "";
                while(rs.next()){
                    if(ok == 2)
                        ok = 0;
                    if(ok == 0){
                    results = results + rs.getInt("teams.team_id") + " " +
                    rs.getString("name") + " with player: \n";
                    }
                    results = results + rs.getInt("players.player_id") + " " + rs.getString("players.f_name")
                    + " " + rs.getString("l_name") ;
                    results = results + "\n";
                    ok++;
                }

                torRank.setText(teamRank);
                displayTorTeams.setText(resultSet);
                displayAvTeams.setText(results);
            }
            else
                displayTorTeams.setText("There is no such tournament...");

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void updateTournament(ActionEvent actionEvent) {
    }

    public void addTeam(ActionEvent actionEvent) {

        String teamId = idField.getText();

        String query = "SELECT * " +
                "FROM `teams`, `players` " +
                "WHERE `players`.`rank` LIKE  '"+ teamRank +"' " +
                "AND `players`.`team_id_fk` = " + teamId +";";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                String player = rs.getString("players.rank");
                if(!teamRank.equals(player))
                    popUp("Failed!", "The selected team has a different rank", "Close this dialog");
                else{


                    if(verifyTeams(teamId)){
                        String insertTeam = "INSERT INTO  `sql11167728`.`team_tournament` (\n" +
                                "`id` ,\n" +
                                "`tournament_id_fk` ,\n" +
                                "`team_id_fk`\n" +
                                ")\n" +
                                "VALUES (\n" +
                                "NULL , "+ tourId.getText()+", " + teamId + ");";
                        stmt.executeUpdate(insertTeam);
                        displayAddedTeams();
                        popUp("Congrats", "Team has been added", "Close");}
                    else
                        popUp("Failed", "Team has been added already", "Close");

                }

            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }
    public boolean verifyTeams(String id){

        String query = "SELECT * FROM `team_tournament` WHERE `team_id_fk` = " + id +
                " AND `tournament_id_fk` = " + tourId.getText() + ";";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultSet = "";
            while(rs.next()) {
                String player = rs.getString("team_id_fk");
                resultSet = resultSet + player;
                if (!resultSet.equals(""))
                    return false;

            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    int teams;
    public void displayAddedTeams(){
        teams = 0;

        String query = "SELECT * " +
                "FROM `teams`, `team_tournament`\n" +
                "WHERE `team_tournament`.`tournament_id_fk` = " + tourId.getText() +
                " AND `team_tournament`.`team_id_fk` = `teams`.`team_id`";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultSet = "";
            while(rs.next()) {
                String player = rs.getString("team_tournament.team_id_fk") + " "
                        + rs.getString("teams.name");
                resultSet = resultSet + player + "\n";
                teams ++;

            }
            displayTorTeams.setText(resultSet);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeTeam(ActionEvent actionEvent) {

        String teamId = idField.getText();
        String query = "DELETE FROM `team_tournament` WHERE `team_id_fk` = " + teamId + " AND" +
                " `tournament_id_fk` = "+ tourId.getText()+";";

        Connection con = null;
        try{
            con = DBConn.getConn();
            PreparedStatement stmt = con.prepareStatement(query);
            int rs = stmt.executeUpdate();
            displayAddedTeams();
            if(teams < 3){
            if(rs != 0)
                displayTorTeams.setText("The team has been deleted.");

            else
                displayTorTeams.setText("The team does not exist in the tournament.");
            }
            else
                popUp("Failed", "You cannot leave a tournament with less than 2 teams", "Close");
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
