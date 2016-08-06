package pritam.com.studentofcharlotte.school;

/**
 * Created by LibraryGuest2 on 8/5/2016.
 */
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pritam.com.studentofcharlotte.R;

public class SchoolProfileActivity extends AppCompatActivity implements StudentAddFragment.OnFragmentInteractionListener{

    TextView school_Name,school_NoOfStudents,school_Desc;
    ListView students_List;
    static Student student;
    String schoolNoOfStudents;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_activity_school_profile);

        school_Name=(TextView)findViewById(R.id.school_profile_name);
        school_NoOfStudents=(TextView)findViewById(R.id.school_profile_number_students);
        school_Desc=(TextView)findViewById(R.id.school_profile_desc);
        students_List=(ListView)findViewById(R.id.school_profile_student_list);

        //shows the student profile on clicking of each student
        students_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SchoolProfileActivity.this);
                builder.setTitle("Code  to link the student profile")
                        .setCancelable(true);

            }
        });

        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
        dbRef.child("schools").child("school_1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Fetch values from the database and store in a variable
                String schoolName=dataSnapshot.child("name").getValue().toString();
                String schoolDesc=dataSnapshot.child("location").getValue().toString();
                schoolNoOfStudents=dataSnapshot.child("students").getChildrenCount()+"";

                //listview operation
                ArrayList<String> studentsList=new ArrayList<String>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.child("students").getChildren())
                {
                    studentsList.add(dataSnapshot1.child("fname").getValue().toString());
                }
                adapter=new StudentAdapter(SchoolProfileActivity.this,R.layout.interest_list,studentsList);
                adapter.setNotifyOnChange(true);

                Log.d("schoolname",schoolName);

                //Setting the fetched values in the UI
                school_Name.setText(schoolName);
                school_NoOfStudents.setText("Strength: "+schoolNoOfStudents);
                school_Desc.setText("Location: "+schoolDesc);
                students_List.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Sending the student data to update activity using intents on click
        findViewById(R.id.btn_student_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("addstudent","add student button clicked");
                getFragmentManager().beginTransaction().add(R.id.school_container,new StudentAddFragment(),"addStudent").commit();
            }
        });

    }

    @Override
    public void onFragmentInteraction(Student student, Parent parent) {
        Log.d("testttttt", student+"");
        int noOfStudents=Integer.parseInt(schoolNoOfStudents)+1;

        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
        dbRef.child("schools").child("school_1").child("students").child("S"+noOfStudents).child("fname").setValue(student.getFname());
        dbRef.child("schools").child("school_1").child("students").child("S"+noOfStudents).child("lname").setValue(student.getLname());
        dbRef.child("schools").child("school_1").child("students").child("S"+noOfStudents).child("classStudy").setValue(student.getStuClass());
        dbRef.child("schools").child("school_1").child("students").child("S"+noOfStudents).child("rank").setValue(student.getRank());
        dbRef.child("schools").child("school_1").child("students").child("S"+noOfStudents).child("parents").child("P1").setValue(parent.getParent_fname());
        adapter.add(student.getFname());
        adapter.notifyDataSetChanged();
        Toast.makeText(SchoolProfileActivity.this, "Student added to the database", Toast.LENGTH_SHORT).show();

    }

}

