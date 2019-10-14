package com.moringa.sanergyapp.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.moringa.sanergyapp.R;
import com.moringa.sanergyapp.adapters.EmployeesRecyclerAdapter;
import com.moringa.sanergyapp.models.Employees;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesFragment extends Fragment {
    RecyclerView mEmployeesListView;
    private List<Employees> employeesList;
    private EmployeesRecyclerAdapter employeesRecyclerAdapter;
    private FirebaseFirestore mFirestore;

    public EmployeesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_employees, container, false);
        mEmployeesListView = (RecyclerView) view.findViewById(R.id.users);

        mFirestore = FirebaseFirestore.getInstance();
        employeesList = new ArrayList<>();
        employeesRecyclerAdapter = new EmployeesRecyclerAdapter(container.getContext(),employeesList);

        mEmployeesListView.setHasFixedSize(true);
        mEmployeesListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mEmployeesListView.setAdapter(employeesRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();



        mFirestore.collection("Employees").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String user_id = doc.getDocument().getId();
                        Employees employees = doc.getDocument().toObject(Employees.class).withId(user_id);
                        employeesList.add(employees);

                        employeesRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
