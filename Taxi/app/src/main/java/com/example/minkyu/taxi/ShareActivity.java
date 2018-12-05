package com.example.minkyu.taxi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ActionBar ab = getSupportActionBar() ;

        ab.setIcon(R.drawable.typing) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true);

        final Intent intent = getIntent();

        final String Addr = intent.getStringExtra("Addr");

        TextView textView = (TextView) findViewById(R.id.IDep);
        TextView textView1 = (TextView) findViewById(R.id.IDes);
        TextView textView2 = (TextView) findViewById(R.id.ITime);
        TextView textView3 = (TextView) findViewById(R.id.IMin);
        TextView textView4 = (TextView) findViewById(R.id.ISay);
        textView.setText(intent.getStringExtra("Dep"));
        textView1.setText(intent.getStringExtra("Des"));
        textView2.setText(intent.getStringExtra("Time"));
        textView3.setText(intent.getStringExtra("Min"));
        textView4.setText(intent.getStringExtra("Say"));

        Button b1 = (Button) findViewById(R.id.Receivebtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShareActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_popup, null);
                TextView txtText = (TextView) mView.findViewById(R.id.textView9);
                final TextView txtText2 = (TextView) mView.findViewById(R.id.textView11);
                Button okbtn = (Button) mView.findViewById(R.id.button1);
                Button sharebtn = (Button) mView.findViewById(R.id.Sharebutton);
                txtText.setText(intent.getStringExtra("Addr"));
                txtText2.setText(intent.getStringExtra("Phone"));
                final String number = txtText2.getText().toString();
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                sharebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number.isEmpty()){
                            Toast.makeText(getApplicationContext(),"입력된 연락처가 없습니다."
                                    ,Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"+number));
                            startActivity(in);
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        Button b2 = (Button) findViewById(R.id.Backbtn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(ShareActivity.this, ListActivity.class);
                startActivity(i2);
            }
        });
    }
}
