// ktlint-disable filename
package sh.ld2.example.coordinators.languageCoordinator

import android.util.Log
import sh.ld2.example.coordinators.languageCoordinator.LanguageCoordinator.Companion.identifier
import sh.ld2.example.coordinators.notificationCoordinator.NotificationCoordinator
import sh.ld2.example.coordinators.notificationCoordinator.sendOnLanguageContentUpdateIntent
import sh.ld2.example.models.constants.DebuggingIdentifiers
import sh.ld2.example.models.languageContent.Languages
import java.util.Locale

fun LanguageCoordinator.updateCurrentContent() {
    // Get the current system language
    systemLanguage = Locale.getDefault().language
    Log.i(
        identifier,
        "${DebuggingIdentifiers.actionOrEventInProgress} updateCurrentContent language : $systemLanguage  ${DebuggingIdentifiers.actionOrEventInProgress}.",
    )
    // Set the Current Language
    currentLanguage = when (systemLanguage) {
        // Spanish US or Spain
        "es" -> Languages.SPANISH
        // Euskara
        "eu" -> Languages.SPANISH
        // Gallego
        "gl" -> Languages.SPANISH
        // Catalan
        "ca" -> Languages.SPANISH
        // Anything else, in english
        else -> Languages.ENGLISH
    }
    // Get the current UI Content
    val content = getContent(currentLanguage)
    // Make Sure Content Exists
    if (content == null) {
        Log.i(
            identifier,
            "updateCurrentContent  ${DebuggingIdentifiers.actionOrEventFailed} Could not gather $currentLanguage file. Check that the context exists.",
        )
        return
    }
    // Set Content
    Log.i(
        identifier,
        "updateCurrentContent  ${DebuggingIdentifiers.actionOrEventSucceded} Set content to $currentLanguage.",
    )
    currentContent = content
    // Send Notification
    NotificationCoordinator.shared.sendOnLanguageContentUpdateIntent()
}
