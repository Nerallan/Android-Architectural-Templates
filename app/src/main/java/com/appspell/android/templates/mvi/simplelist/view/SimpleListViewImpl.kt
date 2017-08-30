package com.appspell.android.templates.mvi.list.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.appspell.android.templates.R

class SimpleListViewImpl(rootView: View) : SimpleListView {
    private var progress: ProgressBar = rootView.findViewById(R.id.progress) as ProgressBar
    private val list: RecyclerView = rootView.findViewById(R.id.list) as RecyclerView

    private lateinit var adapterSimple: SimpleListAdapter

    override fun initViews(listener: OnSimpleListItemClick) {
        list.layoutManager = LinearLayoutManager(list.context, LinearLayoutManager.VERTICAL, false)
        adapterSimple = SimpleListAdapter(listener)
        list.adapter = adapterSimple
    }

    override fun updateList(list: List<String>) {
        if (list.isEmpty()) {
            return
        }
        Log.d("TAG", list.toString())
        adapterSimple.updateList(list)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
        list.visibility = View.VISIBLE
    }
}