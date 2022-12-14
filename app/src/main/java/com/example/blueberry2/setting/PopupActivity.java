package com.example.blueberry2.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.blueberry2.LoginActivity;
import com.example.blueberry2.MainActivity;
import com.example.blueberry2.R;
import com.example.blueberry2.chat.MessageActivity;
import com.example.blueberry2.fragment.MyInfoFragment;
import com.example.blueberry2.model.ChatModel;
import com.example.blueberry2.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class PopupActivity extends FragmentActivity{

    private EditText txtText;

    private Button btn;

    private String uid;
    private String username;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private String profileimgurl;


    private Button button;

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); //???????????? ????????? -> ?????? ???????????? PopupActivity ?????????!


        //UI ????????????

        InfoAdapter infoAdapter=new InfoAdapter();
        infoAdapter.getInfoAdapter();


        txtText = (EditText) findViewById(R.id.myinfo_popup_txtText);



        button=(Button) findViewById(R.id.myinfo_popup_btn);

        //profileimgurl=userModel_org.profileImageUrl;

        //profileimgurl="ccc";
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid(); // ??? uid ????????????
        databaseReference= FirebaseDatabase.getInstance().getReference();






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtText.getText().toString().equals("")){
                    return;
                }

                UserModel userModel=new UserModel();
                userModel.uid=uid;
                userModel.profileImageUrl=profileimgurl;
                userModel.userName=txtText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                          finish();

                    }
                });



            }
        });

    }

    class InfoAdapter {
        UserModel userModel_org;


        public void getInfoAdapter(){
            uid=FirebaseAuth.getInstance().getCurrentUser().getUid(); // ??? uid ????????????

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userModel_org=dataSnapshot.getValue(UserModel.class);
                    username=userModel_org.userName;
                    profileimgurl=userModel_org.profileImageUrl;
                    userid=userModel_org.uid;


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    //?????? ?????? ??????
    public void mOnClose(View v){
        //????????? ????????????
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //????????????(??????) ??????
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //??????????????? ????????? ????????????
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
//    public class goFragment extends FragmentActivity{
//        public void goMyInfo(){
//            MyInfoFragment fragment1=new MyInfoFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.myinfo_popup_btn, new MyInfoFragment()).commit();
//
//        }
//    }


}