package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.userapplyfordata;

/**
 * Created by lirun on 2018/4/8.
 */

public class MyApplyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List list;
    Context context;
    View view;
    private LayoutInflater layoutInflater;
    SharedPreferences sharedPreferences;

    public MyApplyViewAdapter(Context context, List<Map<String,Object>> list ){
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_apply_view,parent,false);
        return new MyApplyViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final userapplyfordata data = (userapplyfordata) list.get(position);
        if( null == data )
            return;
        MyApplyViewAdapter.ViewHolder viewHolder = (MyApplyViewAdapter.ViewHolder) holder;
        sharedPreferences = context.getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        switch ( data.getFlag() ){
            case 0:
                viewHolder.flag_1.setVisibility(View.GONE);
                viewHolder.time_0.setText(data.getCreatetime().toString());
                if( 0 == data.getState() ){
                    viewHolder.state_0.setText("正在处理");
                }else if( 1 == data.getState()){
                    viewHolder.state_0.setText("申请通过");
                    Log.i("专栏申请","申请+"+sharedPreferences.getString("tpzid" , null));
                    if( null == sharedPreferences.getString("tpzid" , null)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tpzid" , data.getTpzid());
                        editor.putInt("applyTPZState" , 2);
                        editor.apply();
                        Log.i("专栏申请","申请成功");
                    }
                }else{
                    viewHolder.state_0.setText("申请失败");
                }
                break;
            case 1:
                viewHolder.flag_0.setVisibility(View.GONE);
                viewHolder.time_1.setText(data.getCreatetime().toString());
                if( 0 == data.getState() ){
                    viewHolder.state_1.setText("正在处理");
                }else if( 1 == data.getState()){
                    viewHolder.state_1.setText("申请通过");
                    if( !"2".equals(sharedPreferences.getString("ispassed" , null))){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ispassed" , "2");
                        editor.apply();
                        Log.i("ispassed" , "ispassed"+" success");
                    }
                }else{
                    viewHolder.state_1.setText("申请失败");
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout flag_0,flag_1;
        TextView time_0,time_1,state_0,state_1;

        ViewHolder(View itemView){
            super(itemView);
            flag_0 = itemView.findViewById(R.id.my_apply_applytpz_layout);
            flag_1 = itemView.findViewById(R.id.my_apply_applyuser_layout);
            time_0 = itemView.findViewById(R.id.my_apply_applytpz_createtime);
            state_0 = itemView.findViewById(R.id.my_apply_applytpz_state);
            time_1 = itemView.findViewById(R.id.my_apply_applyuser_createtime);
            state_1 = itemView.findViewById(R.id.my_apply_applyuser_state);
        }
    }
}
