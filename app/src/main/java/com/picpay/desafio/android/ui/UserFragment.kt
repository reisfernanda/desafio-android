package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.commons.Status
import com.picpay.desafio.android.setVisible
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment: Fragment() {

    private val viewModel: UserListViewModel by viewModel()
    private var adapter: UserListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner, {
            adapter?.users = it
        })
        viewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                Status.SUCCESS -> showUsersList()
                Status.LOADING -> showLoading()
                Status.EMPTY -> showError(getString(R.string.empty))
                Status.FAILURE -> showError(getString(R.string.error))
                else -> showError(getString(R.string.error))
            }
        })
    }

    private fun showUsersList() {
        recyclerView.setVisible(true)
        user_list_progress_bar.setVisible(false)
        errorMessage.setVisible(false)
    }

    private fun showLoading() {
        recyclerView.setVisible(false)
        user_list_progress_bar.setVisible(true)
        errorMessage.setVisible(false)
    }

    private fun showError(message: String) {
        recyclerView.setVisible(false)
        user_list_progress_bar.setVisible(false)
        errorMessage.setVisible(true)
        errorMessage.text = message
    }
}