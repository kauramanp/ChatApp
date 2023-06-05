package com.aman.chatapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.aman.chatapp.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var mDatabase: DatabaseReference? = null
    private var userRef: DatabaseReference? = null
    private var customerRef: DatabaseReference? = null
    private var orderStatusRef: DatabaseReference? = null
    private var orderIdStatusRef: DatabaseReference? = null
    var valueEventListener: ValueEventListener? = null
    lateinit var binding: FragmentSecondBinding
    lateinit var refId: String
    lateinit var name: String
    private var userType: Int = 0
    private val TAG = "SecondFragment"
    lateinit var messageAdapter: MessageAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        refId = SecondFragmentArgs.fromBundle(requireArguments()).refId
        name = SecondFragmentArgs.fromBundle(requireArguments()).name!!
        userType = SecondFragmentArgs.fromBundle(requireArguments()).userType
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDatabase = FirebaseDatabase.getInstance().getReference("chat")
        userRef = mDatabase!!.child(refId)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        messageAdapter = MessageAdapter(requireContext())
        binding.recyclerView.adapter = messageAdapter
        startListeners()
        Log.e(TAG, "userRef $userRef")
        binding.btnSend.setOnClickListener {
            lateinit var type: String
            var firebaseChat: FirebaseChat = FirebaseChat()

            when (userType) {
                UserType.CLIENT.type -> type = UserType.CLIENT.toString()
                UserType.ADMIN.type -> type = UserType.ADMIN.toString()
                UserType.ASTROLOGER.type -> {
                    type = UserType.ASTROLOGER.toString()
                    firebaseChat.name = name
                }
            }
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd-HH:mm:ss"
            )
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val currentDate = dateFormat.format(Date())
            firebaseChat.date = currentDate
            firebaseChat.msg = binding.etMessage.text.toString().trim()
            firebaseChat.type = type

            val key = userRef!!.push().key
            userRef!!.child(key!!).setValue(firebaseChat)
            binding.etMessage.text.clear()
        }
    }

    private fun startListeners() {
        valueEventListener = userRef!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(dsp in dataSnapshot.children){
                        var firebaseChat: FirebaseChat  = dsp.getValue(FirebaseChat::class.java)!!
                        Log.e(TAG, "firebase chat ${firebaseChat.msg}")
                        messageAdapter.addMessage(firebaseChat)
                        messageAdapter.notifyDataSetChanged()
                        binding.recyclerView.scrollToPosition(messageAdapter.itemCount - 1);
                    }
                }

            }

        })
        userRef!!.addValueEventListener(valueEventListener!!)

    }
}