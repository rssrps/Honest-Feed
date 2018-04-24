package raman.in.justreact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class userhome extends AppCompatActivity {

    private Bundle b;
    private String username, packagename = "raman.in.justreact";
    private TextView usernamedisplay, viewcount;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase, pkgname;
    private ImageView logout, share, emoji;
    private Button viewanswers;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private Animation in, out;
    private int kisscount = 0, poopcount = 0, litcount = 1, smartcount = 0, array[];
    private String max = "lit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

        b = getIntent().getExtras();
        pkgname = FirebaseDatabase.getInstance().getReference().child("package");
        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        username = b.getString("username");
        viewanswers = (Button) findViewById(R.id.viewanswers);
        usernamedisplay = (TextView) findViewById(R.id.username1);
        usernamedisplay.setText("Honest Feed : " + username);
        viewcount = (TextView) findViewById(R.id.viewcount);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        logout = (ImageView) findViewById(R.id.logout);
        share = (ImageView) findViewById(R.id.share);
        emoji = (ImageView) findViewById(R.id.emoji);

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1200);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);

        array = new int[5];

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading");
        mProgress.setMessage("Please wait");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        pkgname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                packagename = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child(username).child("views").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                viewcount.setText(String.valueOf(dataSnapshot.getValue()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child(username).child("emoji").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kisscount = dataSnapshot.child("kiss").getValue(Integer.class);
                poopcount = dataSnapshot.child("poop").getValue(Integer.class);
                smartcount = dataSnapshot.child("smart").getValue(Integer.class);
                litcount = dataSnapshot.child("lit").getValue(Integer.class);

                array[0] = kisscount;
                array[1] = poopcount;
                array[2] = smartcount;
                array[3] = litcount;

                findMax();

                if(max.equals("kiss"))
                    emoji.setImageResource(R.drawable.kiss);
                if(max.equals("poop"))
                    emoji.setImageResource(R.drawable.poop);
                if(max.equals("smart"))
                    emoji.setImageResource(R.drawable.smart);
                if(max.equals("lit"))
                    emoji.setImageResource(R.drawable.lit);

                mProgress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                share.startAnimation(in);

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String sAux = "Honest Feed: The only anonymous app with positive vibes\n\nFollow the new trend! Anonymously answer what you think about your friends and know what they think about you.\n\nSearch me by my username: "+username+"\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + packagename + " \n";

                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        usernamedisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                usernamedisplay.startAnimation(in);
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");

                    String sAux ="Honest Feed"+"\n\nThe only anonymous app with positive vibes\n\nFollow the new trend! Anonymously answer what you think about your friends and know what they think about you.\n\nSearch me by my username: "+username+"\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + packagename + "\n";

                    i.putExtra(Intent.EXTRA_TEXT,sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }

            }
        });

        viewanswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(userhome.this, answers.class);
                i1.putExtra("username", username);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(i1, bundle);

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("username", "");
                editor.commit();
                Intent i = new Intent(userhome.this, raman.in.justreact.MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(i, bundle);
                finish();

            }
        });

    }

    private void findMax() {

        int x = 3;
        for (int i = 0; i < 4; i++) {
            if (array[i] > array[x])
                x = i;

            if (x == 0)
                max = "kiss";
            if (x == 1)
                max = "poop";
            if (x == 2)
                max = "smart";
            if (x == 3)
                max = "lit";
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
