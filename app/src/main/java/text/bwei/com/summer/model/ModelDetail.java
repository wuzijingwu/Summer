package text.bwei.com.summer.model;


import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.summer.bean.DetailBean;
import text.bwei.com.summer.presenter.PresenterDetail;
import text.bwei.com.summer.retrofit.RetrofitUtils;

/**
 * Created by z on 2018/1/12.
 */

public class ModelDetail implements IModel{
    private PresenterDetail presenterDetail;

    public ModelDetail(PresenterDetail presenterDetail) {
        this.presenterDetail = presenterDetail;
    }

    @Override
    public void getData(String basrUrl, Map<String, String> map) {
        Flowable<DetailBean> flowable = RetrofitUtils.getInstance(basrUrl).getApiService().getDetail(map);
        presenterDetail.getNews(flowable);
    }
}
