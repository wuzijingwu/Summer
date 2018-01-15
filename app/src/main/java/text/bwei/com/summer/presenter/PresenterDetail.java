package text.bwei.com.summer.presenter;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import text.bwei.com.summer.bean.DetailBean;
import text.bwei.com.summer.model.IModel;
import text.bwei.com.summer.model.ModelDetail;
import text.bwei.com.summer.view.IView;

/**
 * Created by z on 2018/1/12.
 */

public class PresenterDetail implements IPresenter{
    private IView iView;
    private DisposableSubscriber<DetailBean> subscriber;

    public PresenterDetail(IView iView) {
        this.iView = iView;
    }
    public void detechView(){
        if(iView!=null){
            iView=null;
        }
        if(subscriber!=null){
            if(!subscriber.isDisposed()){
                subscriber.dispose();
            }
        }
    }

    @Override
    public void getData(String baseUrl, Map<String, String> map) {
        IModel model=new ModelDetail(this);
        model.getData(baseUrl, map);
    }
    public void getNews(Flowable<DetailBean> flowable){
        subscriber=flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<DetailBean>() {
                    @Override
                    public void onNext(DetailBean detailBean) {
                        iView.Success(detailBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        iView.Failed(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
