package pritam.com.studentofcharlotte.school;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pritam.com.studentofcharlotte.LoginActivity;
import pritam.com.studentofcharlotte.R;

public class MainActivity extends AppCompatActivity implements PostEventFragment.OnFragmentInteractionListener {

    EditText text_post_event;
    Button btn_post_event;
    TextView txt_event;
    ListView list_events;
    SchoolAdapter adapter;
    Event event;
    ArrayList<Event> eventsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_activity_main);

        text_post_event = (EditText) findViewById(R.id.edt_post_status);
        btn_post_event = (Button) findViewById(R.id.btn_post_event);
        list_events = (ListView) findViewById(R.id.list_events);
        eventsList = new ArrayList<Event>();

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        adapter = new SchoolAdapter(MainActivity.this, R.layout.school_events_list, eventsList);
        adapter.setNotifyOnChange(true);
        list_events.setAdapter(adapter);

        dbRef.child("schools").child(LoginActivity.uId).child("events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = new Event();
                event.setName(dataSnapshot.child("name").getValue().toString());
                event.setDate(dataSnapshot.child("date").getValue().toString());
                //event.setTime(dataSnapshot.child("time").getValue().toString());
                event.setVenue(dataSnapshot.child("venue").getValue().toString());
                adapter.add(event);
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



        btn_post_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testmain", "tes");
                LinearLayout postDetailsLayout = (LinearLayout) findViewById(R.id.postDetailsLayout);
                postDetailsLayout.setVisibility(View.GONE);
                findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().add(R.id.fragmentContainer, new PostEventFragment(), "postEvent").commit();
            }
        });
    }

    @Override
    public void onFragmentInteraction(String postingDate, String selectedGroup,String venue) {
        event = new Event();
        event.setName(text_post_event.getText().toString());
        event.setiGroup(selectedGroup);
        event.setDate(postingDate);
        event.setVenue(venue);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        String key = dbRef.child("schools").child(LoginActivity.uId).child("events").push().getKey();
        dbRef.child("schools").child(LoginActivity.uId).child("events").child(key).setValue(event);
        Toast.makeText(MainActivity.this, "Event Posted", Toast.LENGTH_SHORT).show();
        findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
        findViewById(R.id.postDetailsLayout).setVisibility(View.VISIBLE);
        //getFragmentManager().beginTransaction().remove().commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("menuSelected",item.getItemId()+"");
        switch (item.getItemId()) {
            case R.id.schoolProfileMenu:
                // write your code here
                Toast.makeText(MainActivity.this, "Profile Page", Toast.LENGTH_LONG);
                Intent intent = new Intent(MainActivity.this, SchoolProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.notificationMenu:
                Intent intentNotification=new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intentNotification);
                break;
            case R.id.signOutMenu:
                // code to logout the School
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }




}


