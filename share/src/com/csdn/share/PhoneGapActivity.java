/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.csdn.share;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.csdn.share.util.bean.Care;
import com.csdn.share.util.bean.Share;
import com.csdn.share.util.dao.CareDao;
import com.csdn.share.util.dao.ShareDao;

import org.apache.cordova.DroidGap;
import org.json.JSONObject;

import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;


public class PhoneGapActivity extends DroidGap {
    public ShareDao shareDao;
    public CareDao careDao;
    public List<Share> list;
    public PromptDialog promptDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shareDao = new ShareDao(PhoneGapActivity.this);
        careDao=new CareDao(PhoneGapActivity.this);

        promptDialog = new PromptDialog(this);

        super.init();
        appView.getSettings().setJavaScriptEnabled(true);

        appView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        appView.addJavascriptInterface(new PluginMethod(appView), "SM"); // 注意这里一句

        super.loadUrl("file:///android_asset/index.html");

    }

    /**
     * Created with IntelliJ IDEA.
     * User: FakeMr
     * Date: 13-7-15
     * Time: 下午4:12
     * To change this template use File | Settings | File Templates.
     */
    class PluginMethod {
        private WebView webView;


        public PluginMethod(WebView view) {
            webView = view;

        }


        @JavascriptInterface
        public void UpdateApp(final String path) {

            Log.e("---------------", path);  //注意这里日志输出
        }

        @JavascriptInterface
        public void delete2(String code) {
            int i = careDao.deleteShare(code);
            if (i > 0) {
                PhoneGapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.showInfo("删除成功");
                    }
                });
            }
        }


        @JavascriptInterface
        public void delete(String code) {
            int i = shareDao.deleteShare(code);
            if (i > 0) {
                PhoneGapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.showInfo("删除成功");
                    }
                });
            }
        }


        @JavascriptInterface
        public void UpdateCare(final String shareStr) {
            try {

                JSONObject jsonObject = new JSONObject(shareStr);
                Care care = new Care();
                care.setName(jsonObject.optString("name"));
                care.setCode(jsonObject.optString("code"));
                care.setRemark(jsonObject.optString("remark"));


                boolean isExit = careDao.query(jsonObject.optString("code"));
                if (isExit) {
                    PhoneGapActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptDialog.showInfo("关注已经存在");
                        }
                    });

                } else {
                    long l = careDao.add(care);
                    if (l >= 0) {
                        PhoneGapActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                promptDialog.showInfo("关注添加成功");
                            }
                        });
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @JavascriptInterface
        public void UpdateShare(final String shareStr) {


            try {

                JSONObject jsonObject = new JSONObject(shareStr);
                Share share = new Share();
                share.setName(jsonObject.optString("name"));
                share.setCode(jsonObject.optString("code"));
                share.setShape(jsonObject.optString("shape"));
                share.setDate(jsonObject.optString("date"));
                share.setRemark(jsonObject.optString("remark"));


                boolean isExit = shareDao.query(jsonObject.optString("code"));
                if (isExit) {
                    PhoneGapActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptDialog.showInfo("已经存在");
                        }
                    });

                } else {
                    long l = shareDao.add(share);
                    if (l >= 0) {
                        PhoneGapActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                promptDialog.showInfo("添加成功");
                            }
                        });

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @JavascriptInterface
        public void push() {
            list = shareDao.getALL();

            final String json = getJson(list);

                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:callJs('" + json + "')");
                    }
                });
        }
        @JavascriptInterface
        public void push1() {
         List<Care>   list = careDao.getALL();

            final String json = getJson1(list);

            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:callJs('" + json + "')");
                }
            });
        }

    }

    public String getJson(List<Share> list) {

        if(list.size()==0){
            return "";
        }


        String json = "[";
        for (Share share : list) {
            json += "{\"code\":\"" + share.getCode() + "\"," +
                    "\"name\":\"" + share.getName() + "\"," +
                    "\"date\":\"" + share.getDate() + "\"," +
                    "\"shape\":\"" + share.getShape() + "\"," +
                    "\"remark\":\"" + share.getRemark() + "\"},";

        }

        json= json.substring(0, json.length() - 1);
        json += "]";
        return json;

    }
    public String getJson1(List<Care> list) {

        if(list.size()==0){
            return "";
        }


        String json = "[";
        for (Care share : list) {
            json += "{\"code\":\"" + share.getCode() + "\"," +
                    "\"name\":\"" + share.getName() + "\"," +
                    "\"remark\":\"" + share.getRemark() + "\"},";

        }

        json= json.substring(0, json.length() - 1);
        json += "]";
        return json;

    }


}

