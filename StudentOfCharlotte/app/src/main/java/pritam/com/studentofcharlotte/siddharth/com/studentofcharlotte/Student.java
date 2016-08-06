package pritam.com.studentofcharlotte.siddharth.com.studentofcharlotte;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishal on 7/19/2016.
 */
public class Student implements Serializable{

    String fname,lname,stuClass,school,rank;
    ArrayList<String> interests;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }
}
