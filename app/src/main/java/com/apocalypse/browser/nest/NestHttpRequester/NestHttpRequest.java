package com.apocalypse.browser.nest.NestHttpRequester;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.os.Handler;
import android.os.HandlerThread;
import com.apocalypse.browser.nest.utils.SimpleLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * http请求
 * 模式：post， get
 * Created by Dave on 2016/1/14.
 */

public class NestHttpRequest {

    public interface NestHttpCallback{
        void CallBack(NestHttpResultData data);
    }

    public static class NestHttpResultData{
        public Boolean isSucceed;
        public String filePath;
        public int responseCode;
        NestHttpResultData(){
            isSucceed = false;
            filePath = "";
            responseCode = 0;
        }
    }

    private static final int MSG_HTTP_GET_REQUEST = 1;
    private static final int MSG_HTTP_POST_REQUEST = 2;

    private static final String QUEUE_TASK_NAME = "NestHttpRequestQueueThread";
    private static final String DEBUG_TAG = "NestHttpRequest";

    private static NestHttpRequest mInstance;
    private Handler mHandler;
    private HandlerThread mTaskThread;

    public static NestHttpRequest getInstance(){
        if (mInstance == null) {
            mInstance = new NestHttpRequest();
            mInstance.initialize();
        }
        return mInstance;
    }

    private static NestHttpRequestData getArgs(String url, int connectTimeOut,
                                int downloadTimeOut, String cookies,
                                Object postObject, String outFilePath,
                                               NestHttpCallback callBack){

        NestHttpRequestData data = new NestHttpRequestData();
        data.url = url;
        data.connectTimeOut = connectTimeOut;
        data.downloadTimeOut = downloadTimeOut;
        data.cookies = cookies;
        data.postObject = postObject;
        data.filePath = outFilePath;
        data.callBack = callBack;

        return data;
    }

