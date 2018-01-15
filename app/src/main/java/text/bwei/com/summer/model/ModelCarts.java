package text.bwei.com.summer.model;


import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.summer.bean.CartsBean;
import text.bwei.com.summer.presenter.PresenterCarts;
import text.bwei.com.summer.retrofit.RetrofitUtils;

/**
 * Created by z on 2018/1/13.
 */

public class ModelCarts implements CardIModel{
    private PresenterCarts presenterCarts;

    public ModelCarts(PresenterCarts presenterCarts) {
        this.presenterCarts = presenterCarts;
    }

    @Override
    public void getData(String basrUrl, Map<String, String> map) {
        Flowable<CartsBean> flowable = RetrofitUtils.getInstance(basrUrl).getApiService().getCarts(map);
        presenterCarts.getNews(flowable);
    }
}
