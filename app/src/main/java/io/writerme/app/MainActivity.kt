package io.writerme.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.writerme.app.ui.navigation.Bookmarks
import io.writerme.app.ui.navigation.Home
import io.writerme.app.ui.navigation.Note
import io.writerme.app.ui.navigation.Settings
import io.writerme.app.ui.screen.BookmarksScreen
import io.writerme.app.ui.screen.HomeScreen
import io.writerme.app.ui.screen.SettingsScreen
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.utils.Const
import io.writerme.app.viewmodel.BookmarksViewModel
import io.writerme.app.viewmodel.SettingsViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private fun onTermsClicked() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Const.TERMS_LINK))
        startActivity(browserIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Initialize settings

        setContent {
            WriterMeApp()
        }
    }
}

@Composable
fun WriterMeApp() {
    val navController = rememberNavController()

    WriterMeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Home.route
            ) {
                composable(Home.route) {
                    HomeScreen()

                }
                composable(Note.route) {
                    //NoteScreen(noteState = , onTitleChange = , showHashtagBar = )
                }

                composable(Bookmarks.route) {
                    val bookmarksViewModel = hiltViewModel<BookmarksViewModel>()

                    BookmarksScreen(
                        bookmarksState = bookmarksViewModel.bookmarksStateFlow,
                        onFolderClicked = {},
                        onLinkClicked = {},
                        showCreateFolderDialog = bookmarksViewModel::showCreateFolderDialog,
                        dismissCreateFolderDialog = bookmarksViewModel::dismissCreateFolderDialog,
                        showCreateBookmarkDialog = bookmarksViewModel::showCreateBookmarkDialog,
                        dismissCreateBookmarkDialog = bookmarksViewModel::dismissCreateBookmarkDialog,
                        showFloatingDialog = bookmarksViewModel::showFloatingDialog,
                        dismissFloatingDialog = bookmarksViewModel::dismissFloatingDialog,
                        createBookmark = bookmarksViewModel::createBookmark,
                        createFolder = bookmarksViewModel::createFolder
                    )
                }

                composable(Settings.route) {
                    val settingsViewModel = hiltViewModel<SettingsViewModel>()

                    SettingsScreen(
                        uiState = settingsViewModel.settingsState,
                        onLanguageChange = settingsViewModel::onLanguageChange,
                        onDarkModeChange = settingsViewModel::onDarkModeChange,
                        onTermsClick = {
                            // TODO
                        },
                        onCounterChange = settingsViewModel::onCounterChange
                    )
                }
            }
        }
    }
}
