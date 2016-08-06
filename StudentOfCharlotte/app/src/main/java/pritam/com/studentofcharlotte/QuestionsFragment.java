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
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static QuestionListAdapter questionListAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getView().findViewById(R.id.questions_listview);
        questionListAdapter = new QuestionListAdapter(getActivity(),R.layout.questions_list,new ArrayList<Questions>());
        questionListAdapter.setNotifyOnChange(true);
        listView.setAdapter(questionListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new QuestionWithAnswersFragment(),"Q&A").commit();
                mListener.setQnA((Questions) questionListAdapter.getItem(position));
            }
        });

        for (String iName : MainActivity.studentInterestList){
            listenerForSingleValue(iName);
        }
        //final List<Questions> questionsList = mListener.getQuestionsIfAny();

        /*if (questionsList!= null)
        {
            ListView listView = (ListView) getView().findViewById(R.id.questions_listview);
            questionListAdapter = new QuestionListAdapter(getActivity(),R.layout.questions_list,questionsList);
            questionListAdapter.setNotifyOnChange(true);
            listView.setAdapter(questionListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new QuestionWithAnswersFragment(),"Q&A").commit();
                    mListener.setQnA(questionsList.get(position));
                }
            });

        }*/


    }


    private void listenerForSingleValue(final String interest) {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //Log.d("REFERENCE IS -" ,databaseReference.child("interests").child(interest).child("iAchievements").child(key).toString());
        databaseReference.child("interests").child(interest).child("iQuestions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("SNAPSHOT IS - ",dataSnapshot.toString());


                final Questions question = new Questions();
                question.setInterest(interest);
                question.setQuestionKey(dataSnapshot.getKey());
                question.setQuestionText(dataSnapshot.child("qText").getValue().toString());
                question.setQuestionByStudent(dataSnapshot.child("qStudent").getValue().toString());


                List<Answers> answersList = new ArrayList<Answers>();
                for (DataSnapshot answerSanpshot : dataSnapshot.child("qAnswers").getChildren())
                {
                    Answers answers = new Answers();
                    answers.setAnswerText(answerSanpshot.child("answer1").getValue().toString());
                    answers.setAnswerByStudent(answerSanpshot.child("aStudent").getValue().toString());
                    answersList.add(answers);
                }
                question.setAnswersList(answersList);
                questionListAdapter.add(question);
                //final Achievements achievement = dataSnapshot.getValue(Achievements.class);
                /*databaseReference.child("interests").child(interest).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        achievement.setGroupIcon(dataSnapshot.child("icon").getValue().toString());
                        Log.d("Achievement Data is - ",achievement.getAchievementByStudent());
                        questionListAdapter.add(achievement);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
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



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionsFragment newInstance(String param1, String param2) {
        QuestionsFragment fragment = new QuestionsFragment();
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
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }

   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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
        List<Questions> getQuestionsIfAny();
        void setQnA(Questions question);

    }
}
