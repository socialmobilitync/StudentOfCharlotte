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
 * Created by LibraryGuest2 on 8/5/2016.
 */
public class SchoolNotificationAdapter extends ArrayAdapter {

    Context mContext;
    int mResource;
    List<String> mList;

    public SchoolNotificationAdapter(Context context, int resource, List objects) {
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

        TextView notification_string=(TextView)convertView.findViewById(R.id.txt_notification_string);

        notification_string.setText(mList.get(position));


        return convertView;

    }
}
