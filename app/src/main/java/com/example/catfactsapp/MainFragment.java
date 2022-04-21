package com.example.catfactsapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MainViewModel mViewModel;
    CatListAdapter catListAdapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    static List<String> breeds;

    List<Cat> totalCats;
    String filter;
    static String[] catBreeds = { "Filter by breed"};

    static ArrayAdapter<String> adapter;
    Spinner spinner;
    Button buttonClear;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    public void goTop(View view) {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        filter = catBreeds[i];
        if(!filter.equals(getResources().getString(R.string.filter))){
            List<Cat> filteredCatList = filterList(totalCats);
            catListAdapter.setmCats(filteredCatList);
            catListAdapter.notifyDataSetChanged();
        }
        else{
            clear(view);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {    }

    public void clear(View view) {
        String selected = spinner.getSelectedItem().toString();
        if(!selected.equals(getResources().getString(R.string.filter))){
            spinner.setSelection(0);
            catListAdapter.setmCats(totalCats);
            catListAdapter.notifyDataSetChanged();
        }
        else {
            catListAdapter.setmCats(new ArrayList<Cat>(totalCats));
            catListAdapter.notifyDataSetChanged();
        }
    }

    private List<Cat> filterList(List<Cat> totalCats) {
        List<Cat> result = new ArrayList<>();
        for (int i = 0 ; i < totalCats.size(); ++i){
            if (totalCats.get(i).getBreed().equals(filter)) result.add(totalCats.get(i));
        }
        return result;
    }
}