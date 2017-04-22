package sample.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by blackhatt on 09/04/2017.
 */
public class Player {

    private String f_name;
    private String l_name;
    private SimpleDateFormat date_of_birth = new SimpleDateFormat("yyyy-MM-dd");
    private String email;
    private String rank;
    public Player(){

    }

    public Player(String f_name, String l_name, SimpleDateFormat date_of_birth, String email, String rank){

        this.f_name = f_name;
        this.l_name = l_name;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.rank = rank;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        if(f_name.length() == 0)
            System.out.println("No name entered");
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        if(f_name.length() == 0)
            System.out.println("No name entered");
        this.l_name = l_name;
    }

    public SimpleDateFormat getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(SimpleDateFormat date_of_birth) {

        if(date_of_birth != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            try {

                //if not valid, it will throw ParseException
                if(sdf.parse(String.valueOf(date_of_birth)) != null)
                    this.date_of_birth = date_of_birth;

            } catch (ParseException e) {

                e.printStackTrace();
            }
        }


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.contains("@"))
        this.email = email;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        if(rank.equals("beginner") || rank.equals("intermediate") || rank.equals("master"))
        this.rank = rank;
    }
}
