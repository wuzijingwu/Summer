package text.bwei.com.summer.presenter;


import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import text.bwei.com.summer.bean.ProductsBean;
import text.bwei.com.summer.model.IModel;
import text.bwei.com.summer.model.ModelProducts;
import text.bwei.com.summer.view.IView;

/**
 * Created by z on 2018/1/11.
 */

public class PresenterProducts implements IPresenter{
    private IView iView;
    private DisposableSubscriber<ProductsBean> subscriber;

    public PresenterProducts(IView iView) {
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
        IModel model=new ModelProducts(this);
        model.getData(baseUrl, map);
    }
    public void getNews(Flowable<ProductsBean> flowable){
                subscriber=flowable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSubscriber<ProductsBean>() {
                            @Override
                            public void onNext(ProductsBean productsBean) {
                                iView.Success(productsBean);
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
