package kathy.nick.mike.cohinza;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    public static final String QUERY_KEY = "QUERY_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mainActivity = (RelativeLayout) findViewById(R.id.activity_main);
        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(MainActivity.this);
            }
        });

        EditText mEditText = (EditText) findViewById(R.id.home_page_search);
        mEditText.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                    EditText mEditText = (EditText) findViewById(R.id.home_page_search);
                    String message = mEditText.getText().toString();
                    intent.putExtra(QUERY_KEY, message);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mButton = (Button) findViewById(R.id.home_page_search_button);
        mButton.setBackgroundResource(R.drawable.home_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                EditText mEditText = (EditText) findViewById(R.id.home_page_search);
                String message = mEditText.getText().toString();
                intent.putExtra(QUERY_KEY, message);
                startActivity(intent);
            }
        });

    }


    public static void closeKeyboard(Activity MainActivity) {
        InputMethodManager manageInput = (InputMethodManager)
                MainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        manageInput.hideSoftInputFromWindow(MainActivity.getCurrentFocus().getWindowToken(), 0);
    }
}
