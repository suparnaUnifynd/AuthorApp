package com.example.authorapp.presentation.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.authorapp.Constants
import com.example.authorapp.R
import com.example.authorapp.data.model.Author
import com.example.authorapp.databinding.ActivityMainBinding
import com.example.authorapp.presentation.adapter.AuthorListingAdapter
import com.example.authorapp.presentation.viewModel.AuthorViewModel
import com.example.authorapp.presentation.viewModel.AuthorViewModelFectory
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener,AuthorListingAdapter.Interaction {
    private lateinit var binding : ActivityMainBinding
    @Inject
    lateinit var factory : AuthorViewModelFectory
    private lateinit var viewModel: AuthorViewModel
    lateinit var authorListingAdapter: AuthorListingAdapter
    private var currentPage = 1
    private var totalPages = 100
    private var isScrollDataLoading = false
    private var gridLayoutManager: GridLayoutManager?=null
    private var authorList: List<Author> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeVariables()
        registerScrollListener()
        setUpListeners()
        setUpAdapters()
    }


    override fun onResume() {
        super.onResume()
        resetListParams()
        getSavedAuthors()
    }
    private fun initializeVariables(){
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(AuthorViewModel::class.java)
        // viewModel.startAuthorUpdates()

    }
    private fun setUpListeners(){
        binding.ivClose.setOnClickListener(this)
    }
    private fun setUpAdapters(){
        gridLayoutManager=GridLayoutManager(
            this,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape mode
            gridLayoutManager!!.spanCount = 6
        } else {
            // Portrait mode
            gridLayoutManager!!.spanCount = 3
        }
        authorListingAdapter= AuthorListingAdapter(interaction = this@MainActivity)
        binding.rvAuthorList.layoutManager = gridLayoutManager
        binding.rvAuthorList.adapter = authorListingAdapter
    }



    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.ivClose ->{
                this.onBackPressed()
            }
        }
    }

    private fun resetListParams(){
        currentPage = 1
        totalPages = 100
        isScrollDataLoading = false
        authorListingAdapter.clearAllData()
    }
    private fun registerScrollListener() {
        binding.rvAuthorList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("MainActivity","AuthorList ** ${currentPage} ")
                Log.d("MainActivity","AuthorList ** ${totalPages} ")
                Log.d("MainActivity","AuthorList ** ${currentPage<totalPages} ")

                if (!binding.rvAuthorList.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    !isScrollDataLoading &&
                    (currentPage < totalPages)
                ) {
                    currentPage++
                    isScrollDataLoading = true
                    viewModel.getAuthors(currentPage)
                }
                //show shadow
                if(gridLayoutManager!!.findFirstCompletelyVisibleItemPosition()==0){
                    binding.ivShadow.visibility=View.GONE
                }else{
                    binding.ivShadow.visibility=View.VISIBLE
                }
            }
        })
    }

    private fun getAuthors() {
        viewModel.getAuthors(currentPage)
        viewModel.authors.observe(this) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        authorList = it
//                        this.totalPages=it.totalPages
                        isScrollDataLoading = false
                        authorListingAdapter.addData(authorList)
                    }
                    Log.d("MainActivity","authorList ** ${totalPages} ")
                    Log.d("MainActivity","authorList ** ${authorList.size} ")
                }
                is NetworkResponse.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        if (it.equals(Constants.NO_INTERNET_MSG)) {
                            Timber.d("Internet not available show local data")
                            getSavedAuthors()
                        }else Timber.d("Error msg: $it")
                    }
                }

                is NetworkResponse.Loading -> {
                    viewModel.getSavedAuthors().observe(this) {
                        if (it.isNullOrEmpty()) {
                            showProgressBar()
                        } else {
                            hideProgressBar()
                            authorListingAdapter.addData(it)
                        }
                    }

                }

                else -> {}
            }
        }
    }

    private fun getSavedAuthors() {
        viewModel.getSavedAuthors().observe(this) {
            it?.let {
                authorList = it
                if(authorList.size==0){
                    getAuthors()
                }else{
                    authorListingAdapter.addData(authorList)
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onAuthorClicked(position: Int, item: Author) {
        val fragment = AuthorFragment(item.downloadUrl)
        fragment.show(supportFragmentManager, "InAppFullScreenWithMarginsFragment")
    }
}