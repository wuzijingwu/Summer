package text.bwei.com.summer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import text.bwei.com.summer.R;
import text.bwei.com.summer.bean.CartsBean;
import text.bwei.com.summer.bean.IsnoBean;
import text.bwei.com.summer.intface.ChiledCheckBox;
import text.bwei.com.summer.intface.Delete;
import text.bwei.com.summer.intface.DownGeshu;
import text.bwei.com.summer.intface.GroupCheckBox;
import text.bwei.com.summer.intface.UpGeshu;

/**
 * Created by z on 2018/1/13.
 */

public class ExpandableListViewAdpt extends BaseExpandableListAdapter{
    Context context;
    //店家,商品数据
    List<CartsBean.DataBean> data;

    //复选框传值集合
    List<IsnoBean> GroupIsno;   //Group集合
    List<List<IsnoBean>> ChiledIsno;   //Chiled集合
    //有参构造
    public ExpandableListViewAdpt(Context context, List<CartsBean.DataBean> data, List<IsnoBean> GroupIsno, List<List<IsnoBean>> ChiledIsno) {
        this.context = context;
        this.data = data;
        this.GroupIsno = GroupIsno;
        this.ChiledIsno = ChiledIsno;
    }
    //GroupCheckBox接口
    private GroupCheckBox groupcheckbox;
    public void setOnGroupCheckBox(GroupCheckBox groupcheckbox){
        this.groupcheckbox = groupcheckbox;
    }
    //ChiledCheckBox接口
    private ChiledCheckBox chiledcheckbox;
    public void setOnChiledCheckBox(ChiledCheckBox chiledcheckbox){
        this.chiledcheckbox = chiledcheckbox;
    }
    //加商品个数的接口
    private UpGeshu upgeshu;
    public void setOnUpGeshu(UpGeshu upgeshu){
        this.upgeshu = upgeshu;
    }
    //减商品个数的接口
    private DownGeshu downgeshu;
    public void setOnDownGeshu(DownGeshu downgeshu){
        this.downgeshu = downgeshu;
    }
    //删除商品的接口
    private Delete delete;
    public void setOnDelete(Delete delete){
        this.delete = delete;
    }
    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        final CartsBean.DataBean dataBean = data.get(groupPosition);
        final IsnoBean isnoBean = GroupIsno.get(groupPosition);
        final ViewHodler1 vh1;
        if(view==null){
            vh1 = new ViewHodler1();
            view = View.inflate(context, R.layout.store_item,null);
            vh1.CbStore = (CheckBox) view.findViewById(R.id.store_cb);
            vh1.TvStoreName = (TextView) view.findViewById(R.id.store_name_show);
            view.setTag(vh1);
        }else{
            vh1 = (ViewHodler1) view.getTag();
        }
        vh1.TvStoreName.setText(dataBean.getSellerName());
        //复选框
        vh1.CbStore.setChecked(isnoBean.isno());//false
        vh1.CbStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = vh1.CbStore.isChecked();
                groupcheckbox.onGroupBox(checked,groupPosition);
            }
        });
        //返回当前视图
        return view;

    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        //本商品
        final CartsBean.DataBean.ListBean listBean = data.get(i).getList().get(i1);
        //本商品的复选框的值
        final IsnoBean bisnoBean = ChiledIsno.get(i).get(i1);
        final ViewHolder2 vh2;
        if(view==null){
            vh2 = new ViewHolder2();
            view = View.inflate(context, R.layout.commodity_item,null);
            vh2.CbCommodity = (CheckBox) view.findViewById(R.id.cb_commodity_show);
            vh2.ImgCommodityPicture = (ImageView) view.findViewById(R.id.commodity_img);
            vh2.TvCommodityTitle = (TextView) view.findViewById(R.id.commodity_title);
            vh2.TvCommodityPrice = (TextView) view.findViewById(R.id.commodity_price);
            vh2.TvCommodityColor = (TextView) view.findViewById(R.id.commodity_color);
            vh2.TvCommoditySize = (TextView) view.findViewById(R.id.commodity_size);
            vh2.BtnCommodityUp = (Button) view.findViewById(R.id.commodity_geshu_up);
            vh2.BtnCommodityShow = (Button) view.findViewById(R.id.commodity_geshu_show);
            vh2.BtnCommodityDown = (Button) view.findViewById(R.id.commodity_geshu_down);
            vh2.BtnCommodityDelete = (Button) view.findViewById(R.id.commodity_delete);
            view.setTag(vh2);
        }else{
            vh2 = (ViewHolder2) view.getTag();
        }
        // String[] split = listBean.getImages().split("\\|");//vh2.ImgCommodityPicture
        String images = data.get(i).getList().get(i1).getImages();
        String[] split1 = images.split("\\|");
        Glide.with(context).load(split1[0]).into(vh2.ImgCommodityPicture);
        //ImageLoader.getInstance().displayImage(split[0],vh2.ImgCommodityPicture);
        vh2.TvCommodityTitle.setText(listBean.getTitle());
        vh2.TvCommodityPrice.setText("￥:"+(int)listBean.getBargainPrice());
        vh2.TvCommodityColor.setText("颜色:"+listBean.getPrice());
        vh2.TvCommoditySize.setText("尺寸:"+listBean.getPid()+"L");
        vh2.BtnCommodityShow.setText(listBean.getNum()+"");

        //复选框
        vh2.CbCommodity.setChecked(bisnoBean.isno());
        vh2.CbCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = vh2.CbCommodity.isChecked();
                chiledcheckbox.onChiledBox(checked,i,i1);
            }
        });

        //加个数
        vh2.BtnCommodityUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = vh2.BtnCommodityShow.getText().toString();
                int num = Integer.valueOf(s).intValue();
                num++;
                upgeshu.onUpGeshu(num,i,i1);
            }
        });

        //减个数
        vh2.BtnCommodityDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = vh2.BtnCommodityShow.getText().toString();
                int num = Integer.valueOf(s).intValue();
                num--;
                downgeshu.onDownGeshu(num,i,i1);
            }
        });

        //删除
        vh2.BtnCommodityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete.onDelete(i,i1);
            }
        });
        //返回当前视图
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class ViewHodler1{
        private CheckBox CbStore;
        private TextView TvStoreName;
    }
    class ViewHolder2{
        private CheckBox CbCommodity;
        private TextView TvCommodityTitle;
        private ImageView ImgCommodityPicture;
        private TextView TvCommodityPrice;
        private TextView TvCommodityColor;
        private TextView TvCommoditySize;
        private Button BtnCommodityUp;
        private Button BtnCommodityShow;
        private Button BtnCommodityDown;
        private Button BtnCommodityDelete;
    }
}
