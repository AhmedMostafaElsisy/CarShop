package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.cars.adaptor.CarAdaptor;
import com.example.cars.model.Car;
import com.example.cars.persistence.CarRepository;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CarAdaptor.OnCarListener {
    private CarAdaptor adapter;
    private List<Car> exampleList =new ArrayList<>();
    private FloatingActionButton FAB;
    private CarRepository repository;
    private BottomAppBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.bar);
        setUpToolbar();
        repository = new CarRepository(this);
        retrieveCar();
    }

    private void retrieveCar() {
        repository.retrieveCarsTask().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                if (exampleList.size() > 0) {
                  exampleList.clear();
                }
                if (cars!=null){

                    exampleList.addAll(cars);

                }
                setUpRecyclerView();
            }
        });
    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new CarAdaptor(exampleList, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        FAB = findViewById(R.id.fab);
        FAB.setColorFilter(Color.WHITE);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCarClick(int position) {
        Intent intent = new Intent(this, CarActivity.class);
        intent.putExtra("selectedCar", exampleList.get(position));
        startActivity(intent);
finish();
    }


    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}