package pritam.com.studentofcharlotte;

import java.util.List;

/**
 * Created by Pritam on 7/16/16.
 */
public class Achievements {
    String aStudent, achievement, interest, imageUrl, key,groupIcon;
    List<String> likedByStudents, sharedByStudents;
    List<Comments> commentsList;


    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public Achievements(){

    }
    public Achievements(String achievementByStudent, String achievementText, List<String> likedByStudents, List<String> sharedByStudents, List<Comments> commentsList, String interest, String postUrl, String key) {
        this.aStudent = achievementByStudent;
        this.achievement = achievementText;
        this.likedByStudents = likedByStudents;
        this.sharedByStudents = sharedByStudents;
        this.commentsList = commentsList;
        this.interest = interest;
        this.imageUrl = postUrl;
        this.key = key;
    }

    public void setAchievementByStudent(String achievementByStudent) {
        this.aStudent = achievementByStudent;
    }

    public void setAchievementText(String achievementText) {
        this.achievement = achievementText;
    }

    public void setLikedByStudents(List<String> likedByStudents) {
        this.likedByStudents = likedByStudents;
    }

    public void setSharedByStudents(List<String> sharedByStudents) {
        this.sharedByStudents = sharedByStudents;
    }

    public String getAchievementByStudent() {
        return aStudent;
    }

    public String getAchievementText() {
        return achievement;
    }

    public List<String> getLikedByStudents() {
        return likedByStudents;
    }

    public List<String> getSharedByStudents() {
        return sharedByStudents;
    }

    public String getInterest() {
        return interest;
    }

    public String getPostUrl() {
        return imageUrl;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setPostUrl(String postUrl) {
        this.imageUrl = postUrl;
    }
}
