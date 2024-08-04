package sh.ld2.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import sh.ld2.example.coordinators.dataCoordinator.DataCoordinator
import sh.ld2.example.coordinators.dataCoordinator.updateSampleString
import sh.ld2.example.coordinators.languageCoordinator.LanguageCoordinator
import sh.ld2.example.coordinators.languageCoordinator.updateCurrentContent
import sh.ld2.example.coordinators.notificationCoordinator.NotificationCoordinator
import sh.ld2.example.coordinators.notificationCoordinator.sendSampleIntent
import sh.ld2.example.models.constants.DebuggingIdentifiers
import sh.ld2.example.models.keys.MainActivityStateKeys
import sh.ld2.example.models.notifications.SystemNotifications
import sh.ld2.example.models.states.ExperienceStates
import sh.ld2.example.utils.system.getOrientation

class MainActivity : ComponentActivity() {
    // MARK: Variables
    val identifier = "[MainActivity]"

    // MARK: States
    // / EXPERIENCE STATES
    var state: MutableState<ExperienceStates> = mutableStateOf(ExperienceStates.LANDING)

    // MARK: Broadcast Reciever
    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.i(
                identifier,
                "${DebuggingIdentifiers.actionOrEventSucceded} ${DebuggingIdentifiers.notificationRecieved}  $intent | ${intent.extras}",
            )
            when (intent.action) {
                SystemNotifications.sampleIntent -> onSampleIntent(intent = intent)
                SystemNotifications.onLanguageContentUpdateIntent -> onLanguageContentUpdateIntent(
                    intent = intent,
                )

                SystemNotifications.onUpdateExperienceStateIntent -> onUpdateExperienceStateIntent(
                    intent = intent,
                )
            }
        }
    }

    // MARK: Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNotifications()
        setupCoordinators()
        setupUI()

        val orientation = when (getOrientation(context = baseContext)) {
            Surface.ROTATION_0 -> "Portrait"
            Surface.ROTATION_90 -> "Landscape Right"
            Surface.ROTATION_180 -> "Upside Down"
            Surface.ROTATION_270 -> "Landscape Left"
            else -> "Unknown"
        }
        Log.i(
            identifier,
            "${DebuggingIdentifiers.actionOrEventInProgress} orientation : $orientation.",
        )
        // Test an update
        DataCoordinator.shared.updateSampleString("Hello World!")
        // Back Button Navigation
        // In this case, make sure that it performs the relevant experience state update.
        // If you were to use this within a composable that has custom actions, return @callback and setup a Composable BackHandler to handle the action.
        onBackPressedDispatcher.addCallback {
            when (state.value) {
                ExperienceStates.LANDING -> return@addCallback
                ExperienceStates.MENU -> state.value = ExperienceStates.LANDING
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LanguageCoordinator.shared.updateCurrentContent()
        NotificationCoordinator.shared.sendSampleIntent()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    // MARK: State Persistence Functionality
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(
            identifier,
            "${DebuggingIdentifiers.actionOrEventInProgress} onSaveInstanceState ${DebuggingIdentifiers.actionOrEventInProgress}",
        )
        // Experience States
        outState.putInt(MainActivityStateKeys.experienceState, state.value.ordinal)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(
            identifier,
            "${DebuggingIdentifiers.actionOrEventInProgress} onRestoreInstanceState ${DebuggingIdentifiers.actionOrEventInProgress}",
        )
        // If we have a saved state then we can restore it now
        if (savedInstanceState != null) {
            // Experience States
            val experienceStateOrdinal =
                savedInstanceState.getInt(MainActivityStateKeys.experienceState)
            state.value = ExperienceStates.values()[experienceStateOrdinal]
        }
    }

    // MARK: Setup Functionality
    private fun setupCoordinators() {
        Log.i(
            identifier,
            "${DebuggingIdentifiers.actionOrEventInProgress} Setting Up Coordinators.",
        )
        NotificationCoordinator.shared.initialize(context = baseContext)
        LanguageCoordinator.shared.initialize(context = baseContext)
        DataCoordinator.shared.initialize(
            context = baseContext,
            onLoad = {
                // Do Something
            },
        )
    }
}
