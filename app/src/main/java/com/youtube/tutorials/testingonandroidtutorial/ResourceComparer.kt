package com.youtube.tutorials.testingonandroidtutorial

import android.content.Context

class ResourceComparer {
    fun isEqual(context: Context, resourceId: Int, string: String): Boolean = context.getString(resourceId) == string
}