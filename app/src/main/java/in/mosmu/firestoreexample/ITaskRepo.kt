package `in`.mosmu.firestoreexample

import androidx.lifecycle.MutableLiveData

interface ITaskRepo {
    fun getAllTask(): MutableLiveData<List<Task>>
    fun addTask(task: Task)
    fun deleteTask(taskId: String)
}