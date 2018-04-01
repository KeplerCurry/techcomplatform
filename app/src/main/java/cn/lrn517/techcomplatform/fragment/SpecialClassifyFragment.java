package cn.lrn517.techcomplatform.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.ApplyForSpecialClassifyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialClassifyFragment extends Fragment {

    private FloatingActionButton applyfor;
    private View view;

    public SpecialClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_special_classify, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        applyfor = view.findViewById(R.id.special_classify_apply_for);
    }


    private void initEvent(){
        applyfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , ApplyForSpecialClassifyActivity.class);
                startActivity(intent);
            }
        });
    }

}
