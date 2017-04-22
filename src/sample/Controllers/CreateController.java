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
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by blackhatt on 10/04/2017.
 */
public class CreateController{
    @FXML
    Button homeBtn, exitBtn, createBtn;


    @FXML
    TextField f_name, l_name, email;
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
    public void homeMenu(ActionEvent actionEvent) throws IOException{

        Parent player_parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene player_scene = new Scene(player_parent);
        Stage player_stage = (Stage) homeBtn.getScene().getWindow();
        player_stage.setScene(player_scene);
        player_stage.show();
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

    public void createPlayer(ActionEvent actionEvent) throws SQLException{

        Connection con = null;
        try {
            con = DBConn.getConn();


            if(!f_name.getText().equals(null) &&
                    !l_name.getText().equals(null) &&
                    !email.getText().equals(null) && !radio.equals("")) {
                String sql = "INSERT INTO `sql11167728`.`players` " +
                        "(`player_id`, `f_name`, `l_name`, `date_of_birth`, `email`, `rank`, `team_id_fk`) " +
                        "VALUES (NULL, " +
                        "'" + f_name.getText() + "', '" + l_name.getText() + "', " +
                        "'" + date_birth.getValue() + "', '" + email.getText() + "', " +
                        "'" + radio + "', NULL);";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);

                Stage popupwindow = new Stage();

                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Congrats");

                Label label1 = new Label("You have created a new player");

                Button button1 = new Button("Close this dialog");

                button1.setOnAction(e -> popupwindow.close());


                VBox layout = new VBox(10);


                layout.getChildren().addAll(label1, button1);
                layout.setAlignment(Pos.CENTER);

                Scene scene1 = new Scene(layout, 300, 250);

                popupwindow.setScene(scene1);
                popupwindow.showAndWait();

                con.close();
            }
            else{
                Stage popupwindow = new Stage();

                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Failed!");

                Label label1 = new Label("Please check again the data");

                Button button1 = new Button("Close this dialog");

                button1.setOnAction(e -> popupwindow.close());


                VBox layout = new VBox(10);


                layout.getChildren().addAll(label1, button1);
                layout.setAlignment(Pos.CENTER);

                Scene scene1 = new Scene(layout, 300, 250);

                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


}
