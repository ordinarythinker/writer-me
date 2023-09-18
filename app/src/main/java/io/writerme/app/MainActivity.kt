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
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.writerme.app.ui.navigation.*
import io.writerme.app.ui.screen.BookmarksScreen
import io.writerme.app.ui.screen.GreetingScreen
import io.writerme.app.ui.screen.HomeScreen
import io.writerme.app.ui.screen.NoteScreen
import io.writerme.app.ui.screen.RegistrationScreen
import io.writerme.app.ui.screen.SettingsScreen
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.utils.Const
import io.writerme.app.viewmodel.BookmarksViewModel
import io.writerme.app.viewmodel.HomeViewModel
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

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreen.route
                    ) {
                        composable(GreetingScreen.route) {
                            GreetingScreen {
                                navController.navigate(RegistrationScreen.route)
                            }
                        }

                        composable(RegistrationScreen.route) {
                            RegistrationScreen(
                                saveName = {
                                    // TODO
                                },
                                proceedToNextScreen = { navController.navigate(HomeScreen.route) }
                            )
                        }

                        composable(HomeScreen.route) {
                            val homeViewModel = hiltViewModel<HomeViewModel>()

                            HomeScreen(
                                stateFlow = homeViewModel.homeStateFlow,
                                toggleSearchMode = homeViewModel::toggleSearchMode,
                                openTasksScreen = {
                                    navController.navigate(TasksScreen.route)
                                },
                                openBookmarksScreen = {
                                    navController.navigate(BookmarksScreen.route)
                                },
                                openSettingsScreen = {
                                    navController.navigate(SettingsScreen.route)
                                },
                                onNoteClick = {
                                    navController.navigate("${NoteScreen.navigationRoute}$it")
                                },
                                createNote = {
                                    navController.navigate(NoteScreen.navigationRoute)
                                },
                                onTabChosen = homeViewModel::onTabChosen,
                                toggleNoteDropdown = homeViewModel::toggleNoteDropdown,
                                toggleImportance = homeViewModel::toggleImportance
                            )
                        }
                        composable(
                            NoteScreen.route,
                            arguments = listOf(
                                navArgument(NoteScreen.NOTE_PARAM) {
                                    nullable = true
                                }
                            )
                        ) {
                            val noteViewModel = hiltViewModel<NoteViewModel>()

                            NoteScreen(
                                noteState = noteViewModel.noteState,
                                toggleHistoryMode = noteViewModel::toggleHistoryMode,
                                toggleTopBarDropdownVisibility = noteViewModel::toggleTopBarDropdownVisibility,
                                addCoverImage = noteViewModel::addCoverImage,
                                onTitleChange = noteViewModel::onTitleChange,
                                showHashtagBar = noteViewModel::showHashtagBar,
                                addNewTag = noteViewModel::addNewTag,
                                deleteTag = noteViewModel::deleteTag,
                                modifyHistory = noteViewModel::modifyHistory,
                                saveChanges = noteViewModel::saveChanges,
                                onComponentChange = noteViewModel::onComponentChange,
                                addNewCheckBox = noteViewModel::addNewCheckBox,
                                navigateBack = { navController.popBackStack() },
                                addImageSection = noteViewModel::addImageSection,
                                showDropdown = noteViewModel::showDropdown,
                                dismissDropDown = noteViewModel::dismissDropDown,
                                toggleDropDownHistoryMode = noteViewModel::toggleDropDownHistoryMode,
                                addLinkSection = noteViewModel::addLinkSection,
                                toggleAddLinkDialogVisibility = noteViewModel::toggleAddLinkDialogVisibility
                            )
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

