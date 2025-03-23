package com.example.simpletodocusatteaching.model

import android.util.Log
import com.example.simpletodocusatteaching.ui.components.TodoItemModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange.Type.REMOVED
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import javax.inject.Inject

private const val TAG = "StorageServicesImpl"
class StorageServicesImpl(
    private val reference: CollectionReference
) : StorageServices {

    lateinit var listenerRegistration: ListenerRegistration

    override fun addListener(
        onDocumentEvent: (Boolean, TodoItemModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        listenerRegistration = reference.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {
                Log.d(TAG, "addListener: ${it.document
                    .toObject<TodoItemModel>()}")
                val wasDocumentDeleted = it.type == REMOVED
                val todo = it.document
                    .toObject<TodoItemModel>().copy(
                        id = it.document.id,
                    )

                onDocumentEvent(wasDocumentDeleted, todo)
            }
        }
    }

    override fun removeListener() {
        listenerRegistration.remove()
    }

    override fun saveTodo(
        item: TodoItemModel,
        onResult: (Throwable?) -> Unit
    ) {
        reference
            .document(item.id)
            .set(item)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun updateTodo(
        item: TodoItemModel,
        onResult: (Throwable?) -> Unit
    ) {
        Log.d(TAG, "updateTodo: $item")
        reference.document(item.id)
            .set(item)
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun deleteTodo(
        item: TodoItemModel,
        onResult: (Throwable?) -> Unit
    ) {
        reference
            .document(item.id)
            .delete()
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun getTodos(
        items: List<TodoItemModel>,
        onResult: (Throwable?) -> Unit
    ) {

    }
}