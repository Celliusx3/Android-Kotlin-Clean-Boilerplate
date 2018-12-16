package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.navigation.Navigator
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


abstract class BaseActivity : RxAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var scheduler: BaseSchedulerProvider

    protected val compositeDisposable = CompositeDisposable()

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected abstract fun getRootView(): View

    protected fun getUiScheduler(): Scheduler{
        return scheduler.ui()
    }

    protected fun getIoScheduler(): Scheduler{
        return scheduler.io()
    }

    protected fun getToolbarTitle(): String {
        return ""
    }

    protected fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.is_tablet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetContentView()
        onGetInputData(savedInstanceState)
        onInject()
        onBindView()
        onBindData(getRootView(), savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    protected fun onSetContentView() {
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource())
        }
    }

    protected open fun onBindView() {
        if (toolbar != null) {
            toolbar.title = getToolbarTitle()
            toolbar.setOnClickListener { onSetToolbarClick() }
            setSupportActionBar(toolbar)
        }
    }

    protected open fun onInject() {}

    protected open fun onBindData(view: View?, savedInstanceState: Bundle?) {
        if (savedInstanceState != null)
            return
    }

    protected open fun onGetInputData(savedInstanceState: Bundle?) {}

    protected fun onSetToolbarClick() {}

}
