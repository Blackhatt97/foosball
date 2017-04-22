package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBConn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by blackhatt on 10/04/2017.
 */
public class SearchController {

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
        int count = 0;

        String id = idField.getText();
        String fname = f_name.getText();
        String lname = l_name.getText();
        String date = String.valueOf(date_birth.getValue());
        String em = email.getText();
        String rad = radio;
        if(!id.equals("") && count == 0){
            query = query + "`player_id` = " + id + " ";
            count ++;
        } else if(!id.equals("") && count != 0){

            query = query + "AND `player_id` = " + id + " ";
            count ++;

        }
        if(!fname.equals("") && count == 0){
            query = query + "`f_name` LIKE " + "'" +fname+ "'" + " ";
            count ++;
        }
        else if(!fname.equals("") && count != 0){

            query = query + "AND `f_name` LIKE " + "'" +fname + "'" + " ";
            count ++;

        }
        if(!lname.equals("") && count == 0){
            query = query + "`l_name` LIKE " +"'" + lname+ "'" + " ";
            count ++;
        }
        else if(!lname.equals("") && count != 0){

            query = query + "AND `l_name` LIKE " +"'" + lname+ "'"+ " ";
            count ++;

        }

        if(!date.equals("null") && count == 0){
            query = query + "`date_of_birth` = " + "'" + date + "'" + " ";
            count ++;
        }
        else  if(!date.equals("null") && count != 0) {

            query = query + "AND `date_of_birth` = " + "'" + date + "'"+ " ";
            count ++;

        }
        if(!em.equals("") && count == 0){
            query = query + "`email` LIKE " + "'" + em + "'" + " ";
            count ++;
        }
        else if(!em.equals("") && count != 0){

            query = query + "AND `email` LIKE " + "'" + em + "'" + " ";
            count ++;

        }
        if(!rad.equals("") && count == 0){
            query = query + "`rank` LIKE " + "'" + rad + "'" + " ";
            count ++;
        }
        else if(!rad.equals("") && count != 0){

            query = query + "AND `rank` LIKE " + "'" + rad+ "'" + " ";
            count ++;

        }
        query = query + ";";

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
            searchWindow.setText(resultset);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(query);

        
    }
String radio = "";
    public void handleRank(ActionEvent actionEvent) {
        if(begRadio.isArmed())
            radio = "Beginner";
        else if(intRadio.isArmed())
            radio = "Intermediate";
        else if(masRadio.isArmed())
            radio = "Master";

        System.out.println(radio);
        System.out.println(date_birth.getValue());



    }
}
