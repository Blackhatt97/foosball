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
import java.sql.*;

/**
 * Created by blackhatt on 17/04/2017.
 */
public class CreateTournamentController {

    @FXML
    Button exitBtn, homeBtn, createBtn, addBtn, removeBtn;

    @FXML
    TextArea displayAvPlayers, displayTorPlayers;

    @FXML
    TextField idField, tournamentName;

    @FXML
    RadioButton begRadio, intRadio, masRadio;


    @FXML
    public void exitAction(ActionEvent actionEvent) {


        deleteTeams();
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

    }
    @FXML
    public void homeMenu(ActionEvent actionEvent) throws IOException {
        deleteTeams();

        Parent player_parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
    }
    int teams = 0;
    public void addTeam(ActionEvent actionEvent) {

        String teamId = idField.getText();

        String query = "SELECT * " +
                "FROM `teams`, `players` " +
                "WHERE `players`.`rank` LIKE  '"+rank+"' " +
                "AND `players`.`team_id_fk` = " + teamId +";";
        String teamRank = "";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                String player = rs.getString("players.rank");
                teamRank = player;
                if(!teamRank.equals(rank))
                 popUp("Failed!", "The selected team has a different rank", "Close this dialog");
                else{


                    if(verifyTeams(teamId)){
                    String insertTeam = "INSERT INTO  `sql11167728`.`team_tournament` (\n" +
                            "`id` ,\n" +
                            "`tournament_id_fk` ,\n" +
                            "`team_id_fk`\n" +
                            ")\n" +
                            "VALUES (\n" +
                            "NULL , NULL , " + teamId + ");";
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
                " AND `tournament_id_fk` IS NULL;";
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
    public void deleteTeams(){

        String deleteQuery = "DELETE FROM `team_tournament` WHERE `tournament_id_fk` IS NULL";
        Connection con = null;
        try{
            con = DBConn.getConn();
            PreparedStatement stmt = con.prepareStatement(deleteQuery);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void displayAddedTeams(){

        teams = 0;
        String query = "SELECT * " +
                "FROM `teams`, `team_tournament`\n" +
                "WHERE `team_tournament`.`tournament_id_fk` IS NULL \n" +
                "AND `team_tournament`.`team_id_fk` = `teams`.`team_id`";
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
                teams ++;



            }
            displayTorPlayers.setText(resultSet);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeTeam(ActionEvent actionEvent) {

        String teamId = idField.getText();
        String query = "DELETE FROM `team_tournament` WHERE `team_id_fk` = " + teamId + " AND" +
                " `tournament_id_fk` IS NULL ;";

        Connection con = null;
        try{
            con = DBConn.getConn();
            PreparedStatement stmt = con.prepareStatement(query);
            int rs = stmt.executeUpdate();
            if(rs != 0)
            displayTorPlayers.setText("The team has been deleted.");

            else
                displayTorPlayers.setText("The team does not exist in the tournament.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    String rank = "";
    public void displayRankPlayers(ActionEvent actionEvent) {

        if(begRadio.isArmed())
            rank = "Beginner";
        else if(intRadio.isArmed())
            rank = "Intermediate";
        else if(masRadio.isArmed())
            rank = "Master";

        String query = "SELECT * \n" +
                "FROM `teams`, `players`\n" +
                "WHERE `players`.`rank` LIKE  '"+rank+"'\n" +
                "AND `teams`.`team_id` = `players`.`team_id_fk`\n" +
                "LIMIT 0 , 30";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultset = "";


            while(rs.next()){
                String player =" Team:" + rs.getString("team_id") + " "
                        + rs.getString("name") + " " + " with player"
                        + rs.getString("player_id") + " "
                        + rs.getString("f_name") + " "
                        + rs.getString("l_name") + " "
                        + rs.getDate("date_of_birth") + " ";
                resultset = resultset + player +"\n";
            }
            if(!resultset.equals(""))
            displayAvPlayers.setText(resultset);
            else displayAvPlayers.setText("No teams with the given rank");
            rs.close();
            stmt.close();
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

    public void createTournament(ActionEvent actionEvent) {

        if(teams < 2)
            popUp("Failed", "Cannot create a tournament with less than 2 teams", "OK");
        else
        {

            String createQuery = "INSERT INTO  `sql11167728`.`tournaments` (\n" +
                    "`tournament_id` ,\n" +
                    "`name` ,\n" +
                    "`winner` ,\n" +
                    "`rank`\n" +
                    ")\n" +
                    "VALUES (\n" +
                    "NULL ,  '"+tournamentName.getText()+"',  NULL,  '"+rank+"'\n" +
                    ");";
            String queryForId = "SELECT `tournament_id` FROM `tournaments`\n" +
                    "ORDER BY `tournament_id` DESC\n" +
                    "LIMIT 1;";
            Connection con = null;
            try {
                con = DBConn.getConn();

                Statement stmt = con.createStatement();
                stmt.executeUpdate(createQuery);
                ResultSet rs = stmt.executeQuery(queryForId);
                String id_team = "";
                while(rs.next())
                    id_team = rs.getString(1);


                String updateTab = "UPDATE  `team_tournament` SET  `tournament_id_fk` =  '"+ id_team +"' " +
                        "WHERE `tournament_id_fk` IS NULL;";
                stmt.executeUpdate(updateTab);
                con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }


        }
    }
}
