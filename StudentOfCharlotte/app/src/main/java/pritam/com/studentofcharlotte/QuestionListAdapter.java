package pritam.com.studentofcharlotte;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pritam on 7/18/16.
 */
public class QuestionListAdapter extends ArrayAdapter {

    Context context;
    int resource;
    List<Questions> objects;

    public QuestionListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(resource,parent,false);
        }
        Questions question = objects.get(position);
        TextView questionTxt = (TextView) convertView.findViewById(R.id.questionText);
        TextView recentAnswer = (TextView) convertView.findViewById(R.id.recentAnswer);
        TextView answerBy = (TextView) convertView.findViewById(R.id.answerByTxt);
        ImageView interestImage = (ImageView) convertView.findViewById(R.id.interestImageView);

        questionTxt.setText(question.getQuestionText());
        if (question.getAnswersList().size()>0) {
            recentAnswer.setText(question.getAnswersList().get(0).getAnswerText());
            answerBy.setText(question.getAnswersList().get(0).getAnswerByStudent() + " : ");
        }
        else
        {
            recentAnswer.setText("No Answer Available");
            answerBy.setText("No Answer :");
        }
        interestImage.setImageResource(R.drawable.book);

        return convertView;
    }
}
