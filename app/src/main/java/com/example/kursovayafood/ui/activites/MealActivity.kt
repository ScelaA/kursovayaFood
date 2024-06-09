package com.example.kursovayafood.ui.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kursovayafood.R

import com.example.kursovayafood.data.pojo.Meal
import com.example.kursovayafood.databinding.ActivityCategoriesBinding
import com.example.kursovayafood.mvvm.MealActivityMVVM
import com.example.kursovayafood.ui.fragments.HomeFragment
import com.example.kursovayafood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.kursovayafood.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.example.kursovayafood.ui.fragments.HomeFragment.Companion.MEAL_STR
import com.example.kursovayafood.ui.fragments.HomeFragment.Companion.MEAL_THUMB

class MealActivity : AppCompatActivity() {
    private lateinit var mealActivityMvvm: MealActivityMVVM
    private lateinit var binding: ActivityCategoriesBinding
    private var categoryNme = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealActivityMvvm = ViewModelProviders.of(this)[MealActivityMVVM::class.java]
        startLoading()
        prepareRecyclerView()
        mealActivityMvvm.getMealsByCategory(getCategory())
        mealActivityMvvm.observeMeal().observe(this, object : Observer<List<Meal>> {
            override fun onChanged(t: List<Meal>?) {
                if(t==null){
                    hideLoading()
                    Toast.makeText(applicationContext, "No meals in this category", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }else {
                    binding.tvCategoryCount.text = categoryNme + " : " + t.size.toString()
                    hideLoading()
                }
            }
        })


    }

    private fun hideLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.INVISIBLE
            mealRoot.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }    }

    private fun startLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.VISIBLE
            mealRoot.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.g_loading))
        }
    }

    private fun getCategory(): String {
        val tempIntent = intent
        val x = intent.getStringExtra(CATEGORY_NAME)!!
        categoryNme = x
        return x
    }

    private fun prepareRecyclerView() {
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}