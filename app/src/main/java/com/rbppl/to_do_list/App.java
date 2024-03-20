package com.rbppl.to_do_list;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.rbppl.to_do_list.ui.tasks.TaskFragment;

public class App extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TaskFragment fragment = new TaskFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
