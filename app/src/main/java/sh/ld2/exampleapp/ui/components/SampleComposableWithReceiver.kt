package sh.ld2.example.ui.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import sh.ld2.example.models.constants.DebuggingIdentifiers
import sh.ld2.example.models.notifications.SystemNotifications
import sh.ld2.example.models.notifications.SystemNotificationsExtras
import sh.ld2.example.ui.styleguide.HeaderText

@Composable
fun SampleComposableWithReceiver(name: String, modifier: Modifier) {
    // MARK: Variables
    val identifier: String = "[Comp.WithReceiver]"
    val context = LocalContext.current
    // MARK: Visual
    HeaderText(
        copy = "BroadCast Reciever : $name!",
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
    )
    // MARK: BROADCAST / Notifications
    DisposableEffect(context, effect = {
        // Declare Intents and Reciever
        val broadCast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(
                    "$identifier",
                    "${DebuggingIdentifiers.actionOrEventSucceded} ${DebuggingIdentifiers.notificationRecieved}  $intent | ${intent?.extras}",
                )
                val action = intent?.action ?: return
                when (action) {
                    SystemNotifications.sampleIntent -> {
                        Log.i(
                            identifier,
                            "${DebuggingIdentifiers.actionOrEventSucceded} ${DebuggingIdentifiers.notificationRecieved} sampleIntent.",
                        )
                        // Check for Extras
                        val extras = intent.extras
                        if (extras != null) {
                            // There are extras!
                            val extra = extras.getInt(SystemNotificationsExtras.sample)
                            Log.i(
                                identifier,
                                "${DebuggingIdentifiers.actionOrEventSucceded} onSampleIntent extra: $extra.",
                            )
                            // Do Something
                        } else {
                            // There are no extras
                        }
                        Log.i(
                            identifier,
                            "${DebuggingIdentifiers.actionOrEventSucceded} onSampleIntent Sequence Complete",
                        )
                    }
                }
            }
        }
        // Register Intents
        context.registerReceiver(
            broadCast,
            IntentFilter(SystemNotifications.sampleIntent),
            RECEIVER_NOT_EXPORTED,
        )
        // Dispose
        onDispose {
            context.unregisterReceiver(broadCast)
        }
    })
}
