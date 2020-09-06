package petrov.ivan.gb65apps.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.gb65apps.data.Employee
import petrov.ivan.gb65apps.databinding.ListEmployeeAdapterItemBinding
import java.util.*


class EmployeeAdapter(private val clickListener: EmployeeClickListener) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    var items = ArrayList<Employee>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListEmployeeAdapterItemBinding, val layoutInflater: LayoutInflater) : RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: Employee, clickListener: EmployeeClickListener) {
            binding.apply {
                this.employee = employee
                this.content.setOnClickListener {
                    clickListener.onClick(employee)
                }
//                val btnMap = itemView.findViewById<Button>(R.id.btnMap)
//                btnMap.setOnClickListener { clickListener.onClick(specialty) }
                executePendingBindings()
            }

//            llInvitees.removeAllViews()
//            specialty.listOfInvitees.forEach { person ->
//                val view = layoutInflater.inflate(R.layout.person, null)
//
//                view.tvName.text = person.name
//                view.ivPerson.loadPersonImage(person.imgUri)
//
//                llInvitees.addView(view)
//            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListEmployeeAdapterItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, layoutInflater)
            }
        }
    }

    class EmployeeClickListener(val clickListener: (employee: Employee) -> Unit) {
        fun onClick(employee: Employee) = clickListener(employee)
    }
}
