package com.example.apptestproject.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.MyApp
import com.example.apptestproject.R
import com.example.apptestproject.adapters.CartAdapter
import com.example.apptestproject.utils.DateUtil
import com.example.apptestproject.viewmodels.CartViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartFragment : Fragment() {
    @Inject
    lateinit var cartViewModel: CartViewModel

    @Inject
    lateinit var picasso: Picasso

    private lateinit var cityNameTextView: TextView

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalPrice: TextView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        cityNameTextView = view.findViewById(R.id.cityNameCart)
        val cityName = sharedPreferences.getString("cityName", "")
        cityNameTextView.text = cityName
        val currentDateTextView = view.findViewById<TextView>(R.id.currentDateCart)
        currentDateTextView.text = DateUtil.getCurrentDate()
        val cartRecyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        totalPrice = view.findViewById(R.id.pay)
        cartAdapter =
            CartAdapter(picasso, emptyList(), onPlusClick = { cartViewModel.addToCart(it) },
                onMinusClick = { cartViewModel.removeFromCart(it) })
        cartRecyclerView.adapter = cartAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.cartData.observe(viewLifecycleOwner) {
            cartAdapter.updateCartItems(it.cartItems)
            totalPrice.text = getString(R.string.pay, getString(R.string.price, it.totalPrice))
        }
        cartViewModel.updateCartData()

    }

    override fun onResume() {
        super.onResume()
        cityNameTextView.text = sharedPreferences.getString("cityName", "")

    }
}