package com.example.demoproject;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.demoproject.utill.TableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TableLayout tableLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableLayoutProduct);
        loadData();
        return rootView;
    }

    private void loadData() {
        List<TableModel> tableModels = new ArrayList<TableModel>();
        tableModels.add(new TableModel("10.00.12 PM", 12.99, 22, 22));
        tableModels.add(new TableModel("06.02.12 AM", 1.99, 22, 22));
        tableModels.add(new TableModel("09.11.12 PM", 2.22, 22, 22));
        tableModels.add(new TableModel("08.22.12 PM", 1.22, 22, 22));
        tableModels.add(new TableModel("03.00.12 AM", 3.00, 22, 22));
        tableModels.add(new TableModel("02.00.12 PM", 4.90, 22, 22));

        createColumns();
        fillData(tableModels);
    }

    private void createColumns() {
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Time Column
        TextView textViewId = new TextView(getContext());
        textViewId.setText("Time");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(15, 5, 15, 0);
        tableRow.addView(textViewId);

        // X Column
        TextView textViewName = new TextView(getContext());
        textViewName.setText("X");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        // Y Column
        TextView textViewPrice = new TextView(getContext());
        textViewPrice.setText("Y");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPrice);

        // Z Column
        TextView textViewPhoto = new TextView(getContext());
        textViewPhoto.setText("Z");
        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPhoto.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPhoto);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Add Divider
        tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Time Column
        textViewId = new TextView(getContext());
        textViewId.setText("-----------");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(15, 5, 15, 0);
        tableRow.addView(textViewId);

        // X Column
        textViewName = new TextView(getContext());
        textViewName.setText("-----------");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        // Y Column
        textViewPrice = new TextView(getContext());
        textViewPrice.setText("-----------");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPrice);

        // Z Column
        textViewPrice = new TextView(getContext());
        textViewPrice.setText("-----------");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPrice);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    private void fillData(List<TableModel> tableModels) {
        for (TableModel tableModel : tableModels) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow currentRow = (TableRow) view;
                    TextView textViewId = (TextView) currentRow.getChildAt(0);
                    String id = textViewId.getText().toString();
                }
            });

            // Id Column
            TextView textViewId = new TextView(getContext());
            textViewId.setText(tableModel.getTime());
            textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewId.setPadding(15, 5, 15, 0);
            tableRow.addView(textViewId);

            // Name Column
            TextView textViewName = new TextView(getContext());
            textViewName.setText(String.valueOf(tableModel.getAccelerometerX()));
            textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewName.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewName);

            // Price Column
            TextView textViewPrice = new TextView(getContext());
            textViewPrice.setText(String.valueOf(tableModel.getAccelerometerY()));
            textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewPrice.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewPrice);

            // Price Column
            TextView textViewAZ = new TextView(getContext());
            textViewAZ.setText(String.valueOf(tableModel.getAccelerometerZ()));
            textViewAZ.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewAZ.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewAZ);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
    }
}