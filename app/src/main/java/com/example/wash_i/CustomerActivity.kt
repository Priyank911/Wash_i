package com.example.wash_i

import ServiceProvider
import ShopAdapter
import UserSchema
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var shopAdapter: ShopAdapter
    private val shopList = mutableListOf<ServiceProvider>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        recyclerView = findViewById(R.id.recyclerView)
        database = FirebaseDatabase.getInstance().getReference("users")
        shopAdapter = ShopAdapter(shopList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = shopAdapter

        fetchShops()
    }

    private fun fetchShops() {
        database.orderByChild("userType").equalTo("ServiceProvider")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    shopList.clear()
                    for (shopSnapshot in snapshot.children) {
                        val userSchema = shopSnapshot.getValue(UserSchema::class.java)
                        userSchema?.serviceProvider?.let { shopList.add(it) }
                    }
                    shopAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}
