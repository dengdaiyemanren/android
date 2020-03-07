package com.example.crimeactivity;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment()
    {
        return new CrimeListFragment();
    }
}
