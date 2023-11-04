package com.rbppl.to_do_list
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rbppl.to_do_list.ui.tasks.TaskFragment
class App : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = TaskFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}