// ktlint-disable filename
package sh.ld2.example.coordinators.notificationCoordinator

import android.content.Intent
import sh.ld2.example.models.constants.kAppBundleId
import sh.ld2.example.models.notifications.SystemNotifications
import sh.ld2.example.models.notifications.SystemNotificationsExtras
import sh.ld2.example.models.states.ExperienceStates

fun NotificationCoordinator.sendOnLanguageContentUpdateIntent() {
    // Curate Notification
    val intent = Intent(SystemNotifications.onLanguageContentUpdateIntent)
    // Set the package
    intent.setPackage(kAppBundleId)
    // Send Notification - This is found in the declaration file
    sendNotification(intent)
}

fun NotificationCoordinator.sendOnUpdateExperienceIntent(state: ExperienceStates) {
    // Curate Notification
    val intent = Intent(SystemNotifications.onUpdateExperienceStateIntent)
    // Add data (Extras)
    intent.putExtra(
        SystemNotificationsExtras.experienceState,
        state.ordinal,
    )
    // Set the package
    intent.setPackage(kAppBundleId)
    // Send Notification - This is found in the declaration file
    sendNotification(intent)
}

fun NotificationCoordinator.sendSampleIntent() {
    // Curate Notification
    val intent = Intent(SystemNotifications.sampleIntent)
    // Set the package
    intent.setPackage(kAppBundleId)
    // Send Notification - This is found in the declaration file
    sendNotification(intent)
}

fun NotificationCoordinator.sendSampleIntent(extra: String) {
    // Curate Notification
    val intent = Intent(SystemNotifications.sampleIntent)
    // Add data (Extras)
    intent.putExtra(
        SystemNotificationsExtras.sample,
        extra,
    )
    // Set the package
    intent.setPackage(kAppBundleId)
    // Send Notification - This is found in the declaration file
    sendNotification(intent)
}
