package kz.asetkenes.learnandroid.common.core

interface HashCoder {

    fun sha1(input: String): String

    fun md5(input: String): String
}