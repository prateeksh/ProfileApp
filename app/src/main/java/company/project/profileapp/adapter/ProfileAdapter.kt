package company.project.profileapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import company.project.profileapp.R
import company.project.profileapp.model.ExpandableUserModel
import company.project.profileapp.model.User


class ProfileAdapter (var context: Context, private val userList: MutableList<ExpandableUserModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {

        val viewHolder: RecyclerView.ViewHolder =  when(viewType){

            ExpandableUserModel.PARENT -> ProfileParentViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.user_detail_view, parent, false))

            ExpandableUserModel.CHILD -> ProfileChildViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.user_detail_view_child, parent, false))

            else -> ProfileParentViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.user_detail_view, parent, false))
        }

        return viewHolder

    }

    override fun getItemViewType(position: Int): Int {
        return userList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val row = userList[position]
        when(row.type){

            ExpandableUserModel.PARENT -> {
                (holder as ProfileParentViewHolder).name.text = row.userParent.firstname
                holder.lastName.text = row.userParent.lastname
                holder.age.text = row.userParent.age
                holder.gender.text = row.userParent.gender
                val image = row.userParent.picture
                if (image != null){

                    Glide
                        .with(holder.itemView.context)
                        .load(image)
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.drawable.user)
                        .into(holder.image)
                }

                holder.openArrow.setOnClickListener{

                    if (row.isExpanded){
                        row.isExpanded = false
                        collapseRow(position)
                    }else{
                        row.isExpanded = true
                        holder.openArrow.visibility = View.GONE
                        holder.closeArrow.visibility = View.VISIBLE
                        expandRow(position)
                    }
                }

                holder.closeArrow.setOnClickListener {
                    if (row.isExpanded){
                        row.isExpanded = false
                        collapseRow(position)
                        holder.openArrow.visibility = View.VISIBLE
                        holder.closeArrow.visibility = View.GONE
                    }
                }
            }

            ExpandableUserModel.CHILD -> {

                (holder as ProfileChildViewHolder).role.text = row.userChildJob?.role

                if (row.userChildJob?.organization == null && row.userChildJob?.role== null) {
                    holder.org.visibility = View.GONE
                    holder.role.visibility = View.GONE
                    holder.expView.visibility = View.GONE
                    holder.expTextView.visibility = View.GONE
                }
                if (row.userChildEdu?.institution == null && row.userChildEdu?.degree == null){
                    holder.institute.visibility = View.GONE
                    holder.degree.visibility = View.GONE
                    holder.eduTextView.visibility = View.GONE
                    holder.eduView.visibility = View.GONE
                }

                holder.exp.text = row.userChildJob?.exp.toString()
                holder.org.text = row.userChildJob?.organization
                holder.institute.text = row.userChildEdu?.institution
                holder.degree.text = row.userChildEdu?.degree

            }
        }
    }

    private fun expandRow(position: Int){
        val row = userList[position]
        var nexPosition = position
        when(row.type){

            ExpandableUserModel.PARENT -> {
                for (child in row.userParent.job){
                    userList.add(++nexPosition, ExpandableUserModel(ExpandableUserModel.CHILD, child))
                }
                for (child in row.userParent.education){
                    userList.add(++nexPosition, ExpandableUserModel(ExpandableUserModel.CHILD, child))
                }

                notifyDataSetChanged()
            }

            ExpandableUserModel.CHILD -> {
                notifyDataSetChanged()
            }
        }
    }

    private fun collapseRow(position: Int){
        val row = userList[position]
        var nextPosition = position + 1
        when (row.type) {
            ExpandableUserModel.PARENT -> {
                outerloop@ while (true) {
                    //  println("Next Position during Collapse $nextPosition size is ${shelfModelList.size} and parent is ${shelfModelList[nextPosition].type}")

                    if (nextPosition == userList.size || userList[nextPosition].type == ExpandableUserModel.PARENT) {
                        /* println("Inside break $nextPosition and size is ${closedShelfModelList.size}")
                         closedShelfModelList[closedShelfModelList.size-1].isExpanded = false
                         println("Modified closedShelfModelList ${closedShelfModelList.size}")*/
                        break@outerloop
                    }

                    userList.removeAt(nextPosition)
                }

                notifyDataSetChanged()
            }


        }
    }

    override fun getItemCount(): Int {

        return userList.size
    }

    inner class ProfileParentViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){


        val name: TextView = itemView.findViewById(R.id.name)
        val lastName: TextView = itemView.findViewById(R.id.last_name)
        val gender: TextView = itemView.findViewById(R.id.gender)
        val age: TextView = itemView.findViewById(R.id.age)
        val image: ImageView = itemView.findViewById(R.id.profile_pic)
        internal var closeArrow = itemView.findViewById<ImageButton>(R.id.close_arrow)
        internal var openArrow = itemView.findViewById<ImageButton>(R.id.open_arrow)

    }

    inner class ProfileChildViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){

        val role: TextView = itemView.findViewById(R.id.job_role)
        val exp: TextView = itemView.findViewById(R.id.experience)
        val org: TextView = itemView.findViewById(R.id.company_name)
        val degree: TextView = itemView.findViewById(R.id.degree)
        val institute: TextView = itemView.findViewById(R.id.institute)
        val year: TextView = itemView.findViewById(R.id.years)
        val expTextView: TextView = itemView.findViewById(R.id.exp_text)
        val expView: View = itemView.findViewById(R.id.exp_view)
        val eduTextView: TextView = itemView.findViewById(R.id.edu_text)
        val eduView: View = itemView.findViewById(R.id.edu_view)
    }

}