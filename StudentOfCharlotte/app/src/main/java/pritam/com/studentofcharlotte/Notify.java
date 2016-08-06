package pritam.com.studentofcharlotte;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notify extends Fragment {


    private DatabaseReference mDatabase;
    static String notifDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("create","fragment created");
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //Connect to firebase and fetch the data into a list
    // Populating the list view in the fragment

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("students").child(LoginActivity.uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Notification> test_not = new ArrayList<Notification>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("notifications").getChildren()){
                    notifDesc =dataSnapshot1.child("notifDesc").getValue().toString();
                    Notification notification=new Notification();
                    Log.d("notifDesc",notifDesc);

                    Notification obj=new Notification();
                    obj.setNotifDesc(notifDesc);
                    test_not.add(obj);
                }
                if (test_not.size()==0)
                {
                    Notification obj=new Notification();
                    obj.setNotifDesc("There are no Notification for you!!");
                    test_not.add(obj);
                }
                ListView listView=(ListView)getView().findViewById(R.id.listView);
                NotifyAdapter adapter=new NotifyAdapter(getActivity(),R.layout.list_item,test_not);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("create","On activity created");

    }
}
