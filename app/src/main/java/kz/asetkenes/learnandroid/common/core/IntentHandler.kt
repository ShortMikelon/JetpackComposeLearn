package kz.asetkenes.learnandroid.common.core

interface IntentHandler<UiIntent>  {
    fun obtainIntent(intent: UiIntent)
}