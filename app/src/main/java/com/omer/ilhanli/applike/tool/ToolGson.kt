package com.omer.ilhanli.applike.tool

import android.content.Context
import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.Charset

object ToolGson {

    infix fun Context.jsonFrom(filePath: String): String? {
        try {
            val source = assets.open(filePath).source().buffer()
            return source.readByteString().string(Charset.forName("utf-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}
