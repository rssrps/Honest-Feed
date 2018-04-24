package raman.in.justreact;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class answers extends AppCompatActivity {

    private PieChart mchart;
    private DatabaseReference mDatabase;
    private Bundle bundle;
    private String username;
    private TextView questionnumber,questiondisplay;
    private int count=1,a,b,c,d;
    private float a1,b1,c1,d1;
    private Button next,prev;
    private Animation in,out,in1,in2;
    private String question[],options[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        bundle=getIntent().getExtras();
        username=bundle.getString("username");
        next = (Button) findViewById(R.id.next);
        prev = (Button) findViewById(R.id.previous);
        prev.setVisibility(View.INVISIBLE);
        questionnumber = (TextView) findViewById(R.id.questionnumber);
        questiondisplay = (TextView) findViewById(R.id.question);

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1100);
        in1 = new AlphaAnimation(0.0f, 1.0f);
        in1.setDuration(1400);
        in2 = new AlphaAnimation(0.0f, 1.0f);
        in2.setDuration(2400);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("questions");

        mchart = (PieChart) findViewById(R.id.piechart);
        mchart.setOpenClockwise(true);
        mchart.setLegendColor(Color.parseColor("#ffffff"));
        mchart.setAnimationTime(1300);

        displayStats();


        question = new String[15];

        question[1]="Why are you attracted to me?";
        question[2]="How would you ask me out if you ever wanted to?";
        question[3]="What do you want from me?";
        question[4]="How would you describe me in one word?";
        question[5]="What makes me different from others?";
        question[6]="Where would you like to go with me if I say yes?";
        question[7]="What kind of a person am I?";
        question[8]="What do you think is my best feature?";
        question[9]="One thing you would like to change about me:";
        question[10]="The first thing that comes to your mind when you think about me:";


        options=new String[15][5];
        options[1][1]="Hotness";
        options[1][2]="Intelligence";
        options[1][3]="Humour";
        options[1][4]="You are not attractive";

        options[2][1]="By sending a gift";
        options[2][2]="A heavy pick up line";
        options[2][3]="Writing a note";
        options[2][4]="I won't ask";

        options[3][1]="A Date";
        options[3][2]="Friendship";
        options[3][3]="Marry me";
        options[3][4]="A Night";

        options[4][1]="Sensitive";
        options[4][2]="Passionate";
        options[4][3]="Aggressive";
        options[4][4]="Moron";

        options[5][1]="Confidence";
        options[5][2]="Leadership";
        options[5][3]="Charm";
        options[5][4]="Nothing";

        options[6][1]="Dinner";
        options[6][2]="Pub";
        options[6][3]="Long Drive";
        options[6][4]="Not interested";

        options[7][1]="Pervert";
        options[7][2]="Fake";
        options[7][3]="Sarcastic";
        options[7][4]="Genius";

        options[8][1]="Smile";
        options[8][2]="Personality";
        options[8][3]="Physique";
        options[8][4]="No Feature";

        options[9][1]="Hairstyle";
        options[9][2]="Dress Sense";
        options[9][3]="Attitude";
        options[9][4]="Nothing";

        options[10][1]="A Potato";
        options[10][2]="A Millionaire";
        options[10][3]="A Cartoon";
        options[10][4]="Nothing";


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mchart.clearChart();

                count=count+1;
                if(count==1)
                    prev.setVisibility(View.INVISIBLE);
                else
                prev.setVisibility(View.VISIBLE);

                if(count==10)
                    next.setVisibility(View.INVISIBLE);
                else {
                    next.setVisibility(View.VISIBLE);
                    next.startAnimation(in1);
                }

                questiondisplay.setText(question[count]);
                questionnumber.setText(String.valueOf(count)+"/10");
                displayStats();
                questiondisplay.startAnimation(in2);
            }

        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mchart.clearChart();
                count =count-1;

                if(count==10)
                    next.setVisibility(View.INVISIBLE);
                else
                    next.setVisibility(View.VISIBLE);

                if(count==1)
                    prev.setVisibility(View.INVISIBLE);
                else {
                    prev.setVisibility(View.VISIBLE);
                    prev.startAnimation(in1);
                }

                questiondisplay.setText(question[count]);
                questionnumber.setText(String.valueOf(count)+"/10");
                displayStats();
                questiondisplay.startAnimation(in2);
            }
        });



        /*
        mchart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#f65314")));
        mchart.addPieSlice(new PieModel("abcd", 25, Color.parseColor("#00a1f1")));
        mchart.addPieSlice(new PieModel("tierttme", 55, Color.parseColor("#7cbb00")));
        mchart.addPieSlice(new PieModel("lplpd", 5, Color.parseColor("#ffbb00")));

        mchart.startAnimation();
        */

    }

    private void displayStats() {

        mDatabase.child(String.valueOf(count)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                a=dataSnapshot.child("a").getValue(Integer.class);
                b=dataSnapshot.child("b").getValue(Integer.class);
                c=dataSnapshot.child("c").getValue(Integer.class);
                d=dataSnapshot.child("d").getValue(Integer.class);

                int sum=a+b+c+d;

                if(sum==4){
                    mchart.addPieSlice(new PieModel(options[count][1], 25, Color.parseColor("#f65314")));
                    mchart.addPieSlice(new PieModel(options[count][2], 25, Color.parseColor("#00a1f1")));
                    mchart.addPieSlice(new PieModel(options[count][3], 25, Color.parseColor("#7cbb00")));
                    mchart.addPieSlice(new PieModel(options[count][4], 25, Color.parseColor("#ffbb00")));

                    mchart.startAnimation();
                    mchart.startAnimation(in);
                }

                else {
                    a--;
                    b--;
                    c--;
                    d--;

                    sum=sum-4;

                    a1 = 100 * a / sum;
                    b1 = b * 100 / sum;
                    c1 = 100 * c / sum;
                    d1 = d * 100 / sum ;

                    if(a1!=0)
                        mchart.addPieSlice(new PieModel(options[count][1], a1, Color.parseColor("#f65314")));
                    if(b1!=0)
                        mchart.addPieSlice(new PieModel(options[count][2], b1, Color.parseColor("#00a1f1")));
                    if(c1!=0)
                        mchart.addPieSlice(new PieModel(options[count][3], c1, Color.parseColor("#7cbb00")));
                    if(d1!=0)
                        mchart.addPieSlice(new PieModel(options[count][4], d1, Color.parseColor("#ffbb00")));

                    mchart.startAnimation();
                    mchart.startAnimation(in);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override public void onBackPressed() { super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
