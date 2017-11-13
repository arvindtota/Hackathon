package arvind.ase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText etusername = (EditText) findViewById(R.id.etusername);
        final EditText etpassword = (EditText) findViewById(R.id.etpassword);
        final Button blogin = (Button) findViewById(R.id.blogin);
        final Button bsignup = (Button) findViewById(R.id.bsignup);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().equals("Arvind") && etpassword.getText().toString().equals("Arvind")) {

                    Intent loginIntent = new Intent(Login_Page.this, MainActivity.class);
                Login_Page.this.startActivity(loginIntent);
                } else
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent signupIntent = new Intent(Login_Page.this, RegisterActivity.class);
                    Login_Page.this.startActivity(signupIntent);

            }

        });
    }
}
