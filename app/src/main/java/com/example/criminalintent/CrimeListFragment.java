package com.example.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private static final int REQUEST_CRIME = 1;
    private static final String EXTRA_UUID = "Crime_UUID";
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private final int CALLTHEPOLICEE = R.layout.list_item_crime_police;
    private final int REGULARFRAGMENT = R.layout.list_item_crime;
    private int mLastUpdatedPosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        }else {
            if (mLastUpdatedPosition > -1) {
                mCrimeAdapter.notifyItemChanged(mLastUpdatedPosition);
                mLastUpdatedPosition = -1;
            } else {
                mCrimeAdapter.notifyDataSetChanged();
            }
        }

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTittleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private ImageView mPhoneImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            mTittleTextView = itemView.findViewById(R.id.crime_title_for_recycler);
            mDateTextView = itemView.findViewById(R.id.crime_date_for_recycler);
            mPhoneImageView = itemView.findViewById(R.id.crime_phone);
            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTittleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.toString());
            mPhoneImageView.setVisibility(mCrime.getRequiresPolice() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), CrimeActivity.class);
            mLastUpdatedPosition = this.getAdapterPosition();
            intent.putExtra(EXTRA_UUID, mCrime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != FragmentActivity.RESULT_OK) {
            return;
        }
        if ( requestCode == REQUEST_CRIME){

            return;
        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimeList;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimeList = crimes;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimeList.get(position);
            holder.bind(crime);
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(inflater, parent);


        }

        @Override
        public int getItemCount() {
            return mCrimeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = mCrimeList.get(position);
            return crime.getRequiresPolice() ? CALLTHEPOLICEE : REGULARFRAGMENT;
        }
    }
}
