package com.waqar.sampleproject.base.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.waqar.sampleproject.R
import com.waqar.sampleproject.base.viewmodel.BaseViewModel
import com.waqar.sampleproject.base.viewmodel.Event
import com.waqar.sampleproject.core.model.AlertModel

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    @BindView(R.id.pb_loading)
    lateinit var loadingView: ProgressBar

    protected var activityContext: AppCompatActivity? = null

    open val viewModel: T by lazy { getOrCreateViewModel() }
    protected lateinit var rootView: View
    protected lateinit var baseView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_base, null)

        val contentStub = baseView.findViewById<ViewStub>(R.id.content_viewstub)
        contentStub.layoutResource = getViewLayout()
        rootView = contentStub.inflate()

        ButterKnife.bind(this, baseView)

        setupBaseBinding()

        setupBindings()

        loadData()

        return baseView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context as AppCompatActivity
    }

    override fun onDetach() {
        super.onDetach()
        activityContext = null
    }

    private fun setupBaseBinding() {
        viewModel.showLoading.observe(viewLifecycleOwner, Observer { shouldShow ->
            showLoading(shouldShow)
        })

        viewModel.showNoInternetAlert.observe(this, Observer {
            val showAlert = it?.getContentIfNotHandled()
            if (showAlert is Boolean && showAlert) {
                showErrorDialog(
                    R.string.title_no_internet_connection,
                    R.string.message_no_internet_connection,
                    R.string.alert_ok_label
                )
            }
        })

        viewModel.showAlertDialog.observe(
            viewLifecycleOwner,
            Observer<Event<AlertModel>> { alertEvent ->
                val alert: AlertModel? = alertEvent?.getContentIfNotHandled()
                if (alert != null) {
                    showConfirmationAlert(
                        alert.title,
                        alert.message ?: "",
                        alert.positiveButtonTitle ?: "",
                        alert.onPositiveButtonClickAction,
                        alert.negativeButtonTitle ?: "",
                        alert.onNegativeButtonClickAction
                    )
                }
            })
    }

    protected open fun showLoading(shouldShow: Boolean) {
        loadingView.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    protected fun showErrorDialog(
        alertTitle: Int,
        alertMessage: Int,
        negativeButtonTitle: Int,
        onNegativeButtonClickAction: (() -> Unit)? = null
    ) {
        showErrorDialog(
            getString(alertTitle),
            getString(alertMessage),
            getString(negativeButtonTitle),
            onNegativeButtonClickAction
        )
    }

    protected fun showErrorDialog(
        alertTitle: String,
        alertMessage: String,
        negativeButtonTitle: String,
        onNegativeButtonClickAction: (() -> Unit)? = null
    ) {
        val alertDialog = AlertDialog.Builder(activity).create()
        alertDialog.setTitle(alertTitle)
        alertDialog.setMessage(alertMessage)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, negativeButtonTitle
        ) { dialog, _ ->
            dialog.dismiss();
            onNegativeButtonClickAction?.invoke()
        }
        alertDialog.show()
    }

    protected fun showConfirmationAlert(
        alertTitle: Int? = null,
        alertMessage: Int,
        positiveButtonTitle: Int,
        onPositiveButtonClickAction: (() -> Unit)?,
        negativeButtonTitle: Int,
        onNegativeButtonClickAction: (() -> Unit)?
    ) {
        var title: String? = null
        alertTitle?.let {
            title = getString(it)
        }
        showConfirmationAlert(
            title,
            getString(alertMessage),
            getString(positiveButtonTitle),
            onPositiveButtonClickAction,
            getString(negativeButtonTitle),
            onNegativeButtonClickAction
        )
    }

    protected fun showConfirmationAlert(
        alertTitle: String?,
        alertMessage: String,
        positiveButtonTitle: String,
        onPositiveButtonClickAction: (() -> Unit)?,
        negativeButtonTitle: String,
        onNegativeButtonClickAction: (() -> Unit)?
    ) {

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(alertTitle)
            .setCancelable(false)
            .setMessage(alertMessage)
            .setPositiveButton(
                positiveButtonTitle
            ) { dialogInterface, i ->
                onPositiveButtonClickAction?.invoke()
                dialogInterface.dismiss()
            }

            .setNegativeButton(
                negativeButtonTitle
            ) { dialogInterface, i ->

                onNegativeButtonClickAction?.invoke()
                dialogInterface.dismiss()
            }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps = false
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
    }

    protected open fun getLoadingView(): Int = 0

    // this method will be invoked from @onViewCreated to setup bindings
    protected open fun setupBindings() {}

    // this method will be invoked from @onViewCreated to load data remotely/locally
    protected open fun loadData() {}

    // this method returns the resource id of layout
    protected abstract fun getViewLayout(): Int

    protected abstract fun getOrCreateViewModel(): T
}