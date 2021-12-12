package com.example.p3.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3.Home_Of_Login;
import com.example.p3.R;
import com.example.p3.User;
//import com.example.p3.databinding.FragmentHomeBinding;
import com.example.p3.databinding.ActivityAffectedCountriesBinding;
import com.example.p3.ui.Account.SlideshowViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
//    private FragmentHomeBinding binding;

    TextView w_active,w_recover,w_deaths;
    TextView i_active,i_recover,i_death,india,i_t,i_d,i_T;
    Button refresh,track;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root=inflater.inflate(R.layout.fragment_home,container,false);
        final TextView textView=root.findViewById(R.id.text_home1);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        i_active=root.findViewById(R.id.editText_india_active);
        i_recover= root.findViewById(R.id.editText_india_recover);
        i_death=root.findViewById(R.id.editText_india_death);
//        india = root.findViewById(R.id.editText_india);
        i_T=root.findViewById(R.id.editText_india_total_cases);
        i_t=root.findViewById(R.id.editText_india_today_cases);
        i_d=root.findViewById(R.id.editText_india_today_death);

    w_active= root.findViewById(R.id.editText_world_active);
    w_recover=root.findViewById(R.id.editText_world_recover);
    w_deaths=root.findViewById(R.id.editText_world_deaths);
    refresh=root.findViewById(R.id.refresh);
    track=root.findViewById(R.id.country_Tracking);

    refresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fetch();
            FetchIndia();
        }
    });

    Fetch();
    FetchIndia();

    track.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            startActivity(new Intent(getActivity(), ActivityAffectedCountriesBinding.class));
        }
    });
        return root;
    }

    private void FetchIndia() {

        String url  = "https://corona.lmao.ninja/v2/countries/";



        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<94;i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                i_active.setText(jsonObject.getString("active"));
                                i_death.setText(jsonObject.getString("deaths"));
                                i_recover.setText(jsonObject.getString("recovered"));

                            i_T.setText(jsonObject.getString("cases"));
                           i_t.setText(jsonObject.getString("todayCases"));
                            i_d.setText( jsonObject.getString("todayDeaths"));
//                            india.setText("Total Cases= "+totalCases+"\n Today's Cases= "+todayCases +"\n Today's deaths= "+todayDeaths   );
//

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                        simpleArcLoader.stop();
//                        simpleArcLoader.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            simpleArcLoader.stop();
//            simpleArcLoader.setVisibility(View.GONE);
//            Toast.makeText(AffectedCountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


    }





    private void Fetch() {

           String url="https://corona.lmao.ninja/v2/all/";

           StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {

                   try {

                       JSONObject  jsonObject = new JSONObject(response.toString());
                       w_active.setText(jsonObject.getString("active"));
                      w_recover.setText(jsonObject.getString("recovered"));
                      w_deaths.setText(jsonObject.getString("deaths"));

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
//                Toast.makeText(HomeFragment.this, error.getMessage(),Toast.LENGTH_SHORT).show();

               }
           });

           RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
           requestQueue.add(request);
       }

    }


