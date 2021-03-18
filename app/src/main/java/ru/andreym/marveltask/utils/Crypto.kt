package ru.andreym.marveltask.utils

import java.security.MessageDigest

fun ByteArray.toHexString()=this.joinToString(""){ String.format("%02x",(it.toInt() and 0xFF)) }
fun String.toMD5() =
    MessageDigest.getInstance("MD5").digest(toByteArray()).toHexString()