package pritam.com.studentofcharlotte;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import pritam.com.studentofcharlotte.StorageUploads.NewPost;
import pritam.com.studentofcharlotte.siddharth.com.studentofcharlotte.SidMainActivity;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener, QuestionsFragment.OnFragmentInteractionListener, QuestionWithAnswersFragment.OnFragmentInteractionListener, PostsWithCommentsFragment.OnFragmentInteractionListener {
    private List<Questions> questionsList;
    private List<Achievements> achievementsList;
    private Questions currentQuestion;
    private Achievements currentAchievements;
    public static List<String> studentInterestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportFragmentManager()..beginTransaction().commit()
        final Button homeButton = (Button) findViewById(R.id.Home);
        final Button notifButton = (Button) findViewById(R.id.notification);
        final Button questionButton = (Button) findViewById(R.id.question);
        android.support.design.widget.FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,NewPost.class);
                    startActivity(intent);
                }
            });


        getFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new BlankFragment(),"HomeFragment").commit();

        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Notify(),"notify").commit();
                homeButton.setTextColor(Color.BLACK);
                notifButton.setTextColor(Color.rgb(0,153,204));
                questionButton.setTextColor(Color.BLACK);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new BlankFragment(),"home").commit();
                homeButton.setTextColor(Color.rgb(0,153,204));
                notifButton.setTextColor(Color.BLACK);
                questionButton.setTextColor(Color.BLACK);
            }
        });

        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new QuestionsFragment(),"question").commit();
                homeButton.setTextColor(Color.BLACK);
                notifButton.setTextColor(Color.BLACK);
                questionButton.setTextColor(Color.rgb(0,153,204));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_gift_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileMenu:
                Intent intent = new Intent(this,SidMainActivity.class);
                startActivity(intent);
                //finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(List<Questions> questionsList, List<Achievements> achievementsList) {
        this.questionsList = questionsList;
        this.achievementsList = achievementsList;
    }

    @Override
    public void setCurrentAchievements(Achievements achievements) {
        this.currentAchievements = achievements;
    }

    @Override
    public List<Achievements> getAchievmentsIfAny() {
        return achievementsList;
    }

    @Override
    public void setMyInterests(List<String> interests) {
        this.studentInterestList = interests;
    }

    @Override
    public List<Questions> getQuestionsIfAny() {
        return questionsList;
    }

    @Override
    public void setQnA(Questions question) {
        this.currentQuestion = question;
    }

    @Override
    public Questions getCurrentQuestion() {
        return currentQuestion;
    }

    @Override
    public Achievements getPostDetails() {
        return currentAchievements;
    }
}
