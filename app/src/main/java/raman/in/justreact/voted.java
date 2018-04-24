package raman.in.justreact;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class voted extends AppCompatActivity {


    private TextView recorded;
    private ImageView tick,kiss,lit,poop,smart;
    private Animation in,out,in1;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private String usernameget;
    private DatabaseReference mDatabase;
    private int kisscount=0,poopcount=0,litcount=0,smartcount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voted);

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(700);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(900);
        in1 = new AlphaAnimation(0.0f, 1.0f);
        in1.setDuration(1520);

        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        recorded = (TextView) findViewById(R.id.recorded);
        tick = (ImageView) findViewById(R.id.tick);
        kiss = (ImageView) findViewById(R.id.kiss);
        poop = (ImageView) findViewById(R.id.poop);
        smart = (ImageView) findViewById(R.id.smart);
        lit = (ImageView) findViewById(R.id.lit);

        Bundle b=getIntent().getExtras();
        usernameget=b.getString("username");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(usernameget).child("emoji");

        if(prefs.getString(usernameget+"done","no").equals("yes"))
            initialize();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kisscount = dataSnapshot.child("kiss").getValue(Integer.class);
                poopcount = dataSnapshot.child("poop").getValue(Integer.class);
                smartcount = dataSnapshot.child("smart").getValue(Integer.class);
                litcount = dataSnapshot.child("lit").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kiss.setVisibility(View.INVISIBLE);
                poop.setVisibility(View.INVISIBLE);
                smart.setVisibility(View.INVISIBLE);
                lit.setVisibility(View.INVISIBLE);

                kiss.startAnimation(out);
                tick.setVisibility(View.VISIBLE);
                tick.startAnimation(in1);
                recorded.setText("Your answers have been recorded");
                recorded.startAnimation(in1);

                kisscount=kisscount+1;
                mDatabase.child("kiss").setValue(kisscount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        editor.putString(usernameget+"done","yes");
                        editor.commit();

                    }
                });


            }
        });

        poop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kiss.setVisibility(View.INVISIBLE);
                poop.setVisibility(View.INVISIBLE);
                smart.setVisibility(View.INVISIBLE);
                lit.setVisibility(View.INVISIBLE);

                poop.startAnimation(out);
                tick.setVisibility(View.VISIBLE);
                tick.startAnimation(in1);
                recorded.setText("Your answers have been recorded");
                recorded.startAnimation(in1);

                poopcount=poopcount+1;
                mDatabase.child("poop").setValue(poopcount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        editor.putString(usernameget+"done","yes");
                        editor.commit();

                    }
                });
            }
        });


        lit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kiss.setVisibility(View.INVISIBLE);
                poop.setVisibility(View.INVISIBLE);
                smart.setVisibility(View.INVISIBLE);
                lit.setVisibility(View.INVISIBLE);

                lit.startAnimation(out);
                tick.setVisibility(View.VISIBLE);
                tick.startAnimation(in1);
                recorded.setText("Your answers have been recorded");
                recorded.startAnimation(in1);

                litcount=litcount+1;
                mDatabase.child("lit").setValue(litcount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        editor.putString(usernameget+"done","yes");
                        editor.commit();

                    }
                });

            }
        });


        smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kiss.setVisibility(View.INVISIBLE);
                poop.setVisibility(View.INVISIBLE);
                smart.setVisibility(View.INVISIBLE);
                lit.setVisibility(View.INVISIBLE);

                smart.startAnimation(out);
                tick.setVisibility(View.VISIBLE);
                tick.startAnimation(in1);
                recorded.setText("Your answers have been recorded");
                recorded.startAnimation(in1);

                smartcount=smartcount+1;
                mDatabase.child("smart").setValue(smartcount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        editor.putString(usernameget+"done","yes");
                        editor.commit();

                    }
                });

            }
        });



    }

    public void initialize(){

        kiss.setVisibility(View.INVISIBLE);
        poop.setVisibility(View.INVISIBLE);
        smart.setVisibility(View.INVISIBLE);
        lit.setVisibility(View.INVISIBLE);
        recorded.setText("Your answers have been recorded");
        tick.setVisibility(View.VISIBLE);

    }


    @Override public void onBackPressed() { super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
