package text.bwei.com.summer.presenter;

import android.util.Log;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import text.bwei.com.summer.bean.CartsBean;
import text.bwei.com.summer.model.CardIModel;
import text.bwei.com.summer.model.ModelCarts;
import text.bwei.com.summer.view.IView;

/**
 * Created by z on 2018/1/13.
 */

public class PresenterCarts implements CardIPresenter{
    private IView iView;
    private DisposableSubscriber subscriber;
    public PresenterCarts(IView iView) {
        this.iView = iView;
    }
    public void deletchView(){
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
        CardIModel model=new ModelCarts(this);
        model.getData(baseUrl, map);
    }
    public void getNews(Flowable<CartsBean> flowable){
        subscriber = (DisposableSubscriber) flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<CartsBean>() {
                    @Override
                    public void onNext(CartsBean cartsBean) {
                        Log.e("PresenterCards",cartsBean.getMsg());
                        iView.Success(cartsBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("PresenterCards","falsed");
                        iView.Failed(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*subscriber=(DisposableSubscriber) flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<CartsBean>() {
                    @Override
                    public void onNext(CartsBean cartsBean) {
                        Log.i("cccccccccc",cartsBean.toString());
                        iView.Success(cartsBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        iView.Failed(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }
}
