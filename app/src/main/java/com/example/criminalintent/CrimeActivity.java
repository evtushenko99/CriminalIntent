package com.example.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_UUID = "Crime_UUID";
    @Override
    protected Fragment createFragment() {
        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_UUID);
        return CrimeFragment.newInstance(crimeID);
    }
}
