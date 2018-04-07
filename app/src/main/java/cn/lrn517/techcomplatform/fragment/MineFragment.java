package cn.lrn517.techcomplatform.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.MyCollectionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private TextView ualiase;
    private TextView lookat_userinfomation;
    private LinearLayout latelybrowse;
    private LinearLayout my_collection;
    private LinearLayout attention;
    private LinearLayout account;
    private LinearLayout buyed;
    private LinearLayout apply;
    private LinearLayout editpassword;
    private LinearLayout logout;
    private View view;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        ualiase = view.findViewById(R.id.mine_ualiase);
        lookat_userinfomation = view.findViewById(R.id.mine_lookatuserinfo);
        latelybrowse = view.findViewById(R.id.mine_latelybrowse);
        my_collection = view.findViewById(R.id.mine_collection);
        attention = view.findViewById(R.id.mine_attention);
        account = view.findViewById(R.id.mine_account);
        buyed = view.findViewById(R.id.mine_buyed);
        apply = view.findViewById(R.id.mine_apply);
        editpassword = view.findViewById(R.id.mine_editpassword);
        logout = view.findViewById(R.id.mine_logout);
    }

    private void initEvent(){
        my_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , MyCollectionActivity.class);
                startActivity(intent);
            }
        });
    }

}
