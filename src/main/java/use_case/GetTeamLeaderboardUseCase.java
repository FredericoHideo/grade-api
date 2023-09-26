package use_case;

import api.GradeDB;
import entity.Team;

import java.util.*;

public class GetTeamLeaderboardUseCase {
    private final GradeDB gradeDB;

    public GetTeamLeaderboardUseCase(GradeDB gradeDB) {
        this.gradeDB = gradeDB;
    }

    public String getTeamLeaderboard(String course) {
        Team team = gradeDB.getMyTeam();

        if (team == null) {
            throw new RuntimeException("You are not in a team.");
        }

        String[] members = team.getMembers();
        Map<String, Integer> grades = new Hashtable<>();

        for (String member : members) {
            grades.put(member, gradeDB.getGrade(member, course).getGrade());
        }
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(List.copyOf(grades.entrySet()));

        entries.sort((o1, o2) -> o1.getValue() - o2.getValue());

        StringBuilder result = new StringBuilder();
        for (Map.Entry entry : entries) {
            if (result.length() != 0) {
                result.append("\n");
            }
            result.append(entry.getKey());
            result.append(": ");
            result.append(entry.getValue());
        }

        return result.toString();
    }

}
