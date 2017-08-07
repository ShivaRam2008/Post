package com.example.shiva.post;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.shiva.post.R.layout.lists;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton post;


    SharedPreferences shared;
    TextView display;
    ArrayList<MessageDetails> msgDetails;
    FirebaseStorage mStorage;

    MessageDetails objectToAdd;
    ListView listOfPosts;

boolean checkForInternet;
    TextView wasd;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    ProgressDialog mProgress;
    String userValue;
    String s1, s2, s3,s4;
    String area, news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mProgress = new ProgressDialog(this);

        mProgress.setMessage("..Loading..");
        mProgress.show();

        listOfPosts = (ListView) findViewById(R.id.list);

        mDatabase = FirebaseDatabase.getInstance();
        shared=getSharedPreferences("fileName",Context.MODE_PRIVATE);

        SharedPreferences.Editor edio = shared.edit();
        // recyclerView = (RecyclerView)findViewById(R.id.recycler_view);



                /* mClass = new RecyclerClass(recylerlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mClass);

        prepareData();*/
               // checkForInternet = isInternetAvailable();

        if(isNetworkConnected()){
            Toast.makeText(this, "Connected to internet", Toast.LENGTH_SHORT).show();

        }
        else{
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog);
            builder.setTitle("No Internet Connection").setMessage("Connect to the INTERNET and try again").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            }).show();
        }
        msgDetails = new ArrayList<MessageDetails>();


        userValue = shared.getString("userValue", null);
        area = shared.getString("area", null);
        news = shared.getString("news", null);
        mRef = mDatabase.getReference();

       // display = (TextView) findViewById(R.id.sss);








/*FirebaseListAdapter adapter = new FirebaseListAdapter<Model>(this,Model.class,R.layout.lists,FirebaseDatabase.getInstance().getReference()) {
    @Override
    protected void populateView(View v, Model model, int position) {
        TextView displayArea = (TextView)findViewById(R.id.areafield);
        TextView displayName = (TextView)findViewById(R.id.nameOfPoster);
        TextView displayPost = (TextView)findViewById(R.id.postingNews);

        displayName.setText(model.getUser());
        displayArea.setText(model.getArea());
        displayPost.setText(model.getActualNews());
    }

};
listView.setAdapter(adapter);*/


        post = (FloatingActionButton) findViewById(R.id.floating);
        //wasd = (TextView) findViewById(R.sss);


        //ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_main,s);

        // listOfNews = (ListView)findViewById(R.id.list);
        //listOfNews.setAdapter(adapter);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent toPostNew = new Intent(MainActivity.this, PostNews.class);
                    startActivity(toPostNew);
                } catch (Exception e) {
                    Log.e("error", e.toString());

                }
            }
        });


//dataModels.add(new Model("hjj","jkhk","hj"));
FirebaseDatabase.getInstance().getReference().child("RootNode")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model  user = snapshot.getValue(Model.class);
                    s1 = user.getUser();
                    s2=user.getArea();
                    s3 = user.getActualNews();
                    s4=user.getTime();
                    call(s1,s2,s3,s4);
                    mProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s1 = dataSnapshot.child("RootNode").child("shiva").getValue(Model.class).getUser().toString();
                s2 = dataSnapshot.child("RootNode").child("shiva").getValue(Model.class).getArea().toString();
                s3 = dataSnapshot.child("RootNode").child("shiva").getValue(Model.class).getActualNews().toString();
                Toast.makeText(MainActivity.this, "successful" + s1 + s2 + s3, Toast.LENGTH_SHORT).show();

                call(s1,s2,s3);


                //  Toast.makeText(MainActivity.this, ""+s2, Toast.LENGTH_SHORT).show();
                // s3 = dataSnapshot.child("RootNode").child("shiva").getValue(Model.class).getArea().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //     lst.add("hello");


}

    private void call(String s1,String s2,String s3,String s4) {
        objectToAdd = new MessageDetails();
        objectToAdd.setMyName(s1);
        objectToAdd.setMyArea(s2);
        objectToAdd.setMyPost(s3);
        objectToAdd.setTime(s4);
        msgDetails.add(objectToAdd);

        listOfPosts.setAdapter(new CustomAdapter(msgDetails, this));
    }
private boolean isNetworkConnected(){
    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();

    return cm.getActiveNetworkInfo()!=null && activeNetworkInfo.isConnected();
}
public boolean isInternetAvailable(){
    try{
        InetAddress ipAddr = InetAddress.getByName("google.com");
        return !ipAddr.equals("");


    }catch (Exception e){
        return false;
    }
}


    public void checkForConnection(){
        if(isNetworkConnected()){
            Toast.makeText(this, "Connected to internet", Toast.LENGTH_SHORT).show();

        }
        else{
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog);
            builder.setTitle("No Internet Connection").setMessage("Connect to the INTERNET and try again").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            }).show();
        }
    }

}