    public static NestHttpResultData postQueueTask(String url, int connectTimeOut,
                                              int downloadTimeOut, String cookies,
                                              Object postObject, String filePath) {

        NestHttpRequestData data = getArgs(url, connectTimeOut,
                downloadTimeOut, cookies, postObject, filePath, null);
        try {
            return getInstance().httpPostRequest(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new NestHttpResultData();
    }

    public static boolean postQueueTask(String url, int connectTimeOut,
                                        int downloadTimeOut, String cookies,
                                        Object postObject, String filePath,
                                        NestHttpCallback callBack){

        NestHttpRequestData data = getArgs(url, connectTimeOut, downloadTimeOut,
                cookies, postObject, filePath, callBack);

        Message msg  = Message.obtain();
        msg.obj = data;
        msg.what = MSG_HTTP_POST_REQUEST;

        return getInstance().mHandler.sendMessage(msg);
    }

    public static NestHttpResultData getQueueTask(String url, int connectTimeOut,
                                             int downloadTimeOut, String cookies,
                                                  String filePath)  {

        NestHttpRequestData data = getArgs(url, connectTimeOut, downloadTimeOut,
                cookies, null, filePath, null);

        try {
            return getInstance().httpGetRequest(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new NestHttpResultData();
    }


    public static boolean getQueueTask(String url, int connectTimeOut,
                                       int downloadTimeOut, String cookies,
                                       String filePath, NestHttpCallback callBack){

        NestHttpRequestData data = getArgs(url, connectTimeOut, downloadTimeOut,
                cookies, null, filePath, callBack);

        Message msg  = Message.obtain();
        msg.obj = data;
        msg.what = MSG_HTTP_GET_REQUEST;

        return getInstance().mHandler.sendMessage(msg);
    }

    private static class NestHttpRequestData{
        public String url;
        public int connectTimeOut;
        public int downloadTimeOut;
        public String cookies;
        public String filePath;
        public NestHttpCallback callBack;
        public Object postObject;
        NestHttpRequestData(){
            url = "";
            connectTimeOut = 0;
            downloadTimeOut = 0;
            cookies = "";
            filePath = "";
            callBack = null;
            postObject = null;
        }
    }

    protected NestHttpResultData httpGetRequest(NestHttpRequestData data) throws IOException {


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        NestHttpResultData resultData = new NestHttpResultData();

        try {
            URL url = new URL(data.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(data.downloadTimeOut);
            urlConnection.setConnectTimeout(data.connectTimeOut);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            // set other property
            if (data.cookies != null){
                urlConnection.setRequestProperty("Cookie", data.cookies);
            }

            urlConnection.connect();
            resultData.responseCode = urlConnection.getResponseCode();

            SimpleLog.d(DEBUG_TAG, "The response is: " + resultData.responseCode);

            resultData.isSucceed = true;
            resultData.filePath = data.filePath;

            inputStream = urlConnection.getInputStream();
            StreamToFile(inputStream, data.filePath);

        } catch(Exception e){
            SimpleLog.e(e);
        } finally {
            if (inputStream != null){
                inputStream.close();
            }
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return resultData;
    }

    protected NestHttpResultData httpPostRequest(NestHttpRequestData data) throws IOException {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        NestHttpResultData resultData = new NestHttpResultData();

        try {

            URL url = new URL(data.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(data.downloadTimeOut);
            urlConnection.setConnectTimeout(data.connectTimeOut);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            // set other property
            if (data.cookies != null){
                urlConnection.setRequestProperty("Cookie", data.cookies);
            }

            urlConnection.connect();
            resultData.responseCode = urlConnection.getResponseCode();
                    //设置post数据
                    if (data.postObject != null){
                        OutputStream outputStream = urlConnection.getOutputStream();
                        if (outputStream != null){
                            ObjectOutputStream objOutputStrm = new ObjectOutputStream(outputStream);
                            if (outputStream != null){
                                objOutputStrm.writeObject(data.postObject);
                                objOutputStrm.flush();
                                objOutputStrm.close();
                            }
                    outputStream.close();
                }
            }

            SimpleLog.d(DEBUG_TAG, "The response is: " + resultData.responseCode);
            resultData.isSucceed = true;
            resultData.filePath = data.filePath;

            inputStream = urlConnection.getInputStream();
            StreamToFile(inputStream, data.filePath);
        } catch(Exception e){

            SimpleLog.e(e);

        } finally {
            if (inputStream != null){
                inputStream.close();
            }
            // inputStream must be close somewhere
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return resultData;
    }

    protected void initialize(){
        mTaskThread = new HandlerThread(QUEUE_TASK_NAME);
        mTaskThread.start();
        mHandler = new Handler(mTaskThread.getLooper(), new QueueHandler());
    }

    protected class QueueHandler implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            SimpleLog.d(DEBUG_TAG, "handleMessage");
            NestHttpResultData resultData = null;
            NestHttpRequestData msgData = (NestHttpRequestData)msg.obj;
            try {
                switch (msg.what){
                    case MSG_HTTP_GET_REQUEST:{
                        resultData =  httpGetRequest(msgData);
                        break;
                    }
                    case MSG_HTTP_POST_REQUEST:{
                        resultData = httpPostRequest(msgData);
                        break;
                    }
                    default:
                        break;
                }

                if (resultData != null) {
                    //callback
                    if (msgData.callBack != null){
                        msgData.callBack.CallBack(resultData);
                    }
                }

            }catch (IOException o) {
                SimpleLog.e(o);
            }

            return true;
        }
    }

    protected void StreamToFile(InputStream inputStream, String filePath){
        if (inputStream == null)
            return ;

        final int READ_BUFF_SIZE = 1024 * 4;

        OutputStream output = null;
        try {
            File file  = new File(filePath);

            if(!file.exists()) {
                if(!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }

            output = new FileOutputStream(file);
            byte buffer[] = new byte[READ_BUFF_SIZE];
            while((inputStream.read(buffer)) != -1) {
                output.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (output != null)
                    output.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
