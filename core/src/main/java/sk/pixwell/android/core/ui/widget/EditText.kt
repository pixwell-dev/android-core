package sk.pixwell.android.core.ui.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChanged(
    before: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    on: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    after: ((Editable?) -> Unit)? = null
) = addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        before?.invoke(p0, p1, p2, p3)
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        on?.invoke(p0, p1, p2, p3)
    }

    override fun afterTextChanged(p0: Editable?) {
        after?.invoke(p0)
    }
})