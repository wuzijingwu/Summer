package text.bwei.com.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import text.bwei.com.summer.bean.LoginBean;
import text.bwei.com.summer.presenter.PresenterLogin;
import text.bwei.com.summer.view.IView;
import text.bwei.com.summer.view.ProductsActivity;
import text.bwei.com.summer.view.RegActivity;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.login_user)
    EditText loginUser;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_reg)
    TextView loginReg;
    @BindView(R.id.login_btn)
    Button loginBtn;
    private PresenterLogin presenterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void Success(Object o) {
        LoginBean loginBean= (LoginBean) o;
        if(loginBean.getMsg()!=null){
            Toast.makeText(this,loginBean.getMsg(),Toast.LENGTH_SHORT).show();
        }
        if(loginBean.getCode().equals("0")){
            //跳转列表
            Toast.makeText(this,loginBean.getMsg(),Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this, ProductsActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void Failed(Throwable t) {

    }

    @OnClick({R.id.login_reg, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_reg:
                Intent intent=new Intent(MainActivity.this, RegActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                presenterLogin = new PresenterLogin(this);
                Map<String,String> map=new HashMap<>();
                map.put("mobile",loginUser.getText().toString().trim());
                map.put("password",loginPassword.getText().toString().trim());
                presenterLogin.getData("http://120.27.23.105/",map);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterLogin.delethView();

    }
}
