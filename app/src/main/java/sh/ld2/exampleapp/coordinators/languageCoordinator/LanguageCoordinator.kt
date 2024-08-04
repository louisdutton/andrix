package sh.ld2.example.coordinators.languageCoordinator

import android.content.Context
import android.util.Log
import sh.ld2.example.models.constants.DebuggingIdentifiers
import sh.ld2.example.models.languageContent.Languages
import sh.ld2.example.models.languageContent.UIContent
import java.util.Locale

class LanguageCoordinator {
    // MARK: Variables
    companion object {
        val shared = LanguageCoordinator()
        const val identifier = "[LanguageCoordinator]"
    }
    var systemLanguage: String = Locale.getDefault().language
    var currentLanguage: Languages = Languages.ENGLISH
    var context: Context? = null
    var currentContent: UIContent? = null

    // MARK: Lifecycle
    fun initialize(context: Context) {
        Log.i(
            identifier,
            "${DebuggingIdentifiers.actionOrEventInProgress} initialize  ${DebuggingIdentifiers.actionOrEventInProgress}.",
        )
        this.context = context
        updateCurrentContent()
    }
}
