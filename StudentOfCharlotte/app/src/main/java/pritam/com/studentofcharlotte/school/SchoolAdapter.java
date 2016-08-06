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
 * Created by LibraryGuest2 on 8/4/2016.
 */
public class SchoolAdapter extends ArrayAdapter {

    Context mContext;
    int mResource;
    List<Event> mList;

    public SchoolAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.mList=objects;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }

        TextView event_name=(TextView)convertView.findViewById(R.id.text_event_name);
        TextView event_venue=(TextView)convertView.findViewById(R.id.text_event_venue);
        TextView event_date=(TextView)convertView.findViewById(R.id.text_event_date);
        TextView event_time=(TextView)convertView.findViewById(R.id.text_event_time);

        event_name.setText(mList.get(position).getName());
        event_venue.setText(mList.get(position).getVenue());
        event_date.setText(mList.get(position).getDate());
        event_time.setText(mList.get(position).getTime());


    return convertView;

    }
}
