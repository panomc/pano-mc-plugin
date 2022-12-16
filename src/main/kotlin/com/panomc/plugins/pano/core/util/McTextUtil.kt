package com.panomc.plugins.pano.core.util

import com.github.pwittchen.kirai.library.Kirai

object McTextUtil {
    fun format(string: String, variables: Map<String, String>? = null): String {
        val formatter = Kirai.from(string)

        variables?.forEach {
            formatter.put(it.key, it.value)
        }

        return translateColor(formatter.format().toString())
    }

    fun translateColor(text: String) = text.replace("&", "§")
}