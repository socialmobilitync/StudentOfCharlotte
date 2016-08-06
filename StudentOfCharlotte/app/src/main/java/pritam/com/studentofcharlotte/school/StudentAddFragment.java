package pritam.com.studentofcharlotte.school;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import pritam.com.studentofcharlotte.R;


public class StudentAddFragment extends Fragment {

    EditText stu_fname;
    EditText stu_lname;
    EditText stu_class;
    EditText stu_rank;
    EditText parent_fname;
    EditText parent_lname;

    OnFragmentInteractionListener mListener;

    public StudentAddFragment() {
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        setHasOptionsMenu(false);
        if (context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()) ;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.school_fragment_student_add, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stu_fname=(EditText)getActivity().findViewById(R.id.add_student_firstname);
        stu_lname=(EditText)getActivity().findViewById(R.id.add_student_lastname);
        stu_class=(EditText)getActivity().findViewById(R.id.add_student_class);
        stu_rank=(EditText)getActivity().findViewById(R.id.add_student_rank);
        parent_fname=(EditText)getActivity().findViewById(R.id.add_student_parent_firstname);
        parent_lname=(EditText)getActivity().findViewById(R.id.add_student_parent_lastname);



        getActivity().findViewById(R.id.img_add_stu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching values from the student add form

                Log.d("testmessageonclick","clicked");
                Log.d("firstnameentered",stu_fname.getText().toString());
                //validating for the null value
                if(stu_fname.getText().toString().trim().isEmpty()||
                        stu_lname.getText().toString().trim().isEmpty()||
                        stu_class.getText().toString().trim().isEmpty()||
                        stu_rank.getText().toString().trim().isEmpty()||
                        parent_fname.getText().toString().trim().isEmpty()||
                        parent_lname.getText().toString().trim().isEmpty()){

                    Toast.makeText(getActivity(), "Please enter all the fields to register", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("test", "test");
                    //Creating an object corresponding to values fetched
                    Student student = new Student();
                    Parent parent = new Parent();
                    student.setFname(stu_fname.getText().toString());
                    student.setLname(stu_lname.getText().toString());
                    student.setStuClass(stu_class.getText().toString());
                    student.setRank(stu_rank.getText().toString());
                    student.setSchool("Current School");//School needs to be set when the auth is done
                    parent.setParent_fname(parent_fname.getText().toString());
                    parent.setParent_lname(parent_lname.getText().toString());
                    Log.d("test", "test");

                    //calling the interface to add the student values in the database
                    mListener.onFragmentInteraction(student, parent);
                    getActivity().getFragmentManager().beginTransaction().remove(StudentAddFragment.this).commit();
                }

            }
        });
    }

    public interface OnFragmentInteractionListener {
        //declaration is there in the main activity
        void onFragmentInteraction(Student student, Parent parent);

    }
}
