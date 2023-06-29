package com.example.apptestproject.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.MyApp
import com.example.apptestproject.R
import com.example.apptestproject.adapters.DishAdapter
import com.example.apptestproject.adapters.TagAdapter
import com.example.apptestproject.models.Dish
import com.example.apptestproject.viewmodels.DishesViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DishListFragment : Fragment() {
    private lateinit var tagRecyclerView: RecyclerView
    private lateinit var dishRecyclerView: RecyclerView

    @Inject
    lateinit var dishesViewModel: DishesViewModel

    @Inject
    lateinit var picasso: Picasso
    private val args: DishListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dish_list, container, false)
        dishRecyclerView = view.findViewById(R.id.dishesList)
        tagRecyclerView = view.findViewById(R.id.tagsList)
        val categoryNameTextView = view.findViewById<TextView>(R.id.categoryName)
        categoryNameTextView.text = args.categoryName
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dishAdapter = DishAdapter(picasso) { dish ->
            showAddToCartPopup(dish)
        }
        dishRecyclerView.adapter = dishAdapter
        val tagAdapter = TagAdapter { tag ->
            val filteredDishes = dishesViewModel.filterDishesByTag(tag)
            dishAdapter.updateDishes(filteredDishes)
        }
        tagRecyclerView.adapter = tagAdapter
        dishesViewModel.tagsLiveData.observe(viewLifecycleOwner) {
            tagAdapter.updateTags(it)
        }

        dishesViewModel.dishesLiveData.observe(viewLifecycleOwner) {
            dishAdapter.updateDishes(it)
        }
        dishesViewModel.fetchDishes()

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            val action = DishListFragmentDirections.actionDishesToHome()
            findNavController().navigate(action)
        }
    }


    private fun showAddToCartPopup(dish: Dish) {
        val addToCartDialog = AddToCartDialog(picasso, requireContext(), dish) {
            dishesViewModel.addToCart(dish)
        }
        addToCartDialog.show()
    }
}