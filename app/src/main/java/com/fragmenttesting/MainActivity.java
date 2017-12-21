package com.fragmenttesting;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private FragmentTransaction fc;
    private Fragment fragment;
    private FragmentManager fm = getSupportFragmentManager();
    private Button[] b = new Button [4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.infoBS);
        tv.setText("Fragment count in back stack: "+fm.getBackStackEntryCount());
        fm.addOnBackStackChangedListener(()->{

            tv.setText("Fragment count in back stack: "+fm.getBackStackEntryCount());
        });

        b[0] = findViewById(R.id.add_buttton);
        b[0].setOnClickListener((v)->{
            addFragment();
        });
        b[1] = findViewById(R.id.pop_buttton);
        b[1].setOnClickListener((v)->{
            popFragment();
        });
        b[2] = findViewById(R.id.remove_buttton);
        b[2].setOnClickListener((v)->{
            removeFragment();
        });
        b[3] = findViewById(R.id.replace_buttton);
        b[3].setOnClickListener((v)->{
            replaceFragment();
        });
    }

    private void addFragment (){

        fc = fm.beginTransaction();
        fc.replace(R.id.frag_container, fragmentChecker())
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .addToBackStack(null)
        .commit();
    }

    @NonNull
    private Fragment fragmentChecker (){

        fragment = fm.findFragmentById(R.id.frag_container);
        if (fragment instanceof Fragment_One)
            return new Fragment_Two();
        else if (fragment instanceof  Fragment_Two )
            return new Fragment_Three();
        else if (fragment instanceof Fragment_Three)
            return new Fragment_One();
        else
            return new Fragment_One();
    }

    private void popFragment(){
        fm.popBackStack();
    }

    private void removeFragment(){

        fragment = fm.findFragmentById(R.id.frag_container);
        if (fragment != null ) {
            fc = fm.beginTransaction();
            fc.remove(fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .addToBackStack(null)
            .commit();
        }
        else return;
    }

    private void replaceFragment(){

        fc = fm.beginTransaction();
        fc.replace(R.id.frag_container, fragmentChecker())
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .addToBackStack(null)
        .commit();
    }
}
