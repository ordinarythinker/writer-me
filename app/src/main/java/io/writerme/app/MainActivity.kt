package io.writerme.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.navigation.*
import io.writerme.app.ui.screen.BookmarksScreen
import io.writerme.app.ui.screen.GreetingScreen
import io.writerme.app.ui.screen.HomeScreen
import io.writerme.app.ui.screen.NoteScreen
import io.writerme.app.ui.screen.RegistrationScreen
import io.writerme.app.ui.screen.SettingsScreen
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.utils.Const
import io.writerme.app.utils.LocaleUtils
import io.writerme.app.viewmodel.BookmarksViewModel
import io.writerme.app.viewmodel.HomeViewModel
import io.writerme.app.viewmodel.NoteViewModel
import io.writerme.app.viewmodel.RegistrationViewModel
import io.writerme.app.viewmodel.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
                    color = Color.Transparent,
                ) {

                    val startingRoute: String = runBlocking {
                        withContext(Dispatchers.IO) {
                            val settingsRepository = SettingsRepository()
                            val route = if (settingsRepository.getSettings().fullName.isNotEmpty()) {
                                HomeScreen.route
                            } else GreetingScreen.route
                            settingsRepository.close()

                            route
                        }
                    }
                    val context = LocalContext.current

                    NavHost(
                        navController = navController,
                        startDestination = startingRoute
                    ) {
                        composable(GreetingScreen.route) {
                            GreetingScreen {
                                navController.navigate(RegistrationScreen.route)
                            }
                        }

                        composable(RegistrationScreen.route) {
                            val registrationViewModel = hiltViewModel<RegistrationViewModel>()

                            RegistrationScreen(
                                saveName = registrationViewModel::saveName,
                                proceedToNextScreen = { navController.navigate(HomeScreen.route) }
                            )
                        }

                        composable(HomeScreen.route) {
                            val homeViewModel = hiltViewModel<HomeViewModel>()

                            HomeScreen(
                                stateFlow = homeViewModel.homeStateFlow,
                                toggleSearchMode = homeViewModel::toggleSearchMode,
                                openTasksScreen = {
                                    //navController.navigate(TasksScreen.route)

                                    Toast.makeText(context, R.string.sorry_pending, Toast.LENGTH_LONG).show()
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
                                toggleImportance = homeViewModel::toggleImportance,
                                deleteNote = homeViewModel::deleteNote
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
                                updateCoverImage = noteViewModel::updateCoverImage,
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
                                toggleAddLinkDialogVisibility = noteViewModel::toggleAddLinkDialogVisibility,
                                toggleCheckbox = noteViewModel::toggleCheckbox,
                                deleteSection = noteViewModel::deleteSection
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
                                createFolder = bookmarksViewModel::createFolder,
                                deleteFolder = bookmarksViewModel::deleteFolder,
                                deleteBookmark = bookmarksViewModel::deleteBookmark,
                                dismissScreen = { navController.popBackStack() }
                            )
                        }

                        composable(SettingsScreen.route) {
                            val settingsViewModel = hiltViewModel<SettingsViewModel>()

                            SettingsScreen(
                                uiState = settingsViewModel.settingsState,
                                onLanguageChange = {
                                    LocaleUtils.setLocale(it)
                                    settingsViewModel.onLanguageChange(it)
                                },
                                onDarkModeChange = settingsViewModel::onDarkModeChange,
                                onTermsClick = { onLinkClicked(Const.TERMS_LINK) },
                                onCounterChange = settingsViewModel::onCounterChange,
                                updateProfileImage = settingsViewModel::updateProfileImage,
                                dismissScreen = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

