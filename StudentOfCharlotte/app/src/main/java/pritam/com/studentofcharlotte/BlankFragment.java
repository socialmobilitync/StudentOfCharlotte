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

import pritam.com.studentofcharlotte.siddharth.com.studentofcharlotte.Achievement;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class BlankFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Achievements> achievementsList;
    private ListView listView;
    private HomeViewListAdapater adapater;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) getView().findViewById(R.id.homeListView);
        adapater = new HomeViewListAdapater(getView().getContext(), R.layout.home_tab_notifications, new ArrayList<Achievements>());
        adapater.setNotifyOnChange(true);
        listView.setAdapter(adapater);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PostsWithCommentsFragment(),"POST_COMMENTS").commit();
                mListener.setCurrentAchievements((Achievements) adapater.getItem(position));
                Log.d("Listener","position "+ position);
            }
        });


        databaseReference.child("students").child(LoginActivity.uId).child("interest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> studentInterests = new ArrayList<String>();
                for (DataSnapshot sInterest : dataSnapshot.getChildren())
                {
                    studentInterests.add(sInterest.child("iName").getValue().toString());
                }
                mListener.setMyInterests(studentInterests);

                databaseReference.child("posts").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String interest = dataSnapshot.child("interest").getValue().toString();
                            if (studentInterests.contains(interest))
                            {
                                String key = dataSnapshot.getKey();
                                listenerForSingleValue(interest,key);
                            }

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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*if (mListener.getAchievmentsIfAny() != null)
        {
            onListViewItemClick();
        } else
        {
            databaseReference.child("students").child(LoginActivity.uId).child("interest").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    achievementsList = new ArrayList<Achievements>();
                    for (DataSnapshot studentInterests : dataSnapshot.getChildren())
                    {
                        final String interest = studentInterests.child("iName").getValue().toString();
                        Log.d("INTREST IS ",interest);
                        databaseReference.child("interests").child(interest).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                    List<StudentsInInterest> studentsInInterestList = new ArrayList<StudentsInInterest>();
                                    for (DataSnapshot students : dataSnapshot.child("Students").getChildren()) {
                                        StudentsInInterest studentsInInterest = students.getValue(StudentsInInterest.class);
                                        studentsInInterestList.add(studentsInInterest);
                                    }
                                    List<Achievements> iAchievementsList = new ArrayList<Achievements>();
                                    for (DataSnapshot achievements : dataSnapshot.child("iAchievements").getChildren()) {
                                        Log.d("Student Name", achievements.child("aStudent").getValue().toString());
                                        List<Comments> commentsList = new ArrayList<Comments>();
                                        String parentId = achievements.getKey();
                                        for (DataSnapshot comments : achievements.child("comments").getChildren()){
                                            Comments comment = new Comments(parentId, comments.getKey(),comments.child("commentText").getValue().toString(),
                                                    comments.child("student").getValue().toString(),
                                                    Integer.parseInt(comments.child("noOflikes").getValue().toString()));
                                            commentsList.add(comment);
                                        }
                                        Achievements achievements1 = new Achievements(achievements.child("aStudent").getValue().toString(), achievements.child("achievement").getValue().toString(), new ArrayList<String>(), new ArrayList<String>(), commentsList,interest, achievements.child("imageUrl").getValue().toString());
                                        iAchievementsList.add(achievements1);
                                    }
                                    List<Questions> questionsList = new ArrayList<Questions>();
                                    for (DataSnapshot questions : dataSnapshot.child("iQuestions").getChildren()) {
                                        Log.d("Student Name", questions.child("qStudent").getValue().toString());
                                        Questions question = new Questions();
                                        question.setInterest(interest);
                                        question.setQuestionKey(questions.getKey());
                                        question.setQuestionText(questions.child("qText").getValue().toString());
                                        question.setQuestionByStudent(questions.child("qStudent").getValue().toString());
                                        List<Answers> answerseList = new ArrayList<Answers>();
                                        for (DataSnapshot answerSanpshot : questions.child("qAnswers").getChildren())
                                        {
                                            Answers answers = new Answers();
                                            answers.setAnswerText(answerSanpshot.child("answer1").getValue().toString());
                                            answers.setAnswerByStudent(answerSanpshot.child("aStudent").getValue().toString());
                                            answerseList.add(answers);
                                        }
                                        question.setAnswersList(answerseList);
                                        questionsList.add(question);
                                    }
                                    achievementsList.addAll(iAchievementsList);
                                    mListener.onFragmentInteraction(questionsList, achievementsList);
                                    Interests interests = new Interests();
                                    interests.setiName(interest);
                                    interests.setAchievementsList(iAchievementsList);
                                    interests.setInterestedStudents(studentsInInterestList);
                                    interests.setQuestionsList(questionsList);

                                    onListViewItemClick();

                                }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("Error","Transaction Cancelled!!");
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }*/

    }

    private void listenerForSingleValue(final String interest, String key) {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d("REFERENCE IS -" ,databaseReference.child("interests").child(interest).child("iAchievements").child(key).toString());
        databaseReference.child("interests").child(interest).child("iAchievements").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot innerDataSnapshot) {
                Log.d("SNAPSHOT IS - ",innerDataSnapshot.toString());
                final Achievements achievement = innerDataSnapshot.getValue(Achievements.class);
                databaseReference.child("interests").child(interest).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        achievement.setGroupIcon(dataSnapshot.child("icon").getValue().toString());
                        Log.d("Achievement Data is - ",achievement.getAchievementByStudent());
                        adapater.add(achievement);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void onListViewItemClick(){
        achievementsList = mListener.getAchievmentsIfAny();

        HomeViewListAdapater adapater = new HomeViewListAdapater(getView().getContext(), R.layout.home_tab_notifications, achievementsList);
        adapater.setNotifyOnChange(true);
        listView.setAdapter(adapater);
        Log.d("ListView","Before listview listner");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PostsWithCommentsFragment(),"POST_COMMENTS").commit();
                mListener.setCurrentAchievements(achievementsList.get(position));
                Log.d("Listener","position "+ position);
            }
        });
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PostsWithCommentsFragment(),"POST_COMMENTS").commit();
            mListener.setCurrentAchievements(achievementsList.get(position));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(List<Questions> questionsList, List<Achievements> achievementsList);
        void setCurrentAchievements(Achievements achievements);
        List<Achievements> getAchievmentsIfAny();
        void setMyInterests(List<String> interests);
    }
}
