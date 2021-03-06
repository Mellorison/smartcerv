package zm.gov.moh.common.submodule.dashboard.client.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardCareServicesFragment;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardVitalsFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class ClientDashboardFragmentPagerAdapter extends FragmentPagerAdapter {

    private BaseActivity mContext;

    public ClientDashboardFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            Fragment fragment = new ClientDashboardVitalsFragment();
            fragment.setArguments(mContext.getIntent().getExtras());
            return fragment;
        }
        else if (position == 1)
            return new ClientDashboardCareServicesFragment();
         else
            return new ClientDashboardVitalsFragment();

    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        switch (position) {
            case 0:
                return "Vitals";
            case 1:
                return "Care Services";
            default:
                return null;
        }
    }

}