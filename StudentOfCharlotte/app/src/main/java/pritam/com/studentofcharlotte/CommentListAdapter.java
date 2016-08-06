package pritam.com.studentofcharlotte;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Pritam on 7/26/16.
 */
public class CommentListAdapter extends ArrayAdapter {

    List<Comments> objects;
    Context context;
    int resource;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public CommentListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(resource,parent,false);
        }
        final Comments comment = objects.get(position);
        TextView answerBy = (TextView) convertView.findViewById(R.id.answerByStudent);
        TextView answerTxt = (TextView) convertView.findViewById(R.id.currentAnswerTxt);
        final ImageButton answerLikeButton = (ImageButton) convertView.findViewById(R.id.answerLikeButton);
        answerLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bkgImg = answerLikeButton.getBackground();
                Drawable unLike = v.getResources().getDrawable(android.R.drawable.btn_star_big_off);
                if (bkgImg.equals(unLike)){
                    databaseReference.child("interests").child("Study").child("iAchievements")
                            .child(comment.getParentId()).child("comments").child(comment.getCommentId()).child("noOflikes").setValue(comment.getNoOfLikes()+1);
                    comment.setNoOfLikes(comment.getNoOfLikes()+1);
                    answerLikeButton.setBackgroundResource(android.R.drawable.btn_star_big_on);

                } else
                {
                    databaseReference.child("interests").child("Study").child("iAchievements")
                            .child(comment.getParentId()).child("comments").child(comment.getCommentId()).child("noOflikes").setValue(comment.getNoOfLikes()-1);
                    comment.setNoOfLikes(comment.getNoOfLikes()-1);
                    answerLikeButton.setBackgroundResource(android.R.drawable.btn_star_big_on);

                }

                }
        });

        answerBy.setText(comment.getCommentByStudent());
        answerTxt.setText(comment.getCommentText());

        return convertView;
    }
}
