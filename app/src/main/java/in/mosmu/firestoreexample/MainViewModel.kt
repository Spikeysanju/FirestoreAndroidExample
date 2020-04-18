package `in`.mosmu.firestoreexample

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val repository: ITaskRepo = TaskRepo()

    fun getTasks() = repository.getAllTask()

    fun deleteTask(taskId: String) {
        repository.deleteTask(taskId)
    }

    fun addTask(taskTitle: String) {
        repository.addTask(Task("${System.currentTimeMillis()}", taskTitle))
    }

}