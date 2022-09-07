package com.stuntech.stunting.ui.reminderv2

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stuntech.stunting.data.db.model.reminder.DataReminder
import com.stuntech.stunting.databinding.ListReminderv2Binding
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding


class ReminderV2Adapter(
    val onUpdate: (DataReminder) -> Unit,
    val onLongClick: (DataReminder) -> Unit
) : RecyclerView.Adapter<ViewHolder<ListReminderv2Binding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ListReminderv2Binding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ListReminderv2Binding>, position: Int) {
        val dataReminder = data[position]
        with(holder.binding) {
            tvRTime.text = dataReminder.clock
            tvNote.text = dataReminder.note

            val repeat =
                (if (dataReminder.sunday) "Sun " else "") +
                (if (dataReminder.monday) "Mon " else "") +
                (if (dataReminder.tuesday) "Thu " else "") +
                (if (dataReminder.wednesday) "Wed " else "") +
                (if (dataReminder.thursday) "Thu " else "") +
                (if (dataReminder.friday) "Fri " else "") +
                if (dataReminder.saturday) "Sat " else ""
            tvRepeat.text = repeat
            sReminder.isChecked = dataReminder.status

            sReminder.setOnCheckedChangeListener { _, b ->
                onUpdate.invoke(dataReminder.also { it.status = b })
            }
            holder.binding.root.setOnLongClickListener {
                onLongClick.invoke(dataReminder)
                true
            }
        }
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<DataReminder>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }


    fun getList(): List<DataReminder> {
        return data
    }

    private val data: MutableList<DataReminder> = ArrayList()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}