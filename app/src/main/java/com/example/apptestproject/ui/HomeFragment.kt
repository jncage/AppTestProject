package com.example.apptestproject.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apptestproject.MyApp
import com.example.apptestproject.R
import com.example.apptestproject.location.LocationHelper
import com.example.apptestproject.models.Category
import com.example.apptestproject.utils.DateUtil
import com.example.apptestproject.viewmodels.CategoryViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    private lateinit var cityNameTextView: TextView

    @Inject
    lateinit var categoryViewModel: CategoryViewModel

    @Inject
    lateinit var locationHelper: LocationHelper
    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                locationHelper.fetchCityName()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val currentDate = view.findViewById<TextView>(R.id.currentDateTextView)
        cityNameTextView = view.findViewById(R.id.cityTextView)
        currentDate.text = DateUtil.getCurrentDate()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel.categoriesLiveData.observe(viewLifecycleOwner) { categories ->
            updateCategories(categories)
        }
        categoryViewModel.fetchCategories()

        if (hasLocationPermission()) {
            locationHelper.fetchCityName()
        } else {
            requestLocationPermission()
        }


    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestLocationPermissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onResume() {
        super.onResume()
        locationHelper.getCityName().observe(viewLifecycleOwner) {
            cityNameTextView.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requestLocationPermissionLauncher.unregister()
    }

    private fun updateCategories(categories: List<Category>) {
        categories.forEachIndexed { index, category ->
            val categoryButtons = resources.obtainTypedArray(R.array.category_buttons)
            val buttonId = categoryButtons.getResourceId(index, 0)
            categoryButtons.recycle()
            val categoryButton = requireView().findViewById<RelativeLayout>(buttonId)
            categoryButton.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeToDishes(category.name)
                findNavController().navigate(action)
            }
            val backgroundImage =
                categoryButton.findViewById<ImageView>(R.id.backgroundImage)
            backgroundImage.clipToOutline = true
            val buttonName = categoryButton.findViewById<TextView>(R.id.buttonName)
            buttonName.text = category.name
            picasso.load(category.imageUrl).into(backgroundImage)
        }
    }
}
