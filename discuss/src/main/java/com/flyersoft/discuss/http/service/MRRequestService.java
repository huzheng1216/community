package com.flyersoft.discuss.http.service;

import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.Book;
import com.flyersoft.discuss.javabean.BookAndDiscuss;
import com.flyersoft.discuss.javabean.BookContent;
import com.flyersoft.discuss.javabean.BookDetail;
import com.flyersoft.discuss.javabean.CatalogDetail;
import com.flyersoft.discuss.javabean.ChargeRecords;
import com.flyersoft.discuss.javabean.CommentAndDiscuss;
import com.flyersoft.discuss.javabean.DefaultCode;
import com.flyersoft.discuss.javabean.DefaultInfo;
import com.flyersoft.discuss.javabean.DetailBookInfo;
import com.flyersoft.discuss.javabean.DetailCatalogDetail;
import com.flyersoft.discuss.javabean.DisRecordResult;
import com.flyersoft.discuss.javabean.ShelfBook;
import com.flyersoft.discuss.javabean.account.AmountInfo;
import com.flyersoft.discuss.javabean.account.Collection;
import com.flyersoft.discuss.javabean.account.PayConfig;
import com.flyersoft.discuss.javabean.account.TencentYunConfig;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.account.WXLandingConfig;
import com.flyersoft.discuss.javabean.account.ZFBLandingConfig;
import com.flyersoft.discuss.javabean.seekbook.BookList;
import com.flyersoft.discuss.javabean.seekbook.BookListInfo;
import com.flyersoft.discuss.javabean.seekbook.Comments;
import com.flyersoft.discuss.javabean.seekbook.Discuss;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 请求服务接口
 * Created by Administrator on 2016/9/23.
 */
public interface MRRequestService {

    @GET("mreader/common/listActivity.do")
    Observable<BaseRequest<List<Book>>> getActivityBooks();

    @FormUrlEncoded
    @POST("mreader/common/getModuleData.do")
    Observable<BaseRequest<List<BookDetail>>> getActivityBooks(@Field("moduleId") String moduleId, @Field("from")int from, @Field("limit")int limit );

    @POST("mreader/common/getCategory.do")
    Observable<BaseRequest<List<CatalogDetail>>> getCatalogBooks();

    @FormUrlEncoded
    @POST("mreader/common/queryCatalog.do")
    Observable<BaseRequest<List<DetailCatalogDetail>>> getCatalogBooks(@Field("bookId") String bookId, @Field("from")int from, @Field("maxCount")int maxCount);

