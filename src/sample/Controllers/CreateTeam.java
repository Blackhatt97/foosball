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
 * Created by blackhatt on 13/04/2017.
 */
public class CreateTeam {

    @FXML
    Button homeBtn, exitBtn, createBtn, showPlayerBtn;


    @FXML
    TextField player1, player2, teamName;

    @FXML
    TextArea textArea;


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

    public void showPlayers(ActionEvent actionEvent) {

        String query = "SELECT * " +
                "FROM  `players` " +
                "WHERE `team_id_fk` IS NULL;";

        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultset = "";
            while(rs.next()){
                String player = rs.getString("player_id") + " "
                        + rs.getString("f_name") + " "
                        + rs.getString("l_name") + " "
                        + rs.getDate(4) + " "
                        + rs.getString("email") + " "
                        + rs.getString("rank");
                resultset = resultset + player +"\n";

            }
            if(resultset.equals(""))
                textArea.setText("No players are available now to form teams...");
            else
            textArea.setText(resultset);
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(query);

    }

    public void createTeam(ActionEvent actionEvent) {

        String player_one = player1.getText();
        String player_two = player2.getText();
        String team_name = teamName.getText();
        String team_id = "";

        String rank1 = verify(player_one);
        String rank2 = verify(player_two);



        if(player_one.equals("") || player_two.equals("") || team_name.equals("")){
            popUp("Failed!","Please fill in all the dialogs","Close this dialog");

        }
        else if(!rank1.equals(rank2)){
            popUp("Failed!","The two players don't have the same rank", "Close this dialog");

        }else if(!verify2(player_one) || !verify2(player_two)){

            popUp("Failed!","The selected players cannot be teamed", "Close this dialog");
            System.out.println(verify2(player_one));
            System.out.println(verify2(player_two));


        }

        else{

            String query = "INSERT INTO `sql11167728`.`teams` " +
                    "(`team_id`, `name`) VALUES (NULL, '" +team_name+ "');";
            String queryForId = "SELECT team_id FROM teams\n" +
                    "ORDER BY team_id DESC\n" +
                    "LIMIT 1;";

            Connection con = null;
            try {
                con = DBConn.getConn();

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    ResultSet rs = stmt.executeQuery(queryForId);
                String id_team = "";
                while(rs.next())
                        id_team = rs.getString(1);


                String updateQ = "UPDATE `players` SET `team_id_fk`= " + id_team + " WHERE `player_id` = "
                        + player_one + " OR `player_id` = " + player_two + ";";
                stmt.executeUpdate(updateQ);
                    con.close();
                }
            catch (SQLException e){
                e.printStackTrace();
            }


            popUp("Congrats","Team " + team_name + " has been created", "Close this dialog");



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

    public String verify(String id){

        String rank = "";
        String query = "SELECT `rank` FROM `players` WHERE `player_id` = " + id + ";";
        Connection con = null;
        try{

            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String player = rs.getString("rank");
                rank = player;

            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return rank;
    }
    public boolean verify2(String id) throws NullPointerException{

        int pler = 0;
        String query = "SELECT `team_id_fk` FROM `players` WHERE `player_id` = " + id + ";";
        Connection con = null;
        try{


            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                pler = rs.getInt("team_id_fk");

                System.out.println(pler);
                if(rs.wasNull()){
                    return true;
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;


    }
}
