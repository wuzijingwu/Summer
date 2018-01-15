package text.bwei.com.summer.retrofit;


import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import text.bwei.com.summer.bean.AddBean;
import text.bwei.com.summer.bean.CartsBean;
import text.bwei.com.summer.bean.DeleteBean;
import text.bwei.com.summer.bean.DetailBean;
import text.bwei.com.summer.bean.LoginBean;
import text.bwei.com.summer.bean.ProductsBean;
import text.bwei.com.summer.bean.RegBean;

/**
 * Created by z on 2018/1/11.
 */

public interface ApiService {
   /* 登陆
    http://120.27.23.105/user/login?mobile=17610781536&password=666666*/
    @GET("user/login")
    Flowable<LoginBean> getLogin(@QueryMap Map<String, String> map);

   /* 注册
    http://120.27.23.105/user/reg?mobile=17610781536&password=666666*/
   @GET("user/reg")
   Flowable<RegBean> getReg(@QueryMap Map<String, String> map);

   /*列表信息
   https://www.zhaoapi.cn/product/getProducts?pscid=39&page=1
   * */
   @GET("product/getProducts")
   Flowable<ProductsBean> getProducts(@QueryMap Map<String, String> map);

   /*商品详情
   *https://www.zhaoapi.cn/product/getProductDetail?source=android&pid=10
   * */
   @GET("product/getProductDetail")
   Flowable<DetailBean> getDetail(@QueryMap Map<String, String> map);

   /*添加购物车
   *http://120.27.23.105/product/addCart?source=android&uid=3809&pid=30
   * */
   @GET("product/addCart")
   Flowable<AddBean> getAdd(@QueryMap Map<String, String> map);

   /*查询购物车信息
   * http://120.27.23.105/product/getCarts?source=android&uid=3809
   * */
   @GET("product/getCarts")
   Flowable<CartsBean> getCarts(@QueryMap Map<String, String> map);

   /*删除购物车
   * http://120.27.23.105/product/deleteCart?source=android&uid=1653&pid=2
   * */
   @GET("product/deleteCart")
   Flowable<DeleteBean> getDelete(@QueryMap Map<String, String> map);


}
