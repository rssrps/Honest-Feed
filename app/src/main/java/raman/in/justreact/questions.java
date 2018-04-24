package raman.in.justreact;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.data;
import static android.R.attr.duration;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class questions extends AppCompatActivity {

    private Button optiona,optionb,optionc,optiond;
    private Animation in,out;
    private String question[],options[][];
    private AnimationSet A;
    private Bundle b;
    private DatabaseReference mDatabase;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private TextView questionnumber,questiondisplay,name;
    private int count=1;//The current question we are on
    private String searchedItem; // username of person whom we are answering to

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questionnumber = (TextView) findViewById(R.id.questionnumber);


        b=getIntent().getExtras();
        searchedItem = b.getString("searched");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(searchedItem).child("questions");
        name = (TextView) findViewById(R.id.name);
        name.setText(searchedItem);

        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        optiona = (Button) findViewById(R.id.optiona);
        optionb = (Button) findViewById(R.id.optionb);
        optionc = (Button) findViewById(R.id.optionc);
        optiond = (Button) findViewById(R.id.optiond);

        questiondisplay = (TextView) findViewById(R.id.question);

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

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1200);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);

            count=Integer.parseInt(prefs.getString(searchedItem,"0"));
            count++;
            if(count>=10){
                Intent i1=new Intent(questions.this,voted.class);
                i1.putExtra("username",searchedItem);
                i1.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(i1,bundle);
                finish();
            }

            else
             startFromThisQuestion();


        optiona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                optiona.startAnimation(out);

                count=(count+1)%11;

                if(count==0){

                    mDatabase.child("10").child("a").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int xyz=dataSnapshot.getValue(Integer.class);
                            xyz++;
                            mDatabase.child("10").child("a").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    editor.putString(searchedItem,"10");
                                    editor.commit();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Intent i1=new Intent(questions.this, voted.class);
                    i1.putExtra("username",searchedItem);
                    i1.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(i1,bundle);
                    finish();

                }

                else {

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            optiona.setText(options[count][1]);
                            optiona.startAnimation(in);
                            optionb.setText(options[count][2]);
                            optionb.startAnimation(in);
                            optionc.setText(options[count][3]);
                            optionc.startAnimation(in);
                            optiond.setText(options[count][4]);
                            optiond.startAnimation(in);

                            questiondisplay.setText(question[count]);
                            questiondisplay.startAnimation(in);

                            questionnumber.setText(String.valueOf(count)+"/10");
                            questionnumber.startAnimation(in);


                            mDatabase.child(String.valueOf(count-1)).child("a").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    int xyz=dataSnapshot.getValue(Integer.class);
                                    xyz++;
                                    mDatabase.child(String.valueOf(count-1)).child("a").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            editor.putString(searchedItem,String.valueOf(count-1));
                                            editor.commit();

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }, 210);

                }

            }
        });




        optionb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                optionb.startAnimation(out);

                count=(count+1)%11;

                if(count==0){

                    mDatabase.child("10").child("b").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int xyz=dataSnapshot.getValue(Integer.class);
                            xyz++;
                            mDatabase.child("10").child("b").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    editor.putString(searchedItem,"10");
                                    editor.commit();

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Intent i1=new Intent(questions.this, voted.class);
                    i1.putExtra("username",searchedItem);
                    i1.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(i1,bundle);
                    finish();

                }

                else {

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            optiona.setText(options[count][1]);
                            optiona.startAnimation(in);
                            optionb.setText(options[count][2]);
                            optionb.startAnimation(in);
                            optionc.setText(options[count][3]);
                            optionc.startAnimation(in);
                            optiond.setText(options[count][4]);
                            optiond.startAnimation(in);

                            questiondisplay.setText(question[count]);
                            questiondisplay.startAnimation(in);

                            questionnumber.setText(String.valueOf(count)+"/10");
                            questionnumber.startAnimation(in);


                            mDatabase.child(String.valueOf(count-1)).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    int xyz=dataSnapshot.getValue(Integer.class);
                                    xyz++;
                                    mDatabase.child(String.valueOf(count-1)).child("b").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            editor.putString(searchedItem,String.valueOf(count-1));
                                            editor.commit();

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }, 210);

                }

            }
        });


        optionc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                optionc.startAnimation(out);

                count=(count+1)%11;

                if(count==0){

                    mDatabase.child("10").child("c").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int xyz=dataSnapshot.getValue(Integer.class);
                            xyz++;
                            mDatabase.child("10").child("c").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    editor.putString(searchedItem,"10");
                                    editor.commit();

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Intent i1=new Intent(questions.this, voted.class);
                    i1.putExtra("username",searchedItem);
                    i1.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(i1,bundle);
                    finish();

                }

                else {

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            optiona.setText(options[count][1]);
                            optiona.startAnimation(in);
                            optionb.setText(options[count][2]);
                            optionb.startAnimation(in);
                            optionc.setText(options[count][3]);
                            optionc.startAnimation(in);
                            optiond.setText(options[count][4]);
                            optiond.startAnimation(in);

                            questiondisplay.setText(question[count]);
                            questiondisplay.startAnimation(in);

                            questionnumber.setText(String.valueOf(count)+"/10");
                            questionnumber.startAnimation(in);


                            mDatabase.child(String.valueOf(count-1)).child("c").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    int xyz=dataSnapshot.getValue(Integer.class);
                                    xyz++;
                                    mDatabase.child(String.valueOf(count-1)).child("c").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            editor.putString(searchedItem,String.valueOf(count-1));
                                            editor.commit();

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }, 210);

                }

            }
        });




        optiond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                optiond.startAnimation(out);

                count=(count+1)%11;

                if(count==0){

                    mDatabase.child("10").child("d").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int xyz=dataSnapshot.getValue(Integer.class);
                            xyz++;
                            mDatabase.child("10").child("d").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    editor.putString(searchedItem,"10");
                                    editor.commit();

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Intent i1=new Intent(questions.this, voted.class);
                    i1.putExtra("username",searchedItem);
                    i1.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(i1,bundle);
                    finish();

                }

                else {

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            optiona.setText(options[count][1]);
                            optiona.startAnimation(in);
                            optionb.setText(options[count][2]);
                            optionb.startAnimation(in);
                            optionc.setText(options[count][3]);
                            optionc.startAnimation(in);
                            optiond.setText(options[count][4]);
                            optiond.startAnimation(in);

                            questiondisplay.setText(question[count]);
                            questiondisplay.startAnimation(in);

                            questionnumber.setText(String.valueOf(count)+"/10");
                            questionnumber.startAnimation(in);


                            mDatabase.child(String.valueOf(count-1)).child("d").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    int xyz=dataSnapshot.getValue(Integer.class);
                                    xyz++;
                                    mDatabase.child(String.valueOf(count-1)).child("d").setValue(xyz).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            editor.putString(searchedItem,String.valueOf(count-1));
                                            editor.commit();

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }, 210);

                }

            }
        });


    }

    private void startFromThisQuestion() {

        optiona.setText(options[count][1]);
        optiona.startAnimation(in);
        optionb.setText(options[count][2]);
        optionb.startAnimation(in);
        optionc.setText(options[count][3]);
        optionc.startAnimation(in);
        optiond.setText(options[count][4]);
        optiond.startAnimation(in);

        questiondisplay.setText(question[count]);
        questiondisplay.startAnimation(in);

        questionnumber.setText(String.valueOf(count)+"/10");
        questionnumber.startAnimation(in);

    }

    @Override public void onBackPressed() { super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); }


}
