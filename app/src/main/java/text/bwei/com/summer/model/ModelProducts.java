package text.bwei.com.summer.model;


import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.summer.bean.ProductsBean;
import text.bwei.com.summer.presenter.PresenterProducts;
import text.bwei.com.summer.retrofit.RetrofitUtils;

/**
 * Created by z on 2018/1/11.
 */

public class ModelProducts implements IModel{
    private PresenterProducts presenterProducts;

    public ModelProducts(PresenterProducts presenterProducts) {
        this.presenterProducts = presenterProducts;
    }

    @Override
    public void getData(String basrUrl, Map<String, String> map) {
        Flowable<ProductsBean> flowable = RetrofitUtils.getInstance(basrUrl).getApiService().getProducts(map);
        presenterProducts.getNews(flowable);
    }
}
