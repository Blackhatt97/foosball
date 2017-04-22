package sample.Model;

import sample.Model.Team;

/**
 * Created by blackhatt on 09/04/2017.
 */
public class Tournament {
    private Team[] contenstants;
    private String name;

    public Tournament(Team[] contestants, String name){

        this.contenstants = contestants;
        this.name = name;

    }

    public Team[] getContenstants() {
        return contenstants;
    }

    public void setContenstants(Team[] contenstants) {
        this.contenstants = contenstants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
