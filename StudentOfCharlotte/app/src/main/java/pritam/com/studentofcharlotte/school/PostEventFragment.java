package pritam.com.studentofcharlotte.school;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pritam.com.studentofcharlotte.R;

public class PostEventFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    CalendarView date_picker;
    TimePicker timePicker;
    EditText venue;
    String selectedGroup;

    public PostEventFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.school_fragment_post_event, container, false);
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("test","tes");
        date_picker=(CalendarView) getActivity().findViewById(R.id.cal_post_event_date);
        timePicker=(TimePicker)getActivity().findViewById(R.id.time_picker_event_post);
        venue=(EditText)getActivity().findViewById(R.id.txt_post_event_venue);
        Button btn_done_posting_further_details=(Button) getActivity().findViewById(R.id.btn_post_further_event_details);

        btn_done_posting_further_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference();

                final String postingDate=date_picker.getDate()+"";
                //String postingTime=timePicker.getHour()+":"+timePicker.getMinute(); TimePicker is deprecated. Use Something else

                //Hardcoding Values for the Interest groups
                final CharSequence[] interestGroups=getResources().getStringArray(R.array.intrestList);

            //Alertbox to choose the interest group in which the events are posted
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select the Interest Group");
                    builder.setItems(interestGroups, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("selected",interestGroups[which].toString());
                            selectedGroup=interestGroups[which].toString();
                            mListener.onFragmentInteraction(postingDate,selectedGroup,venue.getText().toString());
                            //getActivity().getFragmentManager().beginTransaction().remove(PostEventFragment.this).commit();
                        }
                    }).show();


            }
        });


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String postingDate, String selectedGroup, String venue);
    }
}
