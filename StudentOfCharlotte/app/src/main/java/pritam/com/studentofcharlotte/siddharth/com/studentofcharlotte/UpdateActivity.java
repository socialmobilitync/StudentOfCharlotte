package pritam.com.studentofcharlotte.siddharth.com.studentofcharlotte;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import pritam.com.studentofcharlotte.LoginActivity;
import pritam.com.studentofcharlotte.R;

public class UpdateActivity extends AppCompatActivity {

    Student student;
    EditText updateFname,updateLname,updateClass,updateSchool,updateRank;
    TextView fname,lname,cls,school,rank;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        student=(Student)getIntent().getExtras().getSerializable("STUDENT_KEY");


        Log.d("updateAct",student.getFname());

        updateFname=(EditText) findViewById(R.id.update_fname);
        updateLname=(EditText) findViewById(R.id.update_lname);
        updateClass=(EditText) findViewById(R.id.update_class);
        updateSchool=(EditText) findViewById(R.id.update_school);
        updateRank=(EditText) findViewById(R.id.update_rank);

        fname=(TextView) findViewById(R.id.textView4);
        lname=(TextView) findViewById(R.id.textView5);
        cls=(TextView) findViewById(R.id.txt_update_class);
        school=(TextView) findViewById(R.id.txt_update_schl);
        rank=(TextView) findViewById(R.id.txt_update_rank);

        dbRef= FirebaseDatabase.getInstance().getReference();


        if(getIntent().getAction()=="100") {

            //Setting visibility to firstname and lastname for updation
            updateClass.setVisibility(View.GONE);
            updateSchool.setVisibility(View.GONE);
            updateRank.setVisibility(View.GONE);
            cls.setVisibility(View.GONE);
            school.setVisibility(View.GONE);
            rank.setVisibility(View.GONE);
            updateFname.setVisibility(View.VISIBLE);
            updateLname.setVisibility(View.VISIBLE);
            fname.setVisibility(View.VISIBLE);
            lname.setVisibility(View.VISIBLE);
            updateFname.setText(student.getFname());
            updateLname.setText(student.getLname());
        } else if(getIntent().getAction()=="200") {
            //Setting visibility to class,school and section for updation
            updateClass.setVisibility(View.VISIBLE);
            updateSchool.setVisibility(View.VISIBLE);
            updateRank.setVisibility(View.VISIBLE);
            cls.setVisibility(View.VISIBLE);
            school.setVisibility(View.VISIBLE);
            rank.setVisibility(View.VISIBLE);
            updateFname.setVisibility(View.GONE);
            updateLname.setVisibility(View.GONE);
            fname.setVisibility(View.GONE);
            lname.setVisibility(View.GONE);
        }

        updateFname.setHint(student.getFname());
        findViewById(R.id.update_cnfm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("updateAct","Clicked on Confirm");


                //Alert dialog for the confirmation to update and update only when confirmation is positive
                AlertDialog.Builder builder=new AlertDialog.Builder(UpdateActivity.this);
                builder.setTitle("Confirmation Box")
                        .setMessage("Wanna give a second thought.Have you decided?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //If action =="100" update the firstname and lastname else update personal info
                                if(getIntent().getAction()=="100")
                                {

                                    //Updating the student object
                                    student.setFname(updateFname.getText().toString());
                                    student.setLname(updateLname.getText().toString());

                                    //Updating the database
                                    dbRef.child("students").child(LoginActivity.uId).child("fname").setValue(updateFname.getText().toString());
                                    dbRef.child("students").child(LoginActivity.uId).child("lname").setValue(updateLname.getText().toString());

                                    //Send value back to the profile
                                    Intent intent=new Intent();
                                    intent.putExtra("100",student);
                                    setResult(RESULT_OK);
                                    finish();

                                }
                                else if(getIntent().getAction()=="200")
                                {

                                    //Updating the object
                                    student.setStuClass(updateClass.getText().toString());
                                    student.setSchool(updateSchool.getText().toString());
                                    student.setRank(updateRank.getText().toString());

                                    //Updating the database
                                    dbRef.child("students").child(LoginActivity.uId).child("class").setValue(updateClass.getText().toString());
                                    dbRef.child("students").child(LoginActivity.uId).child("school").setValue(updateSchool.getText().toString());
                                    dbRef.child("students").child(LoginActivity.uId).child("rank").setValue(updateRank.getText().toString());


                                    //Send value back to the profile
                                    Intent intent=new Intent();
                                    intent.putExtra("200",student);
                                    setResult(RESULT_OK);
                                    finish();


                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("alert","clicked No");

                            }
                        });

                AlertDialog alert= builder.create();
                alert.show();
            }
        });





    }
}
