package cn.lrn517.techcomplatform.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MessageViewAdapter;
import cn.lrn517.techcomplatform.bean.loadMessageData;
import cn.lrn517.techcomplatform.model.MessageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    //测试数据
    String uid = "20180319124601";

    private Call call;
    private MessageModel messageModel = new MessageModel();
    private View view;
    private TextView count;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private MessageViewAdapter messageViewAdapter;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        count = view.findViewById(R.id.message_count);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.message_recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = messageModel.loadMessageList(uid);
        Callback<loadMessageData> callback = new Callback<loadMessageData>() {
            @Override
            public void onResponse(Call<loadMessageData> call, Response<loadMessageData> response) {
                loadMessageData data = response.body();
                count.setText(data.getCount().toString());
                List datalist = data.getList();
                messageViewAdapter = new MessageViewAdapter(getActivity(),datalist,1,uid);
                recyclerView.setAdapter(messageViewAdapter);
            }

            @Override
            public void onFailure(Call<loadMessageData> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

}
