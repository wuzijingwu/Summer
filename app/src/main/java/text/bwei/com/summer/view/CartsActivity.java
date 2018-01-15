package text.bwei.com.summer.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import text.bwei.com.summer.R;
import text.bwei.com.summer.adapter.ExpandableListViewAdpt;
import text.bwei.com.summer.bean.CartsBean;
import text.bwei.com.summer.bean.IsnoBean;
import text.bwei.com.summer.intface.ChiledCheckBox;
import text.bwei.com.summer.intface.Delete;
import text.bwei.com.summer.intface.DownGeshu;
import text.bwei.com.summer.intface.GroupCheckBox;
import text.bwei.com.summer.intface.UpGeshu;
import text.bwei.com.summer.presenter.PresenterCarts;

public class CartsActivity extends AppCompatActivity implements View.OnClickListener,IView,GroupCheckBox,ChiledCheckBox,UpGeshu,DownGeshu,Delete {
    List<CartsBean.DataBean> data;
    ExpandableListView exdblv;
    ExpandableListViewAdpt ExdblvAdpt;

    List<IsnoBean> GroupIsno;
    List<List<IsnoBean>> ChiledIsno;

    CheckBox CbAll;
    TextView TvAllPrice;
    TextView TvAllGeshu;
    Button BtnJiesuan;

    int allprice = 0;
    int allgeshu = 0;
    private PresenterCarts presenterCarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        data=new ArrayList<CartsBean.DataBean>();
        exdblv = (ExpandableListView) findViewById(R.id.expandablelistview_show);
        CbAll = (CheckBox) findViewById(R.id.cb_all);
        TvAllPrice = (TextView) findViewById(R.id.tv_all_price);
        TvAllGeshu = (TextView) findViewById(R.id.tv_all_geshu);
        BtnJiesuan = (Button) findViewById(R.id.bt_jeisuan);

        //P层
        presenterCarts = new PresenterCarts(this);
        Map<String,String> map=new HashMap<>();
        map.put("source","android");
        map.put("uid","3809");
        presenterCarts.getData("http://120.27.23.105/",map);

