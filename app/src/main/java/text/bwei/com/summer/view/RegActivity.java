package text.bwei.com.summer.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import text.bwei.com.summer.R;
import text.bwei.com.summer.bean.RegBean;
import text.bwei.com.summer.presenter.PresenterReg;

public class RegActivity extends AppCompatActivity implements IView{

    @BindView(R.id.reg_user)
    EditText regUser;
    @BindView(R.id.reg_password)
    EditText regPassword;
    @BindView(R.id.reg_btn)
    Button regBtn;
    private PresenterReg presenterReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.reg_btn)
    public void onViewClicked() {
        presenterReg = new PresenterReg(this);
        Map<String,String> map=new HashMap<>();
        map.put("mobile",regUser.getText().toString().trim());
        map.put("password",regPassword.getText().toString().trim());
        presenterReg.getData("http://120.27.23.105/",map);
    }

    @Override
    public void Success(Object o) {
        RegBean regBean= (RegBean) o;
        if(regBean.getCode().equals("0")){
            Toast.makeText(this,regBean.getMsg(),Toast.LENGTH_SHORT).show();
            finish();
        }else if(regBean.getCode().equals("1")){
            Toast.makeText(this,regBean.getMsg(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void Failed(Throwable t) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterReg.delethView();
    }
}
