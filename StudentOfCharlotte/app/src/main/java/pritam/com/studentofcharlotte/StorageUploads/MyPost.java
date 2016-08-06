package pritam.com.studentofcharlotte.StorageUploads;

/**
 * Created by Pritam on 8/4/16.
 */
public class MyPost {
    String aStudent, achievement,imageUrl, interest, key;
    int noOfLikes;

    public MyPost(String aStudent, String achievement, int noOfLikes, String imageUrl, String interest, String key) {
        this.aStudent = aStudent;
        this.achievement = achievement;
        this.noOfLikes = noOfLikes;
        this.imageUrl = imageUrl;
        this.interest = interest;
        this.key = key;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInterest() {
        return interest;
    }

    public String getKey() {
        return key;
    }

    public void setaStudent(String aStudent) {
        this.aStudent = aStudent;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String getaStudent() {
        return aStudent;
    }

    public String getAchievement() {
        return achievement;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }
}
