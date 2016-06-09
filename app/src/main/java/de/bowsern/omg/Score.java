package de.bowsern.omg;

/**
 * Created by felix on 09-Jun-16.
 */
public class Score implements Comparable<Score> {

    public Score(String userLabel){
        this.userLabel = userLabel;
    }


    public String userLabel;
    public int points;

    public int compareTo(Score other) {
        if (points < other.points) {
            return -1;
        } else if (points == other.points){
            return 0;
        } else {
            return 1;
        }
    }
}
