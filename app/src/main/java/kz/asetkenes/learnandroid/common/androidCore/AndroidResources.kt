package kz.asetkenes.learnandroid.common.androidCore

import android.content.Context
import kz.asetkenes.learnandroid.common.core.Resources

class AndroidResources(private val context: Context) : Resources {

    override fun getString(id: Int): String =
        context.getString(id)

    override fun getString(id: Int, vararg args: Any): String =
        context.getString(id, args)

}