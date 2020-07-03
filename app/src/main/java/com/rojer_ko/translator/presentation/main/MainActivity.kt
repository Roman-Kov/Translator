package com.rojer_ko.translator.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.di.injectDependencies
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.base.BaseActivity
import com.rojer_ko.translator.presentation.description.DescriptionActivity
import com.rojer_ko.utils.viewById
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<AppState, MainInteractor>() {
    companion object{
        private const val HISTORY_ACTIVITY_PATH = "com.rojer_ko.historyscreen.HistoryActivity"
        private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
        private const val REQUEST_CODE = 42
    }

    init {
        injectDependencies()
    }

    override val layoutRes: Int = R.layout.activity_main
    override val appBar: Int? = R.id.mainToolbar
    override val networkStatusImage: Int? = R.id.checkConnectionImage
    override val model: MainViewModel by currentScope.inject()
    private val mainActivitySearchBtn by viewById<Button>(R.id.searchBtn)
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager
    private val stateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }

    private var adapter: MainAdapter? = null
    private val onItemClickListener: MainAdapter.OnListItemClickLestener =
        object : MainAdapter.OnListItemClickLestener{
            override fun onItemClick(data: SearchResult){
                if (isNetworkAvailable){
                    startActivity(DescriptionActivity.getIntent(
                        applicationContext,
                        data.text!!,
                        data.meanings!![0].translation!!.translation!!,
                        data.meanings[0].imageUrl
                    ))
                }
                else {Toast.makeText(this@MainActivity, R.string.dialog_message_device_is_offline, Toast.LENGTH_LONG).show()}
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        searchBtnClick()
        checkForUpdates()
    }

    private fun initViewModel(){
        check(mainRecyclerView.adapter == null){"The ViewModel should be initialised first" }
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun searchBtnClick(){
        mainActivitySearchBtn.setOnClickListener {
            model.getData(searchText.text.toString(), isNetworkAvailable)
        }
    }

    override fun setDataToAdapter(data: List<SearchResult>) {
        if (adapter == null) {
            adapter = MainAdapter(onItemClickListener, data)
            mainRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            mainRecyclerView.adapter = adapter
        } else {
            adapter!!.setData(data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                loadHistoryFeature()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: $resultCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadHistoryFeature(){
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)

        val request =
            SplitInstallRequest
                .newBuilder()
                .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener {
                val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Couldn't download feature: " + it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfo = appUpdateManager.appUpdateInfo

        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(parent.activity_main_layout,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }
}
