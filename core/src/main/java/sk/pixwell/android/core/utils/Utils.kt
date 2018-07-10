package sk.pixwell.android.core.utils

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}