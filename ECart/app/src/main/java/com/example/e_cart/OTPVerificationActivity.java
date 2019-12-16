package com.example.e_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPVerificationActivity extends AppCompatActivity {
    private TextView phone;
    private EditText otptext;
    private Button verifyBtn;
    private String intentmobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        phone = findViewById(R.id.phone);
        otptext = findViewById(R.id.enterotp);
        verifyBtn  =findViewById(R.id.verify);
        intentmobile  = getIntent().getStringExtra("MOBILE");

        phone.setText("Verification Code has been sent to "+ intentmobile);

        Random random = new Random();
        final int otp_number =random.nextInt(999999-111111)+111111;
        String SMS_API =  "https://www.fast2sms.com/dev/bulk";


        final StringRequest stringRequest = new StringRequest(Request.Method.POST,SMS_API , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                verifyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         if(otptext.getText().toString().equals(String.valueOf(otp_number)))
                         {
                             DeliveryActivity.codOrderConfirmed =true;
                             finish();
                         }else{
                             Toast.makeText(OTPVerificationActivity.this, "OTP InCorrect", Toast.LENGTH_SHORT).show();
                         }
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OTPVerificationActivity.this, "Failed to send Message", Toast.LENGTH_SHORT).show();
                finish();


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String,String> header = new HashMap<>();
            header.put("authorization", "WoITlYuwrOP6dyFC8gZteN79RxbscAEpanqvkDh4L5SK0zfUQmcCqMTgObZKit2eNRu6X14Q3Er0mP5f");
              return  header;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> body = new HashMap<>();
                body.put("sender_id", "FSTSMS");
                body.put("language", "english");
                body.put("route", "qt");
                body.put("numbers", intentmobile);
                body.put("message", "17186");
                body.put("variables", "{#BB#}");
                body.put("variables_values", String.valueOf(otp_number));

                return body;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
           5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(OTPVerificationActivity.this);
        requestQueue.add(stringRequest);







     }
}
