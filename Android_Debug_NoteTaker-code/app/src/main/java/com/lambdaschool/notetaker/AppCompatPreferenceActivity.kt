package com.lambdaschool.notetaker

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup

/**
 * A [android.preference.PreferenceActivity] which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
abstract class AppCompatPreferenceActivity : PreferenceActivity() {

    private var mDelegate: AppCompatDelegate? = null

    val supportActionBar: ActionBar?
        get() = delegate?.supportActionBar

    private val delegate: AppCompatDelegate?
        get() {
            if (mDelegate == null) {
                mDelegate = AppCompatDelegate.create(this, null)
            }
            return mDelegate
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate?.installViewFactory()
        delegate?.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate?.onPostCreate(savedInstanceState)
    }

    fun setSupportActionBar(toolbar: Toolbar?) {
        delegate?.setSupportActionBar(toolbar)
    }

    override fun getMenuInflater(): MenuInflater {
        return delegate?.menuInflater ?: MenuInflater(applicationContext)
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        delegate?.setContentView(layoutResID)
    }

    override fun setContentView(view: View) {
        delegate?.setContentView(view)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate?.setContentView(view, params)
    }

    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate?.addContentView(view, params)
    }

    override fun onPostResume() {
        super.onPostResume()
        delegate?.onPostResume()
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        delegate?.setTitle(title)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        delegate?.onConfigurationChanged(newConfig)
    }

    override fun onStop() {
        super.onStop()
        delegate?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate?.onDestroy()
    }

    override fun invalidateOptionsMenu() {
        delegate?.invalidateOptionsMenu()
    }
}
