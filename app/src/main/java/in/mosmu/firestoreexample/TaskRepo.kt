package `in`.mosmu.firestoreexample

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

const val TAG = "TaskRepo"

class TaskRepo : ITaskRepo {

    var savedTasks: MutableLiveData<List<Task>> = MutableLiveData()

    private val remoteDB = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
    }

    override fun getAllTask(): MutableLiveData<List<Task>> {
        remoteDB.collection("Tasks").addSnapshotListener(EventListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                savedTasks.value = null
                return@EventListener
            }

            val saveTasksList: MutableList<Task> = mutableListOf()
            for (doc in value!!) {
                val taskItem =
                    doc.toObject(RemoteTask::class.java).apply { id = doc.id }.mapToTask()
                saveTasksList.add(taskItem)
            }
            savedTasks.value = saveTasksList
        })
        return savedTasks
    }

    override fun addTask(task: Task) {
        val taskData = HashMap<String, Any>()
        taskData["title"] = task.title
        taskData["created"] =
            Timestamp(task.created.time / 1000, (task.created.time % 1000 * 1000).toInt())
        remoteDB.collection("Tasks")
            .add(taskData)
    }

    override fun deleteTask(taskId: String) {
        remoteDB.collection("Tasks")
            .document(taskId)
            .delete()
    }

}