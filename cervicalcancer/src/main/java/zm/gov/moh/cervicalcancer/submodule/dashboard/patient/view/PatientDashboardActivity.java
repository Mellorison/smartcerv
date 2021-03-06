package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.cervicalcancer.databinding.ActivityPatientDashboardBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.PatientDashboardFragmentPagerAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.cervicalcancer.BR;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;

public class PatientDashboardActivity extends BaseActivity {

    public static final String CLIENT_ID_KEY = "CLIENT_ID_KEY";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    PatientDashboardViewModel viewModel;
    Submodule vitals;
    long clientId;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidThreeTen.init(this);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Patient Dashboard");

        vitals = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.VITALS);

        clientId = getIntent().getExtras().getLong(CLIENT_ID_KEY);

        viewModel = ViewModelProviders.of(this).get(PatientDashboardViewModel.class);

        ActivityPatientDashboardBinding  binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_dashboard);
        binding.setToolbarhandler(toolBarEventHandler);

        // Find the view pager that will allow the getUsers to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        PatientDashboardFragmentPagerAdapter adapter = new PatientDashboardFragmentPagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewModel.getRepository().getDatabase().cervicalCancerDao().getPatientById(clientId).observe(this, binding::setClient);
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonId(clientId).observe(this, binding::setClientAddress);

        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).observe(this ,binding::setFacility);
    }

    public Submodule getVitals() {
        return vitals;
    }

    public long getClientId() {
        return clientId;
    }

    public Client getClient() {
        return client;
    }

    public PatientDashboardViewModel getViewModel(){
        return viewModel;
    }
}