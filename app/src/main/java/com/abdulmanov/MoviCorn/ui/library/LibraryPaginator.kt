package com.abdulmanov.MoviCorn.ui.library

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

class LibraryPaginator<VO,DTO>(
    private val viewController: LibraryViewController<VO>,
    private val mapper:Function<List<DTO>,List<VO>>,
    var requestFactory:(page:Int)-> Flowable<List<DTO>>
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
                },
                {
                    currentState.fail(it)
                    Log.d("MoviePaginator", it.message)
                }
            )
    }

    interface LibraryViewController<T>{

        fun showEmptyProgress(show: Boolean)

        fun showEmptyData(show: Boolean)

        fun showData(data: List<T> = emptyList(),swap: Boolean)

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
            Log.d("LibraryPaginator", "State(EMPTY) function Refresh ->State(EMPTY_PROGRESS)")
            viewController.showEmptyProgress(true)
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            Log.d("LibraryPaginator", "State(EMPTY) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class EMPTY_PROGRESS : State<VO> {

        override fun refresh() {
            Log.d(
                "LibraryPaginator",
                "State(EMPTY_PROGRESS) function refresh ->State(EMPTY_PROGRESS)"
            )
            disposeAll()
            loadPage(FIRST_PAGE, swap = true)
        }

        override fun newData(data: List<VO>, swap: Boolean) {
            viewHide.invoke()
            if (data.isEmpty()) {
                Log.d(
                    "LibraryPaginator",
                    "State(EMPTY_PROGRESS) function newData ->State(EMPTY_DATA)"
                )
                viewController.showEmptyData(true)
                viewHide = { viewController.showEmptyData(false) }
                currentState = EMPTY_DATA()
            } else {
                Log.d("LibraryPaginator", "State(EMPTY_PROGRESS) function newData->State(DATA)")
                viewController.showData(data, swap)
                currentPage = FIRST_PAGE
                currentState = DATA()
            }

        }
    }

    private inner class EMPTY_DATA : State<VO> {

        override fun refresh() {
            Log.d("LibraryPaginator", "State(EMPTY_DATA) function refresh ->State(EMPTY_PROGRESS)")
            viewHide.invoke()
            viewController.showEmptyProgress(true)
            viewHide = { viewController.showEmptyProgress(false) }
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun release() {
            Log.d("LibraryPaginator", "State(EMPTY_DATA) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class DATA : State<VO> {

        override fun refresh() {
            Log.d("LibraryPaginator", "State(DATA) function refresh ->State(EMPTY_PROGRESS)")
            viewController.showEmptyProgress(true)
            viewHide = { viewController.showEmptyProgress(false) }
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun loadNewPage() {
            Log.d("LibraryPaginator", "State(DATA) function loadNewPage ->State(PAGE_PROGRESS)")
            viewController.showPageProgress(true)
            viewHide = { viewController.showPageProgress(false) }
            currentState = PAGE_PROGRESS()
            loadPage(currentPage + 1)
        }

        override fun release() {
            Log.d("LibraryPaginator", "State(DATA) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class PAGE_PROGRESS : State<VO> {

        override fun refresh() {
            Log.d("LibraryPaginator", "State(PAGE_PROGRESS) function refresh ->State(EMPTY_PROGRESS)")
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
                Log.d("LibraryPaginator", "State(PAGE_PROGRESS) function NewData ->State(DATA)")
                viewController.showData(data,false)
                currentPage++
                currentState = DATA()
            } else {
                Log.d("LibraryPaginator", "State(PAGE_PROGRESS) function NewData ->State(ALL_DATA)")
                currentState = ALL_DATA()
            }
        }

        override fun release() {
            Log.d("LibraryPaginator", "State(PAGE_PROGRESS) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class ALL_DATA : State<VO> {

        override fun refresh() {
            Log.d("LibraryPaginator", "State(ALL_DATA) function refresh ->State(EMPTY_PROGRESS)")
            viewController.showEmptyProgress(true)
            viewHide = {viewController.showEmptyProgress(false)}
            currentState = EMPTY_PROGRESS()
            loadPage(FIRST_PAGE,swap = true)
        }

        override fun release() {
            Log.d("LibraryPaginator", "State(ALL_DATA) function release ->State(RELEASED)")
            currentState = RELEASED()
            disposeAll()
        }
    }

    private inner class RELEASED : State<VO>

}