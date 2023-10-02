package FOOTBALL_GAME;

import java.io.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



class Team{
   String name;
    int wins;
    int loses;
    int gamesPlayed;
    int draws;
    int goalScored;
    int goalConceded;


    public Team(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getDraws() {
        return draws;
    }

    public int getGoalScored() {
        return goalScored;
    }

    public int getGoalConceded() {
        return goalConceded;
    }

    public int goalDiff(){
        return goalScored - goalConceded;
    }


    public int getTotalPoints(){
        return wins * 3 + draws;
    }



    //Liverpool          9    8    0
    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", name, gamesPlayed, wins, draws, loses, getTotalPoints());
    }
}


class FootballTable{

    Map<String,Team> teamsMap;


    final static Comparator<Team> cmp= Comparator.comparingInt(Team::getTotalPoints)
                                                 .thenComparingInt(Team::goalDiff).reversed()
                                                 .thenComparing(Team::getName);

    public FootballTable() {
        teamsMap=new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Team home = teamsMap.computeIfAbsent(homeTeam, key -> new Team(homeTeam));
        Team away = teamsMap.computeIfAbsent(awayTeam, key -> new Team(awayTeam));

        home.goalScored +=homeGoals;
        home.goalConceded +=awayGoals;

        away.goalScored +=awayGoals;
        away.goalConceded +=homeGoals;

        home.gamesPlayed++;
        away.gamesPlayed++;

        if(homeGoals > awayGoals){
            home.wins++;
            away.loses++;

        }else if(homeGoals < awayGoals ){
            home.loses++;
            away.wins++;
        }else{
            home.draws++;
            away.draws++;
        }

    }


    public void printTable() {
        List<Team> tmp= teamsMap.values()
                                 .stream()
                                 .sorted(cmp)
                                 .collect(Collectors.toList());

        IntStream.range(0, tmp.size()).forEach(i -> {
            String rankFormat = (i < 9) ? " %d. %s\n" : "%d. %s\n";
            int rank = i + 1;
            System.out.format(rankFormat, rank, tmp.get(i));
        });


    }
}




public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}


