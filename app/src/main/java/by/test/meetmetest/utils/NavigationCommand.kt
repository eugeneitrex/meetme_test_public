package by.test.meetmetest.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToId(@IdRes val navigationId : Int, val bundle: Bundle? = null): NavigationCommand()
    data class To(val directions: NavDirections): NavigationCommand()
}
