package text.bwei.com.summer.model;


import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.summer.bean.RegBean;
import text.bwei.com.summer.presenter.PresenterReg;
import text.bwei.com.summer.retrofit.RetrofitUtils;

/**
 * Created by z on 2018/1/11.
 */

public class ModelReg implements IModel{
    private PresenterReg presenterReg;

    public ModelReg(PresenterReg presenterReg) {
        this.presenterReg = presenterReg;
    }

    @Override
    public void getData(String basrUrl, Map<String, String> map) {
        Flowable<RegBean> flowable = RetrofitUtils.getInstance(basrUrl).getApiService().getReg(map);
        presenterReg.getNews(flowable);
    }
}
