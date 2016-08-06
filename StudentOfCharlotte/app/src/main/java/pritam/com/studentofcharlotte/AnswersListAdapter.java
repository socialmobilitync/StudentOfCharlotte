package pritam.com.studentofcharlotte;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pritam on 7/20/16.
 */
public class AnswersListAdapter extends ArrayAdapter {

    List<Answers> objects;
    Context context;
    int resource;


    public AnswersListAdapter(Context context, int resource, List objects) {
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
        Answers answer = objects.get(position);
        TextView answerBy = (TextView) convertView.findViewById(R.id.answerByStudent);
        TextView answerTxt = (TextView) convertView.findViewById(R.id.currentAnswerTxt);

        answerBy.setText(answer.getAnswerByStudent());
        answerTxt.setText(answer.getAnswerText());

        return convertView;
    }
}
