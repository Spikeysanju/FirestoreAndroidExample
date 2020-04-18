package `in`.mosmu.firestoreexample

import com.google.firebase.Timestamp
import java.util.*

data class Task(
    var id: String = "",
    var title: String = "",
    var created: Date = Date()
)

data class RemoteTask(
    var id: String = "",
    var title: String = "",
    var created: Timestamp = Timestamp(0, 0)
)

fun RemoteTask.mapToTask(): Task {
    return Task(
        id,
        title,
        created.toDate()
    )
}