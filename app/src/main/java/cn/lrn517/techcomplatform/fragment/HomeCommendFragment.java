package cn.lrn517.techcomplatform.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lrn517.techcomplatform.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCommendFragment extends Fragment {


    public HomeCommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_commend, container, false);
    }

    @Override
    public void onDestroyView() {
        Log.i("msg","销毁Comment视图");
        super.onDestroyView();
    }

}
