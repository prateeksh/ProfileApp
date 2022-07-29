package company.project.profileapp.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import company.project.profileapp.R
import company.project.profileapp.Utils.NetworkUtils
import company.project.profileapp.adapter.ProfileAdapter
import company.project.profileapp.model.ExpandableUserModel
import company.project.profileapp.viewmodel.ProfileViewModel
import company.project.profileapp.viewmodel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    lateinit var adapter: ProfileAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        swipeRefreshLayout = findViewById(R.id.main_refresh_layout)
        progressBar = findViewById(R.id.main_progress_bar)

        val repository = (application as ProfileApplication).repository

        profileViewModel =
            ViewModelProvider(
                this,
                ProfileViewModelFactory(repository)
            ).get(ProfileViewModel::class.java)

        progressBar.isVisible = true
        recyclerView.visibility = View.GONE

            swipeRefreshLayout.setOnRefreshListener {
                if (NetworkUtils.isInternetAvailable(this)) {
                    profileViewModel.init()
                    observeViewModelResponse()
                    swipeRefreshLayout.isRefreshing = false
                } else {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, resources.getText(R.string.no_internet), Toast.LENGTH_LONG)
                        .show()
                }
            }


        if (NetworkUtils.isInternetAvailable(this)) {
            progressBar.isVisible = true
            profileViewModel.init()
            observeViewModelResponse()

        } else {
            progressBar.visibility = View.GONE
            profileViewModel.init()
            observeViewModelResponse()
            Toast.makeText(this, resources.getText(R.string.no_internet), Toast.LENGTH_LONG)
                .show()

        }

    }

    private fun observeViewModelResponse() {

        recyclerView.visibility = View.VISIBLE
        profileViewModel.user!!.observe(this, Observer {
            progressBar.isVisible = false
            var userInfo =
                profileViewModel.prepareDataForExpandableAdapter(profileViewModel.user!!.value!!)
            populateAdapter(userInfo)
        })

    }

    private fun populateAdapter(expandableUserList: MutableList<ExpandableUserModel>) {
        adapter = ProfileAdapter(this, expandableUserList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }
}