    @FormUrlEncoded
    @POST("mreader/common/search.do")
    Observable<BaseRequest<List<BookDetail>>> getSearchBooks(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("mreader/common/getBookInfo.do")
    Observable<BaseRequest<DetailBookInfo>> getDetailBook(@Field("bookId") String bookId);

    @GET("mreader/third/beforeLogin.do?model=app")
    Observable<BaseRequest<WXLandingConfig>> getLandingConfig(@Query("app") String app);

    @GET("mreader/third/beforeLogin.do?model=app&app=alipay")
    Observable<ZFBLandingConfig> getZFBLandingConfig();

    @POST("mreader/u/getUserInfo.do")
    Observable<BaseRequest<UserInfo>> getUserInfo();

    @FormUrlEncoded
    @POST("mreader/u/getBuyBookRecords.do")
    Observable<BaseRequest<List<BookDetail>>> getBuyBookRecords(@Field("from")int from, @Field("limit")int limit);

    @FormUrlEncoded
    @POST("mreader/u/chargeRecords.do")
    Observable<BaseRequest<List<ChargeRecords>>> getChargeRecords(@Field("from")int from, @Field("limit")int limit);

    @GET("mreader/third/appThirdLogin.do")
    Observable<BaseRequest<UserInfo>> uploadCode(@QueryMap Map<String, String> map);

    @GET("mreader/third/beforePay.do")
    Observable<BaseRequest<PayConfig>> getWXPayInfo(@QueryMap Map<String, String> map);

    @GET("mreader/third/beforePay.do")
    Observable<DefaultInfo> getZFBPayInfo(@QueryMap Map<String, String> map);

    @GET("mreader/u/getAmount.do")
    Observable<AmountInfo> getAmount();

    @FormUrlEncoded
    @POST("mreader/u/batchBuy.do")
    Observable<BaseRequest> buyBook(@Field("bookId")String bookId, @Field("chapterNums")String chapterNums, @Field("autoDebits")boolean autoDebits);

    @FormUrlEncoded
    @POST("mreader/u/add2self.do")
    Observable<BaseRequest<BookDetail>> addToSelf(@Field("bookId")String bookId);

    @FormUrlEncoded
    @POST("mreader/common/ifInSelf.do")
    Observable<BaseRequest<DefaultCode>> ifInSelf(@Field("bookId")String bookId);

    @FormUrlEncoded
    @POST("mreader/u/getMyShelf.do")
    Observable<BaseRequest<List<ShelfBook>>> getMyShelf(@Field("from")int from, @Field("limit")int limit);

    @FormUrlEncoded
    @POST("mreader/common/getContent.do")
    Observable<BaseRequest<BookContent>> getContent(@Field("bookId")String bookId, @Field("chapterNo")int chapterNo, @Field("direction")String direction);

    @POST("mreader/u/logOut.do")
    Observable<BaseRequest<Boolean>> logOut();

    @FormUrlEncoded
    @POST("mreader/discuss/add.do")
    Observable<BaseRequest> addDiscuss(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("mreader/discuss/findDiscuss.do")
    Observable<BaseRequest<List<Discuss>>> getDiscuss(@Field("type")int type, @Field("from")int from,  @Field("maxCount")int maxCount,  @Field("userName")String userName,  @Field("sortType")int sortType);

    @FormUrlEncoded
    @POST("mreader/discuss/findComms.do")
    Observable<BaseRequest<List<Comments>>> getComments(@Field("discussId")String discussId, @Field("from")int from, @Field("maxCount")int maxCount, @Field("userName")String userName);

    @FormUrlEncoded
    @POST("mreader/discuss/commAdd.do")
    Observable<BaseRequest> submitComment(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("mreader/discuss/disRecord.do")
    Observable<BaseRequest<DisRecordResult>> disRecord(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("mreader/u/movementAdd.do")
    Observable<BaseRequest> movementAdd(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("mreader/discuss/findData.do")
    Observable<BaseRequest<List<Discuss>>> findData(@Field("sortType")String softType, @Field("from")int from, @Field("maxCount")int maxCount, @Field("userName")String userName, @Field("type")String type);

    @FormUrlEncoded
    @POST("mreader/u/queryDataOfUser.do")
    Observable<BaseRequest<Collection>> queryDataOfUser(@Field("userName")String userName, @Field("from")int from, @Field("maxCount")int maxCount);

    @FormUrlEncoded
    @POST("mreader/discuss/findMovement.do")
    Observable<BaseRequest<List<Discuss>>> queryActionData(@Field("userName")String userName, @Field("from")int from, @Field("maxCount")int maxCount);

    @FormUrlEncoded
    @POST("mreader/common/queryBook.do")
    Observable<BaseRequest<List<Book>>> queryBook(@Field("userName")String userName, @Field("bookName")String name, @Field("author")String auther);

    @FormUrlEncoded
    @POST("mreader/discuss/querySBook.do")
    Observable<BaseRequest<List<BookAndDiscuss>>> querySBook(@Field("userName")String userName, @Field("bookName")String name, @Field("bookAuthor")String auther);

    @FormUrlEncoded
    @POST("mreader/discuss/dataByBookTwo.do")
    Observable<BaseRequest<CommentAndDiscuss>> getBookInfo(@Field("bookName")String bookName, @Field("bookAuthor")String bookAuthor, @Field("userName")String userName, @Field("from")int from, @Field("maxCount")int maxCount);

    @POST("mreader/third/signOfFileCOS.do?scene=1&type=1&&cosPath=/ss.jpg")
    Observable<BaseRequest<TencentYunConfig>> getTencentYunConfig();

    @POST("mreader/u/queryAttenByUser.do")
    Observable<BaseRequest<List<UserInfo>>> queryAttenByUser();

    @FormUrlEncoded
    @POST("mreader/u/addBookList.do")
    Observable<BaseRequest> addBookList(@FieldMap Map<String,Object> map);

    @GET("mreader/discuss/queryBookLists.do")
    Observable<BaseRequest<List<BookList>>> queryBookLists(@Query("skip")int skip, @Query("maxCount")int maxCount);

    @GET("mreader/discuss/queryBookListInfo.do")
    Observable<BaseRequest<List<BookListInfo>>> queryBookList(@Query("listId")String listId, @Query("skip")int skip, @Query("maxCount")int maxCount);
}
