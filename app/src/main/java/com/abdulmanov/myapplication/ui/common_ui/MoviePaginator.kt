package com.abdulmanov.myapplication.ui.common_ui

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

class MoviePaginator<VO,DTO>(
    private val viewController: ViewControl<VO>,
    private val mapper: Function<DTO, List<VO>>,
    var requestFactory:(page:Int)->Observable<DTO>
) {

    private val FIRST_PAGE = 1
    private var currentState: State<VO> = EMPTY()
    private var viewHide: () -> Unit = { viewController.showEmptyProgress(false) }
    private var currentPage = 0
    private var requestDisposable: Disposable? = null

    fun refresh() = currentState.refresh()

    fun loadNewPage() = currentState.loadNewPage()

    fun release() = currentState.release()

    private fun disposeAll() {
        requestDisposable?.dispose()
    }

    private fun loadPage(page: Int, swap:Boolean = false) {
        requestDisposable = requestFactory.invoke(page)
            .map(mapper)
            .subscribe(
                {
                    currentState.newData(it,swap)
                    Log.d("DisposableLog",it.size.toString())
                },
                {
                    currentState.fail(it)
                    Log.d("MoviePaginator", it.message)
                }
            )
    }

    interface ViewControl<T>{

        fun showEmptyProgress(show: Boolean)

        fun showEmptyError(show: Boolean)

        fun showEmptyData(show: Boolean)

        fun showData(data: List<T> = emptyList(),swap: Boolean)

        //button_refresh and refresh_progress_bar
        fun showFailProgress(show: Boolean)

        fun showErrorMessage(show:Boolean)

        fun showPageProgress(show: Boolean)
    }

    private interface State<T> {
        fun refresh() {}
        fun loadNewPage() {}
        fun release() {}
        fun newData(data: List<T>,swap: Boolean) {}
        fun fail(error: Throwable) {}
    }

    private inner class EMPTY : State<VO> {
        override fun refresh() {
            Log.d("MoviePaginator", "State(EMPTY) function Refresh ->State(EMPTY_PROGRESS)")
            viewController.showEmptyProgress(true)
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            Log.d("MoviePaginator", "State(EMPTY) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class EMPTY_PROGRESS : State<VO> {

        override fun refresh() {
            Log.d("MoviePaginator", "State(EMPTY_PROGRESS) function refresh ->State(EMPTY_PROGRESS)")
            disposeAll()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun newData(data: List<VO>, swap: Boolean) {
            viewHide.invoke()
            if (data.isEmpty()) {
                Log.d(
                    "MoviePaginator",
                    "State(EMPTY_PROGRESS) function newData ->State(EMPTY_DATA)"
                )
                viewController.showEmptyData(true)
                viewHide = { viewController.showEmptyData(false) }
                currentState = EMPTY_DATA()
            } else {
                Log.d("MoviePaginator", "State(EMPTY_PROGRESS) function newData->State(DATA)")
                viewController.showData(data,swap)
                currentPage = FIRST_PAGE
                currentState = DATA()
            }

        }

        override fun fail(error: Throwable) {
            Log.d("MoviePaginator", "State(EMPTY_PROGRESS) function fail ->State(EMPTY_ERROR)")
            viewHide.invoke()
            viewController.showEmptyError(true)
            currentState = EMPTY_ERROR()
        }

        override fun release() {
            Log.d("MoviePaginator", "State(EMPTY_PROGRESS) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class EMPTY_DATA : State<VO> {

        override fun refresh() {
            Log.d("MoviePaginator", "State(EMPTY_DATA) function refresh ->State(EMPTY_PROGRESS)")
            viewHide.invoke()
            viewController.showEmptyProgress(true)
            viewHide = { viewController.showEmptyProgress(false) }
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun release() {
            Log.d("MoviePaginator", "State(EMPTY_DATA) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class EMPTY_ERROR : State<VO> {
        override fun refresh() {
            Log.d("MoviePaginator", "State(EMPTY_ERROR) function refresh ->State(EMPTY_PROGRESS)")
            viewController.showFailProgress(true)
            viewHide = {
                viewController.showFailProgress(false)
                viewController.showEmptyError(false)
            }
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            Log.d("MoviePaginator", "State(EMPTY_ERROR) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class DATA : State<VO> {

        override fun refresh() {
            Log.d("MoviePaginator", "State(DATA) function refresh ->State(EMPTY_PROGRESS)")
            viewController.showEmptyProgress(true)
            viewHide = { viewController.showEmptyProgress(false) }
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun loadNewPage() {
            Log.d("MoviePaginator", "State(DATA) function loadNewPage ->State(PAGE_PROGRESS)")
            viewController.showPageProgress(true)
            viewHide = { viewController.showPageProgress(false) }
            currentState = PAGE_PROGRESS()
            loadPage(currentPage + 1)
        }

        override fun release() {
            Log.d("MoviePaginator", "State(DATA) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class PAGE_PROGRESS :
        State<VO> {

        override fun refresh() {
            Log.d("MoviePaginator", "State(PAGE_PROGRESS) function refresh ->State(EMPTY_PROGRESS)")
            disposeAll()
            viewHide.invoke()
            viewController.showEmptyProgress(true)
            viewHide = {viewController.showEmptyProgress(false)}
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun newData(data: List<VO>, swap: Boolean) {
            viewHide.invoke()
            if (data.isNotEmpty()) {
                Log.d("MoviePaginator", "State(PAGE_PROGRESS) function NewData ->State(DATA)")
                viewController.showData(data,false)
                currentPage++
                currentState = DATA()
            } else {
                Log.d("MoviePaginator", "State(PAGE_PROGRESS) function NewData ->State(ALL_DATA)")
                currentState = ALL_DATA()
            }
        }

        override fun fail(error: Throwable) {
            Log.d("MoviePaginator", "State(PAGE_PROGRESS) function fail ->State(PAGE_ERROR)")
            viewHide.invoke()
            viewController.showErrorMessage(true)
            currentState = PAGE_ERROR()
        }

        override fun release() {
            Log.d("MoviePaginator", "State(PAGE_PROGRESS) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class PAGE_ERROR :
        State<VO> {
        override fun refresh() {
            Log.d("MoviePaginator", "State(PAGE_ERROR) function refresh ->State(PAGE_PROGRESS)")
            viewController.showFailProgress(true)
            viewHide = {
                viewController.showFailProgress(false)
                viewController.showErrorMessage(false)
            }
            currentState = PAGE_PROGRESS()
            loadPage(currentPage + 1)
        }

        override fun release() {
            Log.d("MoviePaginator", "State(PAGE_ERROR) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class ALL_DATA :
        State<VO> {

        override fun refresh() {
            Log.d("MoviePaginator", "State(ALL_DATA) function refresh ->State(EMPTY_PROGRESS)")
            viewController.showEmptyProgress(true)
            viewHide = {viewController.showEmptyProgress(false)}
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun release() {
            Log.d("MoviePaginator", "State(ALL_DATA) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class RELEASED :
        State<VO>

}