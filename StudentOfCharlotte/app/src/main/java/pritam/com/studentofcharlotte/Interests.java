package pritam.com.studentofcharlotte;

import java.util.List;

/**
 * Created by Pritam on 7/16/16.
 */
public class Interests {
    String iName;
    List<Achievements> achievementsList;
    List<Questions> questionsList;
    List<StudentsInInterest> interestedStudents;

    public void setiName(String iName) {
        this.iName = iName;
    }

    public void setAchievementsList(List<Achievements> achievementsList) {
        this.achievementsList = achievementsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public void setInterestedStudents(List<StudentsInInterest> interestedStudents) {
        this.interestedStudents = interestedStudents;
    }

    public String getiName() {
        return iName;
    }

    public List<Achievements> getAchievementsList() {
        return achievementsList;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public List<StudentsInInterest> getInterestedStudents() {
        return interestedStudents;
    }
}
