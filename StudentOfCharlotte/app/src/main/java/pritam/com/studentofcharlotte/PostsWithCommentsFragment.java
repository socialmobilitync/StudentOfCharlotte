package pritam.com.studentofcharlotte;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostsWithCommentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostsWithCommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsWithCommentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PostsWithCommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsWithCommentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsWithCommentsFragment newInstance(String param1, String param2) {
        PostsWithCommentsFragment fragment = new PostsWithCommentsFragment();
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
        return inflater.inflate(R.layout.fragment_posts_with_comments, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Achievements achievement = mListener.getPostDetails();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final EditText newComment = (EditText) getView().findViewById(R.id.newCommentForAchievementTxt);

        Button postButton = (Button) getView().findViewById(R.id.postCommentBtn);



        if (achievement != null){
            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newComentText  = newComment.getText().toString().trim();
                    if (newComentText.length() > 0)
                    {
                        Comments comment = new Comments();
                        comment.setNoOfLikes(0);
                        comment.setCommentText(newComentText);
                        comment.setCommentByStudent(LoginActivity.studentName);
                        databaseReference.child("interests").child(achievement.getInterest()).child("iAchievements")
                                .child(achievement.getKey()).child("comments").push().setValue(comment);

                        newComment.setText("");
                        newComment.clearFocus();

                    }
                }
            });
            TextView intrest = (TextView) getView().findViewById(R.id.studentAchievementLbl);
            TextView studentName = (TextView) getView().findViewById(R.id.studentNameForAchievementLbl);
            ImageView achievementImage = (ImageView) getView().findViewById(R.id.achievementImageView);
            ImageView iGrpIcon = (ImageView) getView().findViewById(R.id.intrestPicView);
            Picasso.with(getView().getContext()).load(achievement.getPostUrl())
                    .placeholder(R.drawable.bkg_imageview).into(achievementImage);
            Picasso.with(getView().getContext()).load(achievement.getGroupIcon())
                    .into(iGrpIcon);
            intrest.setText(achievement.getAchievementText());
            studentName.setText(achievement.getAchievementByStudent());
            ListView listView = (ListView) getView().findViewById(R.id.commentForAchievementListView);
            final CommentListAdapter commentListAdapter = new CommentListAdapter(getActivity(),R.layout.answer_layout,new ArrayList<Comments>());
            commentListAdapter.setNotifyOnChange(true);
            listView.setAdapter(commentListAdapter);


            databaseReference.child("interests").child(achievement.getInterest()).child("iAchievements")
                    .child(achievement.getKey()).child("comments").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Comments comments = dataSnapshot.getValue(Comments.class);
                    commentListAdapter.add(comments);
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
        Achievements getPostDetails();
    }
}
