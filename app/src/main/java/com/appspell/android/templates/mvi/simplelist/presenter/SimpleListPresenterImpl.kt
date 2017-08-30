package com.appspell.android.templates.mvi.list.presenter

import com.appspell.android.templates.mvi.list.model.SimpleListInteractor
import com.appspell.android.templates.mvi.list.model.SimpleListViewState
import com.appspell.android.templates.mvi.list.router.SimpleListRouter
import com.appspell.android.templates.mvi.list.view.OnSimpleListItemClick
import com.appspell.android.templates.mvi.list.view.SimpleListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class SimpleListPresenterImpl(
        val viewSimple: SimpleListView,
        val interactorSimple: SimpleListInteractor,
        val routerSimple: SimpleListRouter
) : SimpleListPresenter, OnSimpleListItemClick {

    lateinit var disposable: Disposable

    override fun create() {
        viewSimple.initViews(this)
    }

    override fun bind() {
        disposable = interactorSimple.requestList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewStateSimple: SimpleListViewState -> updateUI(viewStateSimple) }
    }

    override fun unBind() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun updateUI(viewStateSimple: SimpleListViewState) {
        if (viewStateSimple.loading) {
            viewSimple.showProgress()
        } else {
            viewSimple.hideProgress()
        }
        viewSimple.updateList(viewStateSimple.list)
    }

    override fun onItemClicked() {
        routerSimple.openCatalogItem()
    }
}

