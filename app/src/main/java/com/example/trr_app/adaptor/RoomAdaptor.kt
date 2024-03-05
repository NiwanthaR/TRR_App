package com.example.trr_app.adaptor

import android.content.Context
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.holders.RoomViewHolder
import com.example.trr_app.holders.UserViewHolder
import com.example.trr_app.model.Room
import com.example.trr_app.model.RoomBookingDetails
import com.example.trr_app.model.RoomBookingList
import com.example.trr_app.model.RoomDetails
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.support.QuickBookingEdite

class RoomAdaptor(val rooms:ArrayList<Room>, val context: Context,val alreadyBookedList:ArrayList<String>,val bookingDetails: RoomBookingDetails,var showBookingBtn:(Boolean) ->Unit ): RecyclerView.Adapter<RoomViewHolder>() {

    private var isEnable = false
    private val selectRoomList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(LayoutInflater.from(context).inflate(R.layout.single_room_card, parent, false))
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = rooms[position]
        holder.roomName.text = item.room_name
        holder.roomType.text = item.room_condition
        holder.roomCapacity.text =item.room_capacity
        holder.roomCost.text =item.room_cost

        holder.navigateArrow.visibility = View.VISIBLE
        holder.doneIcon.visibility = View.GONE

        if (alreadyBookedList.size>0 && item.room_unic_code=="R007"){
            holder.itemView.visibility = View.GONE
        }

        if (alreadyBookedList.contains(item.room_unic_code)){
            holder.alreadyLayout.visibility = View.VISIBLE
            holder.roomLayout.isActivated = false
            holder.roomLayout.isSelected = false

            //open booking
            holder.alreadyLayout.setOnClickListener(View.OnClickListener {
                   holder.bottomSheet = QuickBookingEdite(bookingDetails.getBookedIdForRoom(item.room_unic_code),2)
                   val fragmentManager = (context as FragmentActivity).supportFragmentManager
                   holder.bottomSheet.show(fragmentManager, "ModalBottomSheet")
            })

        }else{

            holder.roomLayout.setOnLongClickListener(View.OnLongClickListener {
                selectItem(holder,item,position)
                true
            })

            holder.roomLayout.setOnClickListener(View.OnClickListener {
                if (selectRoomList.contains(position)){
                    selectRoomList.remove(position)

                    holder.navigateArrow.visibility = View.VISIBLE
                    holder.doneIcon.visibility = View.GONE

                    item.isSelected = false

                    if (selectRoomList.isEmpty()){
                        isEnable = false
                        showBookingBtn(false)
                    }
                }else if (isEnable){
                    selectItem(holder,item,position)
                }
            })

        }


    }

    private fun selectItem(holder:RoomViewHolder,item:Room,position: Int){
        isEnable = true
        selectRoomList.add(position)
        item.isSelected=true

        //icon
        holder.navigateArrow.visibility = View.GONE
        holder.doneIcon.visibility = View.VISIBLE

        //show
        showBookingBtn(true)
    }

    fun getSelectedList():ArrayList<Room>{
        var selectRooms : ArrayList<Room> = ArrayList()
        return if (selectRoomList.isNotEmpty()){
            selectRooms.clear()
            val count = rooms.size
            for (i in 0..<count){
                if (rooms[i].isSelected==true){
                    //val x = selectRoomList.get(i)
                    selectRooms.add(rooms[i])
                }
            }
            println("got selected list")
            return selectRooms
        }else{
            println(" selected list is empty")
            return selectRooms
        }
    }
}