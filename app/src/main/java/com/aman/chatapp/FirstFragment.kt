package com.aman.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.aman.chatapp.databinding.FragmentFirstBinding
import java.lang.String


class FirstFragment : Fragment(), DialogInterface.ChatClicked, DialogInterface {
    lateinit var optionsDialog: OptionsDialog

    private var mDatabase: DatabaseReference? = null
    private var userRef: DatabaseReference? = null
    var valueEventListener: ValueEventListener? = null
    lateinit var recyclerAdapterChatList: RecyclerAdapterChatList
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var binding: FragmentFirstBinding
    private val TAG = FirstFragment::class.java.canonicalName
    private var refNode: ArrayList<kotlin.String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerAdapterChatList = RecyclerAdapterChatList(requireContext(), refNode, this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = recyclerAdapterChatList
        optionsDialog = OptionsDialog(requireActivity(), this)

        startListener()
    }

    private fun startListener() {
        mDatabase = FirebaseDatabase.getInstance().getReference("chat")
        valueEventListener = mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var firebaseChat: FirebaseChat = FirebaseChat()
                if (dataSnapshot.exists()) {
                    refNode.clear()
                    for (dsp in dataSnapshot.children) {
                        refNode.add(dsp.key!!) //add result into array list
                    }
                    recyclerAdapterChatList.notifyDataSetChanged()
                }

            }

        })
        mDatabase!!.addValueEventListener(valueEventListener!!)
    }

    override fun onChatItemClicked(name: kotlin.String) {
        optionsDialog.setRefId(name)
        optionsDialog.show()

    }

    override fun dialogOk(userType: Int, name: kotlin.String, refId: kotlin.String?) {
        optionsDialog.dismiss()
        val bundle = Bundle()
        bundle.putInt("userType", userType)
        bundle.putString("refId", refId)
        bundle.putString("name", name)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun dialogCancel() {
        optionsDialog.dismiss()
    }


}