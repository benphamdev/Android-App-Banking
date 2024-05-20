package com.example.demoapp.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demoapp.Activities.SearchResult;
import com.example.demoapp.Adapters.SearchAdapter;
import com.example.demoapp.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {


    private static final int REQUEST_CODE_VOICE_INPUT = 1;

    private MaterialSearchBar searchBar;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private List<SearchResult> searchResults;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchBar = view.findViewById(R.id.searchBar);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);

        if (recyclerView == null) {
            throw new NullPointerException(
                    "RecyclerView is null. Ensure that the ID is correct and the layout contains RecyclerView with the specified ID.");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchResults = new ArrayList<>();
        searchAdapter = new SearchAdapter(searchResults);
        recyclerView.setAdapter(searchAdapter);

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                // Không cần xử lý trong ví dụ này
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                performSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_SPEECH) {
                    startVoiceInput();
                }
            }
        });
        return view;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        try {
            startActivityForResult(intent, REQUEST_CODE_VOICE_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(), "Voice search not supported on this device", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VOICE_INPUT && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String voiceQuery = result.get(0);
                searchBar.setText(voiceQuery);
                performSearch(voiceQuery);
            }
        }
    }


    private void performSearch(String query) {
        // Clear the previous search results
        searchResults.clear();

        // Simulate search results
        // In a real application, you would perform a network request here
        if (query.toLowerCase()
                .contains("example")) {
            searchResults.add(new SearchResult(
                    "Example Title 1",
                    "This is the description for example title 1."
            ));
            searchResults.add(new SearchResult(
                    "Example Title 2",
                    "This is the description for example title 2."
            ));
        }

        // Notify the adapter of the changes
        searchAdapter.notifyDataSetChanged();

        // Show the RecyclerView if there are results
        if (searchResults.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}