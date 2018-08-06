package com.example.tyrone.scse_foc_2018.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.adapter.AccidentReportAdapter;
import com.example.tyrone.scse_foc_2018.adapter.NewsAdapter;
import com.example.tyrone.scse_foc_2018.entity.AccidentReport;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAccidentReportFragment extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference database;

    Spinner accidentListSpinner;

    ListView listView;
    private ArrayList<AccidentReport> reportArrayList = new ArrayList<AccidentReport>();

    AccidentReportAdapter accidentReportAdapter;
    //the part that refrehses the page every second
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* do what you need to do */
            RetrieveAccidentReports();
            accidentReportAdapter.notifyDataSetChanged();
            /* and here comes the "trick" */
            //handler.postDelayed(this, 1000);
        }
    };
    AdapterView.OnItemSelectedListener AccidentListSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Organise(accidentListSpinner.getSelectedItem().toString());

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler.postDelayed(runnable, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_accident_report,container,false);

        accidentListSpinner = view.findViewById(R.id.accidentListSpinner);
        ArrayAdapter<String> accidentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.accidentTypesSelection));
        accidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accidentListSpinner.setAdapter(accidentAdapter);
        accidentListSpinner.setOnItemSelectedListener(AccidentListSpinnerListener);

        listView = (ListView) view.findViewById(R.id.frag_list);

        accidentReportAdapter = new AccidentReportAdapter(getActivity(),reportArrayList);

        //Log.i("onGetDataSuccessNull", String.valueOf(this.listView.getAdapter().getCount()));

        listView.setAdapter(accidentReportAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //if the image string is not empty, then start the fragment, else do nothing
                if(!accidentReportAdapter.getItem(i).getImage().equals("")) {

                    ViewImageFragment viewImageFragment = ViewImageFragment.newInstance(accidentReportAdapter.getItem(i).getImage());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fl_contents, viewImageFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


        return view;
    }

   public void RetrieveAccidentReports()
   {
       mAuth = FirebaseAuth.getInstance();
       FirebaseUser user = mAuth.getCurrentUser();

       if ( user != null ) {
           database = FirebaseDatabase.getInstance().getReference("accidentreport");
           database.addListenerForSingleValueEvent(new ValueEventListener() {

               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    onGetDataSuccess(dataSnapshot);
               }
               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }
   }
    public void onGetDataSuccess (DataSnapshot data) {

        reportArrayList.clear();
        for ( DataSnapshot news :  data.getChildren() ) {
            //News news = new News();

            reportArrayList.add(0, news.getValue(AccidentReport.class));

        }
    }
    private void Organise(String selection)
    {
        Log.i("a",selection);
        if(selection.equals("All"))
        {
            RetrieveAccidentReports();
            accidentReportAdapter.notifyDataSetChanged();

        }
        else
        {
            //reportArrayList.clear();
            RetrieveAccidentReports();

            for(int i = 0; i < reportArrayList.size(); i ++)
            {
                //if the accident isnt the one in the selection
                if(!reportArrayList.get(i).getAccident().equals(selection))
                {
                    reportArrayList.remove(i);
                    i--;
                }

            }
            accidentReportAdapter.notifyDataSetChanged();
        }
    }
}
