package com.example.pramoda_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BillActivity extends AppCompatActivity {
    private EditText num1;
    private Button add;
    private TextView result;
    private Button move1;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        move1=findViewById(R.id.ok_btn);

        //calculation part
        num1 =(EditText) findViewById(R.id.etNum1);
        add = (Button) findViewById(R.id.btnAdd);
        result = (TextView)findViewById(R.id.tvAnswer);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = Integer.parseInt(num1.getText().toString());

                int sum = number1 + 500;
                result.setText("Total Amount = Rs. " + String.valueOf(sum));
            }
        });
        move1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(BillActivity.this,invoice.class);
                startActivity(intent);
            }
        });
    }
}
//..
