package com.example.pramoda_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText mName , mPhone, mAddress, mDate, mTime ;
    private Button mConfirmBtn, mViewBtn;
    private FirebaseFirestore db;
    private String uName , uPhone, uAddress, uDate, uTime, uId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //assign variables
        mName = findViewById(R.id.edit_name);
        mPhone = findViewById(R.id.edit_phone);
        mAddress = findViewById(R.id.edit_address);
        mDate = findViewById(R.id.edit_date);
        mTime = findViewById(R.id.edit_time);
        mConfirmBtn = findViewById(R.id.confirm_btn);
        mViewBtn = findViewById(R.id.view_btn);

        db= FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        //validate empty fields
        if (bundle != null){
            mConfirmBtn.setText("Update");
            uName = bundle.getString("uName");
            uId = bundle.getString("uId");
            uPhone = bundle.getString("uPhone");
            uAddress = bundle.getString("uAddress");
            uDate = bundle.getString("uDate");
            uTime = bundle.getString("uTime");
            mName.setText(uName);
            mPhone.setText(uPhone);
            mAddress.setText(uAddress);
            mDate.setText(uDate);
            mTime.setText(uTime);

        }


        else{
            mConfirmBtn.setText("Save");
        }


      mViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ShowActivity.class));
            }
        });


        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check validation

                String name = mName.getText().toString();
                String phone = mPhone.getText().toString();
                String address = mAddress.getText().toString();
                String date = mDate.getText().toString();
                String time = mTime.getText().toString();


                Bundle bundle1 = getIntent().getExtras();

                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , name, phone, address, date, time);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id , name, phone, address, date, time);
                }
            }

        });
}

    private void updateToFireStore(String id , String name , String phone, String address, String date, String time){

        db.collection("Documents").document(id).update("name" , name, "phone" , phone, "address", address, "date", date, "time", time)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Order details Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void saveToFireStore(String id , String name , String phone, String address, String date, String time){

        if (!name.isEmpty() && !phone.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("name" , name);
            map.put("phone" , phone);
            map.put("address" , address);
            map.put("date" , date);
            map.put("time" , time);

            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Successfully confirmed !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

           
        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}

