package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.AskDetailActivity;
import cn.lrn517.techcomplatform.activity.BrowseTechPersonZoneDetailActivity;
import cn.lrn517.techcomplatform.activity.MineInfoActivity;
import cn.lrn517.techcomplatform.activity.SearchActivity;
import cn.lrn517.techcomplatform.activity.TechDetailActivity;
import cn.lrn517.techcomplatform.activity.TechDetailCommentActivity;
import cn.lrn517.techcomplatform.activity.UserInfoActivity;
import cn.lrn517.techcomplatform.bean.searchlist;
import cn.lrn517.techcomplatform.bean.userSearchHistory;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lirun on 2018/5/9.
 */

public class SearchCommonViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;
    private SharedPreferences sharedPreferences;

    public SearchCommonViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 0 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_user_view, parent, false);
            return new SearchCommonViewAdapter.ViewHolder_0(view);
        }else if( 1 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view,parent,false);
            return new SearchCommonViewAdapter.ViewHolder_1(view);
        }else if( 2 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_question_view,parent,false);
            return new SearchCommonViewAdapter.ViewHolder_2(view);
        }else if( 3 == i){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view, parent, false);
            return new SearchCommonViewAdapter.ViewHolder_3(view);
        }else{
            view= layoutInflater.from(parent.getContext()).inflate(R.layout.search_history_list_view, parent, false);
            return new SearchCommonViewAdapter.ViewHolder_4(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        final String uid = sharedPreferences.getString("uid",null);
        if( holder instanceof SearchCommonViewAdapter.ViewHolder_0){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_0 viewHolder_0 = (SearchCommonViewAdapter.ViewHolder_0) holder;
            Glide.with(context)
                    .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                    .dontAnimate()
                    .crossFade()
                    .into(viewHolder_0.image);
            viewHolder_0.ualiase.setText(data.getUaliase());
            viewHolder_0.ulevel.setText(data.getUlevel());
            if( "0".equals(data.getUtype())){
                viewHolder_0.utype.setText("普通用户");
            }else{
                viewHolder_0.utype.setText("高级用户");
            }
            viewHolder_0.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( null != uid ){
                        if( uid.equals(data.getUid())){
                            Intent intent = new Intent(context , MineInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("uid" , data.getUid());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }else{
                            Intent intent = new Intent(context , UserInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("uid" , data.getUid());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(context , UserInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("uid" , data.getUid());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }else if( holder instanceof SearchCommonViewAdapter.ViewHolder_1){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_1 viewHolder_1 = (SearchCommonViewAdapter.ViewHolder_1) holder;
            viewHolder_1.name.setText(data.getTdtitle());
            viewHolder_1.tname.setText(data.getTname());
            viewHolder_1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TechDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tdid" , data.getTdid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else if (holder instanceof SearchCommonViewAdapter.ViewHolder_2){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_2 viewHolder_2 = (SearchCommonViewAdapter.ViewHolder_2) holder;
            viewHolder_2.tdtitle.setText(data.getTdtitle());
            viewHolder_2.tname.setText(data.getTname());
            viewHolder_2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AskDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tdid" , data.getTdid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else if(holder instanceof SearchCommonViewAdapter.ViewHolder_3){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_3 viewHolder_3 = (SearchCommonViewAdapter.ViewHolder_3) holder;
            viewHolder_3.name.setText(data.getTpzdtitle());
            viewHolder_3.tname.setText(data.getTpzname());
            viewHolder_3.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BrowseTechPersonZoneDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tpzdid" , data.getTpzdid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else{
            final userSearchHistory data = (userSearchHistory) list.get(position);
            SearchCommonViewAdapter.ViewHolder_4 viewHolder_4 = (SearchCommonViewAdapter.ViewHolder_4) holder;
            viewHolder_4.text.setText(data.getSearchcontent());
            viewHolder_4.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SearchActivity) context).setSearch(data.getSearchcontent());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_2 extends RecyclerView.ViewHolder{

        TextView tdtitle;
        TextView tname;

        ViewHolder_2(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_attention_question_tdtitle);
            tname = itemView.findViewById(R.id.my_attention_question_tname);
        }
    }

    private class ViewHolder_1 extends RecyclerView.ViewHolder{

        TextView name;
        TextView tname;

        ViewHolder_1(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
            tname = itemView.findViewById(R.id.my_collection_common_detail_tname);
        }
    }

    public class ViewHolder_0 extends RecyclerView.ViewHolder{

        TextView ualiase,ulevel,utype;
        CircleImageView image;

        public ViewHolder_0(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.my_attention_user_ualiase);
            ulevel = itemView.findViewById(R.id.my_attention_user_ulevel);
            utype = itemView.findViewById(R.id.my_attention_user_utype);
            image = itemView.findViewById(R.id.my_attention_user_image);
        }
    }

    public class ViewHolder_3 extends RecyclerView.ViewHolder{

        TextView name;
        TextView tname;


        public ViewHolder_3(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
            tname = itemView.findViewById(R.id.my_collection_common_detail_tname);
        }
    }

    public class ViewHolder_4 extends RecyclerView.ViewHolder{

        TextView text;
        ImageView del;


        public ViewHolder_4(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.search_history_list_text);
            del = itemView.findViewById(R.id.search_history_list_del);
        }
    }
}
