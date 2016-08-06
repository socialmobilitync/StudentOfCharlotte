package pritam.com.studentofcharlotte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LibraryGuest2 on 7/16/2016.
 */
public class NotifyAdapter extends ArrayAdapter<Notification>{


    List<Notification> mList;
    Context mContext;
    int mResource;
    public NotifyAdapter(Context context, int resource,List<Notification> objs) {
        super(context, resource);
        this.mList=objs;
        this.mContext=context;
        this.mResource=resource;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(mResource, parent, false);
        }
        TextView txtView=(TextView)convertView.findViewById(R.id.txt_notify);
        txtView.setText(mList.get(position).getNotifDesc());

        return convertView;


    }
}
