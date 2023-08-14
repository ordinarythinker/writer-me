package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.utils.getDefaultInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Closeable

class BookmarksRepository(scope: CoroutineScope): Closeable {

    lateinit var realm: Realm

    init {
        scope.launch(Dispatchers.Main) {
            realm = Realm.getDefaultInstance()
        }
    }

    suspend fun getMainFolder(): BookmarksFolder {
        val result = realm.query(BookmarksFolder::class, "id = $0", 0).first().find()

        return result
            ?: realm.write {
                val s = BookmarksFolder()
                copyToRealm(s)
            }
    }

    suspend fun createFolder(name: String, parent: BookmarksFolder? = null) {
        val _parent = parent ?: getMainFolder()

        realm.write {
            val folder = BookmarksFolder().apply {
                this.name = name
                this.parent = _parent
            }

            copyToRealm(folder)
        }
    }

    suspend fun createBookmark(url: String, title: String, parent: BookmarksFolder? = null) {
        val _parent = parent ?: getMainFolder()

        realm.write {
            val bookmark = Component().apply {
                this.type = ComponentType.Link
                this.title = title
                this.url = url
            }

            val realmObj = copyToRealm(bookmark)

            _parent.bookmarks.add(realmObj)// possible place of an error: object cannot be used in the different thread
        }
    }

    override fun close() {
        realm.close()
    }
}