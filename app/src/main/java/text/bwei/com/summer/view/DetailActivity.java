package text.bwei.com.summer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import text.bwei.com.summer.R;
import text.bwei.com.summer.bean.DetailBean;
import text.bwei.com.summer.presenter.PresenterAdd;
import text.bwei.com.summer.presenter.PresenterDetail;

public class DetailActivity extends AppCompatActivity implements IView {

    @BindView(R.id.detail_img)
    ImageView detailImg;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_title2)
    TextView detailTitle2;
    @BindView(R.id.detail_price)
    TextView detailPrice;
    @BindView(R.id.detail_button)
    Button detailButton;
    @BindView(R.id.detail_button2)
    Button detailButton2;
    private PresenterDetail presenterDetail;
    private String pid;
    private PresenterAdd presenterAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        pid=intent.getStringExtra("pid");
        presenterDetail = new PresenterDetail(this);
        Map<String,String> map=new HashMap<>();
        map.put("source","android");
        map.put("pid",pid);
        presenterDetail.getData("https://www.zhaoapi.cn/",map);

    }

    @Override
    public void Success(Object o) {
        DetailBean detailBean= (DetailBean) o;
        DetailBean.DataBean data = detailBean.getData();
        //图片
        String images = data.getImages();
        String[] split = images.split("!");
        Glide.with(this).load(split[0]).into(detailImg);
        //标题
        detailTitle.setText(data.getSubhead());
        detailTitle2.setText(data.getTitle());
        //价格
        detailPrice.setText("价格:"+data.getPrice());

    }

    @Override
    public void Failed(Throwable t) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterDetail.detechView();
        presenterAdd.delechView();
    }

    @OnClick({R.id.detail_button, R.id.detail_button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //加入购物车
            case R.id.detail_button:
                presenterAdd = new PresenterAdd(this,this);
                Map<String,String> map=new HashMap<>();
                map.put("source","android");
                map.put("uid","3809");
                map.put("pid",pid);
                presenterAdd.getData("http://120.27.23.105/",map);
//                CartsBean carts=new CartsBean();
//                if(carts.getCode().equals("0")){
//                    Toast.makeText(this,carts.getMsg(),Toast.LENGTH_SHORT).show();
//                }
                break;
            //进入购物车
            case R.id.detail_button2:
                Intent intent=new Intent(DetailActivity.this,CartsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
