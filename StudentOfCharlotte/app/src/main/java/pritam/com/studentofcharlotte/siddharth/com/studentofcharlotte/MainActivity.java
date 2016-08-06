package pritam.com.studentofcharlotte.siddharth.com.studentofcharlotte;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pritam.com.studentofcharlotte.R;

public class MainActivity extends AppCompatActivity {

    TextView prof_fname,prof_lname,prof_class,prof_school,prof_rank;
    ListView interests;
    static Student student;
    int noOfInterest;
    ArrayList<String> interestList;
    InterestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prof_fname=(TextView)findViewById(R.id.profile_fname);
        prof_lname=(TextView)findViewById(R.id.profile_lname);
        prof_class=(TextView)findViewById(R.id.profile_class);
        prof_school=(TextView)findViewById(R.id.profile_school);
        prof_rank=(TextView)findViewById(R.id.profile_rank);
        interests=(ListView)findViewById(R.id.listView);

        final DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
        dbRef.child("students").child("student_1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Fetch values from the database and store in a variable
                String firstname=dataSnapshot.child("fname").getValue().toString();
                String lname=dataSnapshot.child("lname").getValue().toString();
                String student_class=dataSnapshot.child("class").getValue().toString();
                String school=dataSnapshot.child("school").getValue().toString();
                String rank=dataSnapshot.child("rank").getValue().toString();

                //fetch interest and count number of interest
                interestList=new ArrayList<String>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.child("interest").getChildren())
                {
                    interestList.add(dataSnapshot1.child("iName").getValue().toString());
                }
                noOfInterest=(int)dataSnapshot.child("interest").getChildrenCount();
                interests=(ListView)findViewById(R.id.listView);
                adapter=new InterestAdapter(MainActivity.this,R.layout.interest_list,interestList);
                adapter.setNotifyOnChange(true);

                Log.d("firstname",firstname);

                //Setting the fetched values in the UI
                prof_fname.setText(" "+firstname);
                prof_lname.setText(" "+lname+" ");
                prof_class.setText("Class: "+student_class);
                prof_school.setText("School: "+school);
                prof_rank.setText("Rank: "+rank);
                interests.setAdapter(adapter);

                //Setting all the fetcched value in the object
                student=new Student();
                student.setFname(firstname);
                student.setLname(lname);
                student.setStuClass(student_class);
                student.setSchool(school);
                student.setRank(rank);
                student.setInterests(interestList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Sending the student Name to update activity using intents on click
        findViewById(R.id.profile_img_update_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("STUDENT_KEY",student);
                intent.setAction("100");
                startActivityForResult(intent,100);
            }
        });

        //Sending the student Personal Info to update activity using intents on click
        findViewById(R.id.profile_img_update_personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("STUDENT_KEY",student);
                intent.setAction("200");
                startActivityForResult(intent,200);
            }
        });



        //On Clicking of the update icon for the interest field
          findViewById(R.id.interestEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String>  hobbyChoice=new ArrayList<String>();

                //All interest present in the app is stored in the arraylist named "hobbies"
                //Following part needs to be edited as new interest group are being added to the application/System
                //This part could be made global according to the requirement.
                final ArrayList<String> hobbies=new ArrayList<String>();
                hobbies.add("Study");
                hobbies.add("Badminton");
                hobbies.add("Squash");
                hobbies.add("Swimming");
                hobbies.add("Cricket");
                hobbies.add("Coding");

                //keeping only those interests which the student has not subscribed yet
                for(int i=0;i<interestList.size();i++)
                {
                    if(hobbies.contains(interestList.get(i)))
                    {
                        hobbies.remove(interestList.get(i));
                    }
                }

                //Converting list to show into charsequence
                //Charsequence is one of the parameter for setting multichoice items in alert dialog builder
                final CharSequence[] listHobbiesToChoose=hobbies.toArray(new CharSequence[hobbies.size()]);

                //Alert box showing all the interests
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Join your Group")
                        .setMultiChoiceItems(listHobbiesToChoose, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Log.d("interestupdate","Selected item:"+listHobbiesToChoose[which]);
                                hobbyChoice.add(listHobbiesToChoose[which].toString());
                            }
                        }).setPositiveButton("Select", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  Log.d("interestupdatepositive","interest selected="+hobbyChoice);
                                  for(int i=0;i<hobbyChoice.size();i++) {
                                      Achievement achObj = new Achievement();
                                      achObj.setAchievement("default");
                                      Interest intObj = new Interest();
                                      int idCode = ++noOfInterest; //Incrementing the interests by 1 to set a new id of the object
                                      Log.d("noInterests",noOfInterest+"");
                                      intObj.setiAchievement(achObj);
                                      intObj.setiName(hobbyChoice.get(i));
                                      intObj.setiPoint("5");
                                      intObj.setiRank("1");
                                      dbRef.child("students").child("student_1").child("interest").child("interest_" +idCode).setValue(intObj);
                                      dbRef.child("students").child("student_1").child("interest").child("interest_" +idCode).child("iAchievement").child("A1").setValue(achObj);
                                      adapter.add(hobbyChoice.get(i));
                                  }

                        }
                         })
                        .show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            if (requestCode == 100 || requestCode ==200) {
                DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
                dbRef.child("students").child("student_1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Fetch values from the database and store in a variable
                        String firstname=dataSnapshot.child("fname").getValue().toString();
                        String lname=dataSnapshot.child("lname").getValue().toString();
                        String student_class=dataSnapshot.child("class").getValue().toString();
                        String school=dataSnapshot.child("school").getValue().toString();
                        String rank=dataSnapshot.child("rank").getValue().toString();
                        ArrayList<String> interestList=new ArrayList<String>();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.child("interests").getChildren())
                        {
                            interestList.add(dataSnapshot1.child("iName").getValue().toString());
                        }
                        interests=(ListView)findViewById(R.id.listView);
                        InterestAdapter adapter=new InterestAdapter(MainActivity.this,R.layout.interest_list,interestList);

                        Log.d("firstname",firstname);

                        //Setting the fetched values in the UI
                        prof_fname.setText(" "+firstname);
                        prof_lname.setText(" "+lname+" ");
                        prof_class.setText("Class: "+student_class);
                        prof_school.setText("School: "+school);
                        prof_rank.setText("Rank: "+rank);
                        interests.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
    }
}
