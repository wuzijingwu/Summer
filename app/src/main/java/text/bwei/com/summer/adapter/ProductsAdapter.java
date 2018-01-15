package text.bwei.com.summer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import text.bwei.com.summer.R;
import text.bwei.com.summer.bean.ProductsBean;
import text.bwei.com.summer.view.DetailActivity;

/**
 * Created by z on 2018/1/11.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{
    private Context context;
    private List<ProductsBean.DataBean> list;

    public ProductsAdapter(Context context, List<ProductsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_products,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String images = list.get(position).getImages();
        String[] split = images.split("!");
        Glide.with(context).load(split[0]).into(holder.img);
        holder.tv1.setText(list.get(position).getSubhead());
        holder.tv2.setText(list.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("pid",list.get(position).getPid()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv1;
        private TextView tv2;
         public ViewHolder(View itemView) {
             super(itemView);
             img= (ImageView) itemView.findViewById(R.id.imageView);
             tv1= (TextView) itemView.findViewById(R.id.item_title);
             tv2= (TextView) itemView.findViewById(R.id.item_title2);
         }
     }
}
