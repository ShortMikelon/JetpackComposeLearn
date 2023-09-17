package kz.asetkenes.learnandroid.common.androidCore

import kz.asetkenes.learnandroid.common.core.HashCoder
import java.security.MessageDigest

object MessageDigestHashCoder : HashCoder {
    override fun sha1(input: String) = hashString("SHA-1", input)
    override fun md5(input: String) = hashString("MD5", input)

    private fun hashString(type: String, input: String): String {
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}