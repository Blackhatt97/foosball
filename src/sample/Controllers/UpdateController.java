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
public class UpdateController {

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
            if(resultset.equals(""))
                searchWindow.setText("No such player found");
            else
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

    public void updateQuery(ActionEvent actionEvent) {


        String id = idField.getText();
        String fname = f_name.getText();
        String lname = l_name.getText();
        String date = String.valueOf(date_birth.getValue());
        String em = email.getText();
        String rad = radio;
        int count = 0;
        String query = "UPDATE `players` SET ";
        //`f_name`=[value-2],`l_name`=[value-3],
        // `date_of_birth`=[value-4],`email`=[value-5],
        // `rank`=[value-6],`team_id_fk`=[value-7] WHERE `player_id` = ;"

        System.out.println(fname + " 0 " + lname + " 0 " + date + " 0 " + em + " 0 " + radio + " 0");


        if(!fname.equals("") && count == 0){
            query = query + "`f_name` = " + "'" +fname+ "'" + " ";
            count ++;
        }
        else if(!fname.equals("") && count != 0){

            query = query + ", `f_name` = " + "'" +fname + "'" + " ";
            count ++;

        }

        if(!lname.equals("") && count == 0){
            query = query + "`l_name` = " +"'" + lname+ "'" + " ";
            count ++;
        }
        else if(!lname.equals("") && count != 0){

            query = query + ", `l_name` = " +"'" + lname+ "'"+ " ";
            count ++;

        }

        if(!date.equals("null") && count == 0){
            query = query + "`date_of_birth` = " + "'" + date + "'" + " ";
            count ++;
        }
        else  if(!date.equals("null") && count != 0) {

            query = query + ", `date_of_birth` = " + "'" + date + "'"+ " ";
            count ++;

        }
        if(!em.equals("") && count == 0){
            query = query + "`email` = " + "'" + em + "'" + " ";
            count ++;
        }
        else if(!em.equals("") && count != 0){

            query = query + ", `email` = " + "'" + em + "'" + " ";
            count ++;

        }
        if(!rad.equals("") && count == 0){
            query = query + "`rank` = " + "'" + rad + "'" + " ";
            count ++;
        }
        else if(!rad.equals("") && count != 0){

            query = query + ", `rank` = " + "'" + rad+ "'" + " ";
            count ++;

        }
        query += "\n WHERE `player_id` = " + id + ";";

        Connection con = null;
        try{
            con = DBConn.getConn();
            PreparedStatement stmt = con.prepareStatement(query);
            int rs = stmt.executeUpdate();
            searchQuery(actionEvent);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(query);

    }


}
