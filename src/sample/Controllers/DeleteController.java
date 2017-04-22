package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBConn;
import sample.Model.Player;

import java.io.IOException;
import java.sql.*;

/**
 * Created by blackhatt on 10/04/2017.
 */
public class DeleteController {

    @FXML
    Button homeBtn, exitBtn, createBtn;


    @FXML
    TextField idField, f_name, l_name, email;
    @FXML
    TextArea searchWindow;
    @FXML
    DatePicker date_birth;
    @FXML
    RadioButton begRadio, intRadio, masRadio;


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

    public void searchQuery(ActionEvent actionEvent) {

        String query = "SELECT * " +
                "FROM  `players` " +
                "WHERE ";

        String id = idField.getText();


        if(!id.equals(""))
            query = query + "`player_id` = " + id + " ";
        query = query + ";";

        Connection con = null;
        try{
            con = DBConn.getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String resultset = "";
            String fname = "";
            String lname = "";
            String date = "";
            String em = "";
            String rad = "";
            while(rs.next()){
                String player = rs.getString("player_id") + " "
                        + rs.getString("f_name") + " "
                        + rs.getString("l_name") + " "
                        + rs.getDate(4) + " "
                        + rs.getString("email") + " "
                        + rs.getString("rank");
                resultset = resultset + player +"\n";
                fname = rs.getString(2);
                lname = rs.getString(3);
                date = rs.getString(4);
                em = rs.getString(5);
                rad = rs.getString(6);


            }
            if(resultset.equals("")){
                searchWindow.setText("No such player found");
            }
            else
                searchWindow.setText(resultset);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(query);

    }


    public void deleteAction(ActionEvent actionEvent) {

        String id = idField.getText();

        String query = "DELETE FROM `players` WHERE `player_id` = " + id + ";";

        Connection con = null;
        try{
            con = DBConn.getConn();
            PreparedStatement stmt = con.prepareStatement(query);
            int rs = stmt.executeUpdate();
            if(rs != 0)
           searchWindow.setText("The player has been deleted.");
            else
                searchWindow.setText("The player does not exist.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(query);

    }

}

