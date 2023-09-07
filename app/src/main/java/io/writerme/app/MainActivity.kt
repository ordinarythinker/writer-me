package io.writerme.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.writerme.app.ui.navigation.BookmarksScreen
import io.writerme.app.ui.navigation.HomeScreen
import io.writerme.app.ui.navigation.NoteScreen
import io.writerme.app.ui.navigation.SettingsScreen
import io.writerme.app.ui.screen.BookmarksScreen
import io.writerme.app.ui.screen.SettingsScreen
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.utils.Const
import io.writerme.app.viewmodel.BookmarksViewModel
import io.writerme.app.viewmodel.NoteViewModel
import io.writerme.app.viewmodel.SettingsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tag = "MainActivity"

    private fun onLinkClicked(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } catch (e: Exception) {
            Log.e(tag, "", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            WriterMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = SettingsScreen.route
                    ) {
                        composable(HomeScreen.route) {
                            //HomeScreen()

                        }
                        composable(NoteScreen.route) {
                            val noteViewModel = hiltViewModel<NoteViewModel>()
                            //NoteScreen(noteState = , onTitleChange = , showHashtagBar = )
                        }

                        composable(BookmarksScreen.route) {
                            val bookmarksViewModel = hiltViewModel<BookmarksViewModel>()

                            BookmarksScreen(
                                bookmarksState = bookmarksViewModel.bookmarksStateFlow,
                                onFolderClicked = bookmarksViewModel::onFolderClicked,
                                onLinkClicked = { onLinkClicked(it.url) },
                                showCreateFolderDialog = bookmarksViewModel::showCreateFolderDialog,
                                dismissCreateFolderDialog = bookmarksViewModel::dismissCreateFolderDialog,
                                showCreateBookmarkDialog = bookmarksViewModel::showCreateBookmarkDialog,
                                dismissCreateBookmarkDialog = bookmarksViewModel::dismissCreateBookmarkDialog,
                                showFloatingDialog = bookmarksViewModel::showFloatingDialog,
                                dismissFloatingDialog = bookmarksViewModel::dismissFloatingDialog,
                                navigateToParentFolder = bookmarksViewModel::navigateToParentFolder,
                                createBookmark = bookmarksViewModel::createBookmark,
                                createFolder = bookmarksViewModel::createFolder
                            )
                        }

                        composable(SettingsScreen.route) {
                            val settingsViewModel = hiltViewModel<SettingsViewModel>()

                            SettingsScreen(
                                uiState = settingsViewModel.settingsState,
                                onLanguageChange = settingsViewModel::onLanguageChange,
                                onDarkModeChange = settingsViewModel::onDarkModeChange,
                                onTermsClick = { onLinkClicked(Const.TERMS_LINK) },
                                onCounterChange = settingsViewModel::onCounterChange
                            )
                        }
                    }
                }
            }
        }
    }
}

