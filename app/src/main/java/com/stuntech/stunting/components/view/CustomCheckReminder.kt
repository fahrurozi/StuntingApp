package com.stuntech.stunting.components.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.stuntech.stunting.R
import com.stuntech.stunting.databinding.CustomCheckReminderBinding
import com.oratakashi.viewbinding.core.tools.onClick

class CustomCheckReminder @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var binding: CustomCheckReminderBinding
    var textDay: String = ""
    var vSelected: Boolean = false
    init {
        binding = CustomCheckReminderBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let {
            val styledAttributes =
                context.obtainStyledAttributes(it, R.styleable.CustomCheckReminder, 0, 0)

            // Init val Ui
            textDay = styledAttributes.getString(R.styleable.CustomCheckReminder_text).toString()

            // Init Ui
            binding.tvDay.text = textDay

            isSelected = vSelected

            binding.root.onClick {
                isSelected = !isSelected
                binding.root.animateClick()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun setSelected(status: Boolean) {
        vSelected = status
        if (!status) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.clRoot.backgroundTintList = resources.getColorStateList(R.color.dark_grey, null)
            } else {
                binding.clRoot.backgroundTintList = resources.getColorStateList(R.color.dark_grey)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.clRoot.backgroundTintList = resources.getColorStateList(R.color.tosca, null)
            } else {
                binding.clRoot.backgroundTintList = resources.getColorStateList(R.color.tosca)
            }
        }
    }

    override fun isSelected(): Boolean {
        return vSelected
    }

    fun setText(text: String) {
        binding.tvDay.text = text
        textDay = text
    }


    fun View.animateClick() {
        val returned: () -> Unit = {
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(150)
                .setInterpolator(FastOutSlowInInterpolator())
                .start()
        }
        animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(150)
            .withEndAction(returned)
            .setInterpolator(FastOutSlowInInterpolator())
            .start()
    }

}