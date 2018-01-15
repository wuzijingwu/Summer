package text.bwei.com.summer.model;


import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.summer.bean.LoginBean;
import text.bwei.com.summer.presenter.PresenterLogin;
import text.bwei.com.summer.retrofit.RetrofitUtils;

/**
 * Created by z on 2018/1/11.
 */

public class ModelLogin implements IModel{
    private PresenterLogin presenterLogin;

    public ModelLogin(PresenterLogin presenterLogin) {
        this.presenterLogin = presenterLogin;
    }

    @Override
    public void getData(String basrUrl, Map<String, String> map) {
        Flowable<LoginBean> flowable = RetrofitUtils.getInstance(basrUrl).getApiService().getLogin(map);
        presenterLogin.getNews(flowable);
    }
}
