package pritam.com.studentofcharlotte;

import java.util.List;

/**
 * Created by Pritam on 7/16/16.
 */
public class Answers {
    String answerByStudent, answerText;
    List<String> StudentList;

    public void setAnswerByStudent(String answerByStudent) {
        this.answerByStudent = answerByStudent;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setStudentList(List<String> studentList) {
        StudentList = studentList;
    }

    public String getAnswerByStudent() {
        return answerByStudent;
    }

    public String getAnswerText() {
        return answerText;
    }

    public List<String> getStudentList() {
        return StudentList;
    }
}
