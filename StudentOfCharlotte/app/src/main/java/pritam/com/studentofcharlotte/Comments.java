package pritam.com.studentofcharlotte;

/**
 * Created by Pritam on 7/16/16.
 */
public class Comments {
    String parentId, commentId, student, commentText; int noOfLikes;

    public Comments(){}


    public void setCommentByStudent(String commentByStudent) {
        this.student = commentByStudent;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public Comments(String parentId, String commentId,String commentByStudent, String commentText, int noOfLikes) {
        this.parentId = parentId;
        this.commentId = commentId;
        this.student = commentByStudent;
        this.commentText = commentText;
        this.noOfLikes = noOfLikes;
    }

    public String getCommentByStudent() {
        return student;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getParentId() {
        return parentId;
    }


}
