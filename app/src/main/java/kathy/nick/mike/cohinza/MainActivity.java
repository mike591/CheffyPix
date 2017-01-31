package kathy.nick.mike.cohinza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private static final String QUERY_KEY = "QUERY_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.home_page_search_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                EditText mEditText = (EditText) findViewById(R.id.home_page_search);
                String message = mEditText.getText().toString();
                intent.putExtra(QUERY_KEY, message);
                startActivity(intent);
            }
        });



    }
}
