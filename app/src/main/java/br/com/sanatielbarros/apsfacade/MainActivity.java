package br.com.sanatielbarros.apsfacade;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL endpoint = new URL("https://api.github.com/");
                    HttpsURLConnection httpsCon = (HttpsURLConnection) endpoint.openConnection();
                    httpsCon.setRequestProperty("User-Agent","my-app");
                    httpsCon.setRequestProperty("Accept","application/vnd.github.v3+json");
                    httpsCon.setRequestMethod("GET");

                    if(httpsCon.getResponseCode() == 200){
                        System.out.println("Sucesso");
                        InputStream responseBody = httpsCon.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()){
                            String key = jsonReader.nextName();
                            String value = jsonReader.nextString();
                            //mostrar todos os resultados
                            System.out.println(value);

                            /* mostrando resultado de uma key especifica
                            if(key.equals("organization_url")){
                                String value = jsonReader.nextString();
                                System.out.println(value);
                            }else{
                                jsonReader.skipValue();
                            }
                            */
                        }
                    }else{
                        System.out.println("Erro");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
