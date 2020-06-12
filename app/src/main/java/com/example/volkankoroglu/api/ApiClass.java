package com.example.volkankoroglu.api;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.example.volkankoroglu.models.ApiPostParam;
import com.example.volkankoroglu.models.DataList;
import com.example.volkankoroglu.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiClass {
    private static final String TAG = ApiClass.class.getSimpleName();


    private String _baseUrl = "https://reqres.in/api/";
    private String _getDataListUrl = _baseUrl + "users";


    private String param_response_data = "data";
    private String param_response_user_id = "id";
    private String param_response_user_email = "email";
    private String param_response_user_first_name = "first_name";
    private String param_response_user_last_name = "last_name";
    private String param_response_user_avatar = "avatar";


    public int hnd_param_data_list = 101;

    private static Retrofit retrofit = null;
    private static String Base_Url = "https://reqres.in/";


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .build();
            return retrofit;
        }
        return retrofit;
    }


    public void getDataList(final Handler hnd) {
        //showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {

                RestInterface restInterface = ApiClass.getClient().create(RestInterface.class);

                Call<ResponseBody> call = restInterface.listData();

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {


                            JSONObject dataObject = new JSONObject(response.body().string());

                            JSONArray usersArrayJson = dataObject.getJSONArray(param_response_data);


                            DataList dataList = new DataList();

                            List<User> users = new ArrayList<User>();

                            for (int i = 0; i < usersArrayJson.length(); i++) {
                                JSONObject userJson = usersArrayJson.getJSONObject(i);
                                User user = new User();
                                user.setId(userJson.getInt(param_response_user_id));
                                user.setEmail(userJson.getString(param_response_user_email));
                                user.setFirstName(userJson.getString(param_response_user_first_name));
                                user.setLastName(userJson.getString(param_response_user_last_name));
                                user.setAvatar(userJson.getString(param_response_user_avatar));
                                users.add(user);
                            }

                            dataList.setData(users);


                            Log.i(TAG, "Users Size -> " + dataList.getUsers().size() + "");


                            hnd.obtainMessage(1, dataList).sendToTarget();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hnd.obtainMessage(505, "Error").sendToTarget();

                    }
                });


                /*

                Log.i(TAG, "URL -> " + _getDataListUrl);
                URL getDataListUrl = buildUrl(_getDataListUrl);
                try {
                    String data = getResponseFromHttpUrl(getDataListUrl);

                    if (data != null && !data.isEmpty()) {
                        try {
                            JSONObject dataObject = new JSONObject(data);

                            JSONArray usersArrayJson = dataObject.getJSONArray(param_response_data);


                            DataList dataList = new DataList();

                            List<User> users = new ArrayList<User>();

                            for (int i = 0; i < usersArrayJson.length(); i++) {
                                JSONObject userJson = usersArrayJson.getJSONObject(i);
                                User user = new User();
                                user.setId(userJson.getInt(param_response_user_id));
                                user.setEmail(userJson.getString(param_response_user_email));
                                user.setFirstName(userJson.getString(param_response_user_first_name));
                                user.setLastName(userJson.getString(param_response_user_last_name));
                                user.setAvatar(userJson.getString(param_response_user_avatar));
                                users.add(user);
                            }

                            dataList.setData(users);


                            Log.i(TAG, "Users Size -> " + dataList.getUsers().size() + "");


                            hnd.obtainMessage(1, dataList).sendToTarget();
                        } catch (JSONException e) {
                            hnd.obtainMessage(505, "Json Parse Hatası").sendToTarget();
                        }
                    } else {
                        hnd.obtainMessage(505, "Server Hatası").sendToTarget();
                    }


                } catch (IOException e) {
                    hnd.obtainMessage(505, "Error").sendToTarget();
                }

                 */


            }
        }).start();
    }

    public void getUser(final Handler hnd, final int user_id) {
        //showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {

                RestInterface restInterface = ApiClass.getClient().create(RestInterface.class);

                Call<ResponseBody> call = restInterface.getUser(user_id);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {


                            JSONObject dataObject = new JSONObject(response.body().string());


                            JSONObject userJson = dataObject.getJSONObject(param_response_data);

                            User user = new User();
                            user.setId(userJson.getInt(param_response_user_id));
                            user.setEmail(userJson.getString(param_response_user_email));
                            user.setFirstName(userJson.getString(param_response_user_first_name));
                            user.setLastName(userJson.getString(param_response_user_last_name));
                            user.setAvatar(userJson.getString(param_response_user_avatar));

                            hnd.obtainMessage(3, user).sendToTarget();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hnd.obtainMessage(505, "Error").sendToTarget();

                    }
                });


            }
        }).start();
    }


    public void newUser(final Handler hnd, final List<ApiPostParam> params) {
        //showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {

                RestInterface restInterface = ApiClass.getClient().create(RestInterface.class);


                Map<String, Object> jsonParams = new HashMap<>();


                // JSONObject postDataParams = new JSONObject();

                for (ApiPostParam param :
                        params) {
                    //postDataParams.put(param.getName(), param.getData());
                    jsonParams.put(param.getName(), param.getData());


                }


                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());


                //User user = new User(1, "deneme@deneme.com", "Dene", "Me", "https://i.picsum.photos/id/499/300/300.jpg");

                Call<ResponseBody> call = restInterface.setUser(body);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        hnd.obtainMessage(2, "").sendToTarget();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hnd.obtainMessage(505, "").sendToTarget();

                    }
                });


            }
        }).start();
    }

    public void loginOrRegister(final Handler hnd, final List<ApiPostParam> params,final Boolean isLogin) {
        //showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {

                RestInterface restInterface = ApiClass.getClient().create(RestInterface.class);


                Map<String, Object> jsonParams = new HashMap<>();


                // JSONObject postDataParams = new JSONObject();

                for (ApiPostParam param :
                        params) {
                    //postDataParams.put(param.getName(), param.getData());
                    jsonParams.put(param.getName(), param.getData());


                }


                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());


                //User user = new User(1, "deneme@deneme.com", "Dene", "Me", "https://i.picsum.photos/id/499/300/300.jpg");
                Call<ResponseBody> call;
                if (isLogin){
                    call = restInterface.login(body);
                }else{
                    call = restInterface.register(body);
                }


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String success = "false";


                            try {
                                JSONObject dataObject = new JSONObject(response.body().string());

                                success = dataObject.getString("token");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Log.i(TAG,"Token -> " + success);




                        hnd.obtainMessage(4, success).sendToTarget();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hnd.obtainMessage(505, "").sendToTarget();

                    }
                });


            }
        }).start();
    }


    public static URL buildUrl(String urlString) {
        Uri builtUri = Uri.parse(urlString).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    public static String setResponseFromHttpUrl(URL url, List<ApiPostParam> params) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        try {
            JSONObject postDataParams = new JSONObject();

            for (ApiPostParam param :
                    params) {
                postDataParams.put(param.getName(), param.getData());
            }

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            urlConnection.disconnect();
        }

    }

    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator itr = params.keys();

        while (itr.hasNext()) {

            String key = (String) itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }




}


