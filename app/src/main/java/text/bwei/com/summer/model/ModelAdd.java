package text.bwei.com.summer.model;


import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.summer.bean.AddBean;
import text.bwei.com.summer.presenter.PresenterAdd;
import text.bwei.com.summer.retrofit.RetrofitUtils;

/**
 * Created by z on 2018/1/12.
 */

public class ModelAdd implements IModel{
    private PresenterAdd presenterAdd;

    public ModelAdd(PresenterAdd presenterAdd) {
        this.presenterAdd = presenterAdd;
    }

    @Override
    public void getData(String basrUrl, Map<String, String> map) {
        Flowable<AddBean> flowable = RetrofitUtils.getInstance(basrUrl).getApiService().getAdd(map);
        presenterAdd.getNews(flowable);
    }
}
