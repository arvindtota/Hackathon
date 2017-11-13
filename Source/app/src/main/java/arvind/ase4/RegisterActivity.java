package arvind.ase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etname = (EditText) findViewById(R.id.etname);
        final EditText etusername = (EditText) findViewById(R.id.etusername);
        final EditText etemail = (EditText) findViewById(R.id.etemail);
        final EditText etpassword = (EditText) findViewById(R.id.etpassword);
        final EditText etconfpassword = (EditText) findViewById(R.id.etconfpassword);
        final Button bregister = (Button) findViewById(R.id.bsignup);

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity.this, Login_Page.class);
                RegisterActivity.this.startActivity(registerIntent);
            }
        });

    }
}
