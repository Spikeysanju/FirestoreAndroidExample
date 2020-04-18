package `in`.mosmu.firestoreexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val taskAdapter = TaskAdapter(object : OnTaskClickListener {
            override fun onDeleteClick(taskId: String) {
                viewModel.deleteTask(taskId)
            }

        })
        tasksRecylerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        tasksRecylerView.adapter = taskAdapter
        viewModel.getTasks().observe(this, Observer {
            taskAdapter.setItems(it)
        })

        buttonAdd.setOnClickListener {
            viewModel.addTask(editTextTask.text.toString())
            editTextTask.text?.clear()
        }
    }
}
