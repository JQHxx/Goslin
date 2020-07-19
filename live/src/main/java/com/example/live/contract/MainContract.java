package com.example.live.contract;

import com.example.commonlibrary.base.BaseObserver;
import com.example.commonlibrary.base.BasePresenter;
import com.example.commonlibrary.base.BaseView;
import com.example.commonlibrary.net.NetException;
import com.example.commonlibrary.net.manager.RetrofitManager;
import com.example.live.api.API;
import com.example.live.bean.PublicAccountBean;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainContract {
    /*
     * Activity实现的接口
     * */
    public interface IView extends BaseView {
        void doSomething(PublicAccountBean publicAccountBean);
    }

    public static class Prensenter extends BasePresenter<IView> {
        private API api;
        private Gson gson;

        public Prensenter(IView mView) {
            super(mView);
            api = RetrofitManager.getInstance().createApi(API.class);
            gson = new Gson();
        }

        public void getPublicAccountList() {
            Map<String, String> map = new HashMap<>();
            api.getPublicAccountList(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycle.bindUntilEvent(mView.getLifeCycleSubject(), ActivityEvent.DESTROY))
                    .doOnSubscribe(disposable -> mView.showLoading())
                    .doFinally(() -> mView.hideLoading())
                    .subscribe(new BaseObserver<PublicAccountBean>() {
                        @Override
                        public void success(PublicAccountBean publicAccountBean) {
                            mView.doSomething(publicAccountBean);
                        }

                        @Override
                        public void error(NetException.ResponseException e) {
                            //失败。
                        }
                    });
        }
    }
}