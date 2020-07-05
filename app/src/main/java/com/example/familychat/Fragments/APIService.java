package com.example.familychat.Fragments;

import com.example.familychat.Notification.MyResponse;
import com.example.familychat.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(

            {
                    "Content-Type:application/Json",
                    "Authorization:key=AAAAl875T0k:APA91bHh7TRa_l-FOdCLJP0nNCIiFMPoVjcGLDrb0SVlGveRaBEVr8pstzlpTBVnktg3FyXWIHY9zATTJ96tOMgzIjIGZDWl3Fh4E8VLUf18Ha1vl2nxHlY55O0jepZNVab1dOz1JMvL"
            }
    )
    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body Sender body);

}
