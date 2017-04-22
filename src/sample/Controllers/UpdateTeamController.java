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
import java.util.Objects;

/**
 * Created by blackhatt on 14/04/2017.
 */
public class UpdateTeamController {



    @FXML
    Button exitBtn, homeBtn, searchBtn, updateBtn;

    @FXML
    TextField plOne, plTwo, nameField, idField, rankField;

    @FXML
    TextArea searchWindow;

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
    String id1 = "", id2 = "", name = "";
    public boolean rankCheck(String id_one, String id_two){

        String rank_one = "", rank_two = "";
        if(!id_one.equals(id_two)){


            String query = "SELECT `rank` FROM `players` WHERE `player_id` = " + id_one + " OR `player_id` = " +
                    id_two + ";";
            int count = 0;


            Connection con = null;
            try{

                con = DBConn.getConn();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    if(count == 0){
                        rank_one = rs.getString("rank");
                        count ++;
                    }
                    else
                        rank_two = rs.getString("rank");

                }

                if(Objects.equals(rank_one, rankField.getText()) && Objects.equals(rank_two, rankField.getText()))
                {

                    return true;
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }
        return false;
    }
    public void updateAction(ActionEvent actionEvent) {

        String id_change_one = plOne.getText();
        String id_change_two = plTwo.getText();
        String name_change = nameField.getText();

        if(id_change_one.equals(id1) && id_change_two.equals(id2) && name_change.equals(name)){

            popUp("Nothing to change...", "No field has been altered", "Close this dialog");

        }else {
            if (!name_change.equals(name)) {

                String query1 = "UPDATE  `sql11167728`.`teams` SET  `name` =  '" + name_change +
                        "' WHERE  `teams`.`team_id` = " + idField.getText() + ";";
                Connection con = null;
                try {
                    con = DBConn.getConn();
                    PreparedStatement stmt = con.prepareStatement(query1);
                    int rs = stmt.executeUpdate();
                    searchAction(actionEvent);
                    popUp("Congrats", "Team updated", "Close this dialog");


                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
            if (!id_change_one.equals(id1) || !id_change_two.equals(id2)) {

                if (rankCheck(id_change_one, id_change_two)) {

                    String update_query = "UPDATE `players` SET `team_id_fk` = NULL WHERE `player_id` = " + id1 +
                            " OR `player_id` = " + id2 + ";";
                    String update_query_two = "UPDATE `players` SET `team_id_fk` = " + idField.getText() +
                            " WHERE `player_id` = " + id_change_one +
                            " OR `player_id` = " + id_change_two + ";";
                    Connection con = null;
                    try {
                        con = DBConn.getConn();
                        PreparedStatement stmt = con.prepareStatement(update_query);
                        PreparedStatement stmt2 = con.prepareStatement(update_query_two);
                        stmt.executeUpdate();
                        stmt2.executeUpdate();
                        searchAction(actionEvent);
                        popUp("Congrats", "Team updated", "Close this dialog");


                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }


    public void searchAction(ActionEvent actionEvent) {


        String id = idField.getText();

        String query = "SELECT * " +
                "FROM  `teams` " +
                "WHERE `team_id` = " + id + ";";


        if (id.equals(""))
            popUp("Failed!", "Please insert a valid id", "OK");


        else {

            String playerQuery = "SELECT * FROM `players` WHERE `team_id_fk` = " + id + ";";

            Connection con = null;
            try{

                con = DBConn.getConn();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String resultset = "";
                while(rs.next()){
                    String player = rs.getString("team_id") + " "
                            + rs.getString("name");
                    resultset = resultset + player +"\n";
                    name = rs.getString(2);
                    nameField.setText(name);

                }
                resultset = resultset + "Contains: \n";
                ResultSet rs2 = stmt.executeQuery(playerQuery);

                int count = 0;

                while(rs2.next()){
                    if(count == 0){
                    id1 = rs2.getString(1);
                            plOne.setText(id1);
                    count ++;}
                    else{
                        id2 = rs2.getString(1);
                        plTwo.setText((id2));
                    }
                    String player = rs2.getString("player_id") + " "
                            + rs2.getString("f_name") + " "
                            + rs2.getString("l_name") + " "
                            + rs2.getDate(4) + " "
                            + rs2.getString("email") + " ";
                    resultset = resultset + player +"\n";
                    rankField.setText(rs2.getString("rank"));
                }
                searchWindow.setText(resultset);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
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
