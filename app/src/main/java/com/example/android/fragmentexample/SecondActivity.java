package com.example.android.fragmentexample;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button mButton;
    private boolean isFragmentDisplayed = false;
    private final String STATE_FRAGMENT = "state_of_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mButton = findViewById(R.id.button_open);

        if (savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed){
                // If the fragment is displayed, change button_open to close
                mButton.setText(R.string.close);
            }
        }

        // Set the click listener for the button_open
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed){
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
    }

    public void displayFragment(){
        // Instantiate the fragment (SimpleFragment)
        SimpleFragment simpleFragment = SimpleFragment.newInstance();
        // Get the FragmentManager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Add the SimpleFragment
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();
        // Update the Button text
        mButton.setText(R.string.close);
        // Set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    public void closeFragment(){
        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null){
            // Create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Update the Button text
        mButton.setText(R.string.open);
        // Set boolean flag to indicate fragment is closed
        isFragmentDisplayed = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the state of the fragment
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(outState);
    }

    public void OpenMainActivity(View view) {
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }
}
