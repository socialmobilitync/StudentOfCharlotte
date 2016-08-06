package pritam.com.studentofcharlotte;

import java.util.List;

/**
 * Created by Pritam on 7/16/16.
 */
public class Questions {
    String questionByStudent, questionText, interest, questionKey;
    List<Answers> answersList;
    List<Comments> commentsList;

    public void setQuestionByStudent(String questionByStudent) {
        this.questionByStudent = questionByStudent;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswersList(List<Answers> answersList) {
        this.answersList = answersList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public String getQuestionByStudent() {
        return questionByStudent;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<Answers> getAnswersList() {
        return answersList;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }


    public String getInterest() {
        return interest;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }
}
