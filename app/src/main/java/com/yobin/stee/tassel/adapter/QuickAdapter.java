package com.yobin.stee.tassel.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yobin_he on 2017/2/10.
 *   这个Adapter适用于Recyclerview的Help类;
 *
 * 用法1：mAdapter = new QuickAdapter<String>(data){
 * 				@override
 * 				public int getLayoutId(int viewType){
 * 					return R.layout.item;
 * 				}
 * 				@override
 * 				public void convert(VH holder ,String data , int positon){
 * 					holder.setText(R.id.text,data);
 * 					//holder.itemView.setOnClickListener();此处还可添加点击事件；
 * 				
 * 				}
 *         }
 *  用法2：mAdapter =  new QuickAdapter<Model>(data){
 *  	@override
 *  	public int getLayoutId(int viewType){
 *  		switch(ViewType){
 *  			case TYPE_1:
 *  			     return R.layout.item_1;
 *  			 case TYPE_2:
 *  			 	  return R.layou.item_2;
 *  		}
 *  	}
 *  	public int getItemViewType(int position){
 *  		if(position % 2 == 0){
 *  			return TYPE_1;
 *  		}else{
 *  			return TYPE_2
 *  		}
 *  	}
 *  	@override
 *  	public void convert(VH holder,Model data,int id){
 *  		int type = getItemViewType(position);
 *  		switch(type){
 *  			case TYPE_1:
 *     			 	holder.setText(R.id.text,data.text)
 * 				     break;
 * 				case TYPE_2:
 * 					holder.setImage(R.id.image,data.image);
 * 					break;
 *  		}
 *  	}
 *  }
 *
 *
 * from:http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0116/7039.html
 * 添加相应的监听器：http://blog.csdn.net/liaoinstan/article/details/51200600
 */

public abstract class QuickAdapter <T> extends RecyclerView.Adapter<QuickAdapter.VH>{
    private List<T> mDatas;
    public QuickAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public abstract int getLayoutId(int ViewType);


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.get(parent,getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        convert(holder,mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(VH holder, T data , int position);

    public static class VH extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mConvertView;

        public VH(View v) {
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }
        public static VH get(ViewGroup parent ,int layoutId){
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
            return new VH(convertView);
        }
        //获取相应的view,进行相应的findViewById();
        public <T extends View> T getView(int id){
            View v = mViews.get(id);
            if(v == null){
                v = mConvertView.findViewById(id);
                mViews.put(id,v);
            }
            return (T) v;
        }

        public void setText(int id,String value){
            TextView view = getView(id);
            view.setText(value);
        }
    }

    //进行相应的刷新操作
    public  void setmDatas(List<T> mDatas){
//        this.mDatas = mDatas;
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    };
}
