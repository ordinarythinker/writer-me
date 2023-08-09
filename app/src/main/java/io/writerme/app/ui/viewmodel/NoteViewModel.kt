package io.writerme.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.writerme.app.data.model.Component

class NoteViewModel : ViewModel() {



    fun dealHistory(execute: () -> Component?) {
        val c = execute()

        c?.let { component ->

//            realm.executeTransactionAsync(Realm.Transaction {
//                component.deleteFromRealm()
//            }, Realm.Transaction.OnError {
//                Log.e(TAG, "Cannot delete object from Realm", it)
//            })
        }
    }

    companion object {
        const val TAG = "NoteViewModel"
    }
}