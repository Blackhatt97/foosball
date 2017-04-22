package sample.Model;


import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by blackhatt on 09/04/2017.
 */
public class Match {

    private Team a;
    private Team b;
    private int score_a;
    private int score_b;
    private SimpleDateFormat date;

    public Match(Team a, Team b, int score_a, int score_b){

        this.a = a;
        this.b = b;
        this.score_a = score_a;
        this.score_b = score_b;
        this.date = date;
    }

    public Team getA() {
        return a;
    }

    public void setA(Team a) {
        this.a = a;
    }

    public Team getB() {
        return b;
    }

    public void setB(Team b) {
        this.b = b;
    }

    public int getScore_a() {
        return score_a;
    }

    public void setScore_a(int score_a) {
        this.score_a = score_a;
    }

    public int getScore_b() {
        return score_b;
    }

    public void setScore_b(int score_b) {
        this.score_b = score_b;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {

        if(date != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            try {

                //if not valid, it will throw ParseException
                if(sdf.parse(String.valueOf(date)) != null)
                    this.date = date;

            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
    }
}
