package io.writerme.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.writerme.app.data.model.Component

class NoteViewModel : ViewModel() {

    lateinit var realm: Realm

    fun dealHistory(execute: () -> Component?) {
        val c = execute()

        c?.let { component ->
            realm.executeTransactionAsync(Realm.Transaction {
                component.deleteFromRealm()
            }, Realm.Transaction.OnError {
                Log.e(TAG, "Cannot delete object from Realm", it)
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

    companion object {
        const val TAG = "NoteViewModel"
    }
}