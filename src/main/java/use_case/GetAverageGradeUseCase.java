package use_case;
import api.GradeDB;
import entity.Grade;
import entity.Team;

public final class GetAverageGradeUseCase {
    private final GradeDB gradeDB;

    public GetAverageGradeUseCase(GradeDB gradeDB) {
        this.gradeDB = gradeDB;
    }

    public float getAverageGrade(String course) {
        Team team = gradeDB.getMyTeam();

        if (team == null) {
            throw new RuntimeException("You are not in a team.");
        }

        String[] members = team.getMembers();
        int total_grade = 0;
        int validMembers = 0;
        for (String member : members) {
            try {
                total_grade += gradeDB.getGrade(member, course).getGrade();
                validMembers += 1;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return ((float) total_grade) / validMembers;
    }
}
