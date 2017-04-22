package sample.Model;

import sample.Model.Player;

/**
 * Created by blackhatt on 09/04/2017.
 */
public class Team {

    private Player first;
    private Player second;
    private String team_name;
    private int wins = 0;
    private int loses = 0;

    public Team(Player first, Player second, String team_name, int wins, int loses){

        this.first = first;
        this.second = second;
        this.team_name = team_name;
        this.wins = wins;
        this.loses = loses;
    }

    public Player getFirst() {
        return first;
    }

    public void setFirst(Player first) {
        this.first = first;
    }

    public Player getSecond() {
        return second;
    }

    public void setSecond(Player second) {
        this.second = second;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
}
