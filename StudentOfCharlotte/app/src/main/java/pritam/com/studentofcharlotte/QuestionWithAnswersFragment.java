package pritam.com.studentofcharlotte;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pritam.com.studentofcharlotte.siddharth.com.studentofcharlotte.MyAnswer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionWithAnswersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionWithAnswersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionWithAnswersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestionWithAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Questions question = mListener.getCurrentQuestion();

        if(question != null)
        {
            List<Answers> answersList = new ArrayList<Answers>(); //question.getAnswersList();
            TextView currentQuestionTxt = (TextView) getView().findViewById(R.id.currentQuestionTxt);
            currentQuestionTxt.setText(question.getQuestionText());
            ListView listView = (ListView) getView().findViewById(R.id.answersListView);
            final AnswersListAdapter answersListAdapter = new AnswersListAdapter(getActivity(),R.layout.answer_layout,answersList);
            answersListAdapter.setNotifyOnChange(true);
            listView.setAdapter(answersListAdapter);

            //onClickListener for post button
            final EditText answerNew = (EditText) getView().findViewById(R.id.addAnswerTxt);
            Button postAnswer = (Button) getView().findViewById(R.id.provideAnswer);
            postAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!answerNew.getText().toString().trim().equals(""))
                    {
                        String key = databaseReference.child("interests").child(question.getInterest()).child("iQuestions")
                                .child(question.getQuestionKey()).child("qAnswers").push().getKey();
                        /*databaseReference.child("interests").child(question.getInterest()).child("iQuestions")
                                .child(question.getQuestionKey()).child("qAnswers").child(key).child("answer1").setValue(answerNew.getText().toString().trim());
                        databaseReference.child("interests").child(question.getInterest()).child("iQuestions")
                                .child(question.getQuestionKey()).child("qAnswers").child(key).child("aStudent").setValue("Siddharth");
*/
                        databaseReference.child("interests").child(question.getInterest()).child("iQuestions")
                                .child(question.getQuestionKey()).child("qAnswers").child(key).setValue(new MyAnswer(answerNew.getText().toString().trim(),"Raj"));
                        answerNew.setText("");
                        answerNew.clearFocus();

                    }
                }
            });


            databaseReference.child("interests").child(question.getInterest()).child("iQuestions")
                    .child(question.getQuestionKey()).child("qAnswers").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d("onChildAdded", "Key added is "+dataSnapshot.getKey());
                    Answers answers = new Answers();
                    answers.setAnswerText(dataSnapshot.child("answer1").getValue().toString());
                    answers.setAnswerByStudent(dataSnapshot.child("aStudent").getValue().toString());
                    answersListAdapter.add(answers);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionWithAnswersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionWithAnswersFragment newInstance(String param1, String param2) {
        QuestionWithAnswersFragment fragment = new QuestionWithAnswersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_with_answers, container, false);
    }

  /*  // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        Questions getCurrentQuestion();
    }
}
