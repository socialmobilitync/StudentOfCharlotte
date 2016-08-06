package pritam.com.studentofcharlotte;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pritam on 7/16/16.
 */
public class HomeViewListAdapater extends ArrayAdapter {
    Context context;
    int resource;
    List<Achievements> objects;


    public HomeViewListAdapater(Context context, int resource, List<Achievements> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(resource,parent,false);
        }
        Achievements achievements = objects.get(position);
        TextView studentNameTxt = (TextView) convertView.findViewById(R.id.studentName);
        TextView studentInterest = (TextView) convertView.findViewById(R.id.studentInterest);
        ImageView postImage = (ImageView) convertView.findViewById(R.id.homePostImageView);
        ImageView grpIcon = (ImageView) convertView.findViewById(R.id.groupIcon);
        studentNameTxt.setText(achievements.getAchievementByStudent());
        studentInterest.setText(achievements.getAchievementText());
        Picasso.with(getContext()).load(achievements.getPostUrl()).placeholder(R.drawable.profpic).into(postImage);
        Picasso.with(getContext()).load(achievements.getGroupIcon()).placeholder(R.drawable.book).into(grpIcon);
        /*convertView.setBackgroundColor(Color.rgb(50,164,244));
        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.rgb(155,207,244));
*/
        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
