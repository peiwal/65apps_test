package petrov.ivan.gb65apps.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.databinding.ListSpecialityAdapterItemBinding
import java.util.*


class SpecialityAdapter(private val clickListener: SpecialityClickListener) : RecyclerView.Adapter<SpecialityAdapter.ViewHolder>() {
    var items = ArrayList<Speciality>()
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

    class ViewHolder private constructor(val binding: ListSpecialityAdapterItemBinding, val layoutInflater: LayoutInflater) : RecyclerView.ViewHolder(binding.root) {

        fun bind(speciality: Speciality, clickListener: SpecialityClickListener) {
            binding.apply {
                this.speciality = speciality
                this.content.setOnClickListener {
                    clickListener.onClick(speciality)
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
                val binding = ListSpecialityAdapterItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, layoutInflater)
            }
        }
    }

    class SpecialityClickListener(val clickListener: (speciality: Speciality) -> Unit) {
        fun onClick(speciality: Speciality) = clickListener(speciality)
    }
}
