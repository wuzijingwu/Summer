package text.bwei.com.summer.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import text.bwei.com.summer.bean.AddBean;
import text.bwei.com.summer.model.IModel;
import text.bwei.com.summer.model.ModelAdd;
import text.bwei.com.summer.view.IView;

/**
 * Created by z on 2018/1/12.
 */

public class PresenterAdd implements IPresenter{
    private IView iView;
    private Context context;
    private DisposableSubscriber<AddBean> subscriber;

    public PresenterAdd(IView iView,Context context) {
        this.iView = iView;
        this.context=context;
    }

    public void delechView(){
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
        IModel model=new ModelAdd(this);
        model.getData(baseUrl, map);
    }
    public void getNews(Flowable<AddBean> flowable){
        subscriber=flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<AddBean>() {
                    @Override
                    public void onNext(AddBean addBean) {
                        if(addBean.getCode().equals("0")){
                            Toast.makeText(context,addBean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
