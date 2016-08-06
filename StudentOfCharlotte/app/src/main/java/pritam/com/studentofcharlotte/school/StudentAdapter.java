package pritam.com.studentofcharlotte.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pritam.com.studentofcharlotte.R;

/**
 * Created by Vishal on 7/19/2016.
 */
public class StudentAdapter extends ArrayAdapter {

    Context mContext;
    int mResource;
    List<String> interests;

    public StudentAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.interests=objects;
    }

    @Override
    public int getCount() {
        return interests.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }

        TextView stuInt=(TextView)convertView.findViewById(R.id.adapter_interest);

        stuInt.setText(interests.get(position));
        return convertView;
    }
}
