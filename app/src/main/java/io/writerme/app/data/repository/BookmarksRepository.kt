package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.utils.getDefaultInstance
import java.io.Closeable

class BookmarksRepository: Repository(), Closeable {

    private val realm: Realm = Realm.getDefaultInstance()

    suspend fun getMainFolder(): BookmarksFolder {
        val result = realm.query(BookmarksFolder::class, "id == $0", 0).first().find()

        return result
            ?: realm.write {
                val s = BookmarksFolder().apply {
                    this.id = 0
                }
                copyToRealm(s)
            }
    }

    suspend fun createFolder(name: String, parent: BookmarksFolder? = null) {

        realm.write {
            val _parent = parent ?: realm.query(BookmarksFolder::class, "id == $0", 0).first().find()

            val folder = BookmarksFolder().apply {
                this.name = name
                this.parent = _parent
            }

            copyToRealm(folder)
        }
    }

    suspend fun createBookmark(url: String, title: String, parent: BookmarksFolder? = null): Component {

        return realm.write {
            val _parent = parent ?: realm.query(BookmarksFolder::class, "id == $0", 0).first().find()

            val bookmark = Component().apply {
                this.type = ComponentType.Link
                this.title = title
                this.url = url
            }

            val realmObj = copyToRealm(bookmark)

            _parent?.let {
                findLatest(it)?.bookmarks?.add(realmObj)
            }

            bookmark
        }
    }

    override fun close() {
        realm.close()
    }
}