        //总的复选框点击事件
        CbAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cb_all:
                boolean checked = CbAll.isChecked();
                for(int i=0;i<GroupIsno.size();i++){
                    GroupIsno.get(i).setIsno(checked);
                    List<IsnoBean> isnoBeen = ChiledIsno.get(i);
                    for(int j=0;j<isnoBeen.size();j++){
                        isnoBeen.get(j).setIsno(checked);
                    }
                }
                ExdblvAdpt.notifyDataSetChanged();
                ZongjiaAndGeshu();
                break;
        }
    }

    @Override
    public void Success(Object o) {
        CartsBean cartsBean= (CartsBean) o;
        List<CartsBean.DataBean> data1 = cartsBean.getData();
        data.addAll(data1);
        Log.i("aaaaaaaaaa",data1.toString());
        //复选框选择状态的集合
        GroupIsno = new ArrayList<IsnoBean>();
        ChiledIsno = new ArrayList<List<IsnoBean>>();
        for(int i=0;i<data.size();i++){
            GroupIsno.add(new IsnoBean(false));
            List<IsnoBean> li = new ArrayList<IsnoBean>();
            for(int j=0;j<data.get(i).getList().size();j++){
                li.add(new IsnoBean(false));
            }
            ChiledIsno.add(li);
        }

        //适配器
        ExdblvAdpt = new ExpandableListViewAdpt(this,data,GroupIsno,ChiledIsno);
        ExdblvAdpt.setOnGroupCheckBox(this);
        ExdblvAdpt.setOnChiledCheckBox(this);
        ExdblvAdpt.setOnUpGeshu(this);
        ExdblvAdpt.setOnDownGeshu(this);
        ExdblvAdpt.setOnDelete(this);

        //配置适配器
        exdblv.setAdapter(ExdblvAdpt);
        ExdblvAdpt.notifyDataSetChanged();

        int groupCount = exdblv.getCount();
        for (int i=0; i<groupCount; i++) {
            exdblv.expandGroup(i);
        };
        exdblv.setGroupIndicator(null);
    }

    @Override
    public void Failed(Throwable t) {
        Log.i("bbbbbbbbb","失败");
    }

    @Override
    public void onDelete(int i, int i1) {
        data.get(i).getList().remove(i1);
        ChiledIsno.get(i).remove(i1);

        GaiGroupChexkBox(i);

        DeleteDianpu(i);

        ZongjiaAndGeshu();
    }

    @Override
    public void onUpGeshu(int num, int i, int i1) {
        data.get(i).getList().get(i1).setNum(num);
        ExdblvAdpt.notifyDataSetChanged();
        ZongjiaAndGeshu();
    }

    @Override
    public void onDownGeshu(int num, int i, int i1) {
        data.get(i).getList().get(i1).setNum(num);
        if(data.get(i).getList().get(i1).getNum()==0){
            data.get(i).getList().remove(i1);
            ChiledIsno.get(i).remove(i1);
        }
        GaiGroupChexkBox(i);

        DeleteDianpu(i);

        ZongjiaAndGeshu();
    }

    @Override
    public void onGroupBox(boolean isno, int i) {
        GroupIsno.get(i).setIsno(isno);
        List<IsnoBean> isnoBeen = ChiledIsno.get(i);
        for(int u=0;u<isnoBeen.size();u++){
            isnoBeen.get(u).setIsno(isno);
        }
        ExdblvAdpt.notifyDataSetChanged();

        AllCheckBox();

        ZongjiaAndGeshu();
    }

    @Override
    public void onChiledBox(boolean isno, int i, int i1) {
        ChiledIsno.get(i).get(i1).setIsno(isno);

        GaiGroupChexkBox(i);

        ExdblvAdpt.notifyDataSetChanged();

        AllCheckBox();

        ZongjiaAndGeshu();
    }
    //算总价格和总个数---每次点击事件完之后都调用此方法
    public void ZongjiaAndGeshu(){
        allprice = 0;
        allgeshu = 0;
        for(int i=0;i<ChiledIsno.size();i++){
            List<IsnoBean> isnoBeen = ChiledIsno.get(i);
            for(int j=0;j<isnoBeen.size();j++){
                CartsBean.DataBean.ListBean listBean = data.get(i).getList().get(j);
                if(isnoBeen.get(j).isno()){
                    allprice+=(int)(listBean.getNum()*listBean.getBargainPrice());
                    allgeshu+=listBean.getNum();
                }
            }
        }
        TvAllPrice.setText("总价:￥"+allprice);
        TvAllGeshu.setText("共"+allgeshu+"件商品");
    }
    //根据所有GroupCheckBox的状态,改变总的复选框状态
    private void AllCheckBox() {
        int e = 0;
        for(int r=0;r<GroupIsno.size();r++){
            if(!GroupIsno.get(r).isno()){
                e = 1;
            }
        }
        if(e==0){
            CbAll.setChecked(true);
        }else{
            CbAll.setChecked(false);
        }
    }
    //根据商品的复选框改变店铺的复选框状态
    private void GaiGroupChexkBox(int i) {
        int t = 0;
        for(int p=0;p<ChiledIsno.get(i).size();p++){
            if(!ChiledIsno.get(i).get(p).isno()){
                t = 1;
            }
        }
        if(t==0){
            GroupIsno.get(i).setIsno(true);
        }else{
            GroupIsno.get(i).setIsno(false);
        }
    }
    //删除店铺
    private void DeleteDianpu(int i) {
        if(data.get(i).getList()==null||data.get(i).getList().size()<1){
            data.remove(i);
            GroupIsno.remove(i);
            ChiledIsno.remove(i);
        }
        ExdblvAdpt.notifyDataSetChanged();
        AllCheckBox();
        if(data==null||data.size()<1){
            CbAll.setChecked(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterCarts.deletchView();
    }
}
