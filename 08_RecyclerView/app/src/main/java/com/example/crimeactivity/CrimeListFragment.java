package com.example.crimeactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListFragment  extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);

        mCrimeRecyclerView =(RecyclerView) view.findViewById(R.id.crime_recycler_view);

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequestPolice()) {
               return R.layout.list_item_crime_serious;

            }else {
                return R.layout.list_item_crime;
            }
        }
    }


    private class CrimeHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Crime mCrime;
        private Button mContactPoliceButton;
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent,int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            if (mCrime.isRequestPolice()) {
                mContactPoliceButton = (Button) itemView.findViewById(R.id.call_police_button);
                mContactPoliceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Police contacted for " + mCrime.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


    }

}
