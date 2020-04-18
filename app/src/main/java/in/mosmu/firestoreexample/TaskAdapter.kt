package `in`.mosmu.firestoreexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.single_task.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private val taskClickListener: OnTaskClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val taskList = emptyList<Task>().toMutableList()
    private val createdFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_task, parent, false)
        view.imageViewDeleteTask.setOnClickListener { button ->
            taskClickListener.run {
                this.onDeleteClick(
                    button.tag as String
                )
            }
        }
        return TaskViewHolder(view)
    }

    override fun getItemCount() = taskList.size

    fun setItems(newTaskList: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(TaskDiffUtilCallback(taskList, newTaskList))

        taskList.clear()
        taskList.addAll(newTaskList)

        diffResult.dispatchUpdatesTo(this)
    }

    class TaskViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        with(holder.containerView) {
            textViewTitle.text = task.title
            textViewTime.text = createdFormat.format(task.created)

            imageViewDeleteTask.tag = taskList[position].id
        }
    }
}

interface OnTaskClickListener {
    fun onDeleteClick(taskId: String)
}