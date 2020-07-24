package com.appspell.android.templates.mvvm.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appspell.android.templates.mvvm.base.doOnNext

abstract class MvvmListViewModel : ViewModel() {
    abstract val items: LiveData<List<Item>?>
    abstract val error: LiveData<String?>
    abstract val showLoader: LiveData<Boolean?>

    abstract val openScreenEvent: LiveData<Int?>

    abstract val result: LiveData<Result>
}

class MvvmListViewModelImpl(private val repository: MvvmListViewRepository) : MvvmListViewModel() {

    override val items = MutableLiveData<List<Item>?>()
    override val error = MutableLiveData<String?>()
    override val showLoader = MutableLiveData<Boolean?>()

    override val openScreenEvent = MutableLiveData<Int?>()

    override val result = repository.result.doOnNext { result -> handleResult(result) }

    init {
        Log.i(DEBUG_LOG_TAG, "ViewModel.init")
        repository.fetch()

        showLoader.value = true
    }

    private fun handleResult(result: Result) {
        Log.e(DEBUG_LOG_TAG, "ViewModel.handleResult-${repository}")

        items.value = result.list
        error.value = result.error?.message

        showLoader.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(DEBUG_LOG_TAG, "ViewModel.onCleared-${repository}")
        repository.release()
    }
}