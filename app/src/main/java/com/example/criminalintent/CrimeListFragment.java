package com.example.criminalintent;


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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private final int CALLTHEPOLICEE = R.layout.list_item_crime_police;
    private final int REGULARFRAGMENT = R.layout.list_item_crime;

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

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mCrimeAdapter = new CrimeAdapter((crimes));
        mCrimeRecyclerView.setAdapter(mCrimeAdapter);
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
            mPhoneImageView.setVisibility(mCrime.getRequiresPolice()?View.VISIBLE: View.GONE);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + "clicked", Toast.LENGTH_SHORT).show();
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
