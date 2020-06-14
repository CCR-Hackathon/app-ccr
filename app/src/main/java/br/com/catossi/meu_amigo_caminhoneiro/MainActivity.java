package br.com.catossi.meu_amigo_caminhoneiro;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.catossi.meu_amigo_caminhoneiro.model.Payload;
import br.com.catossi.meu_amigo_caminhoneiro.service.APIClient;
import br.com.catossi.meu_amigo_caminhoneiro.service.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private final int SPEECH_RECOGNITION_CODE = 1;
    private ImageView btnMicrophone;
    private TextToSpeech t1;
    private ProgressDialog progress;
    private Context context;
    int lastRandomPhrase = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progress = ProgressDialog.show(MainActivity.this, "Carregando", "Aguarde alguns instantes...", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMicrophone = findViewById(R.id.btn_microphone);

        btnMicrophone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechToText();
            }
        });

        context = this;

        progress.dismiss();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        speak("");

        Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {

                int min = 0;


                String[] phrases = {
                        "Olá amigo caminhoneiro! Você está dirigindo por 4 horas seguidas. Que tal fazer uma pausa no próximo ponto?"};


                int max = phrases.length;
                if (min >= max) {
                    throw new IllegalArgumentException("max must be greater than min");
                }

                Random r = new Random();
                int randomPhrase = 0;

                do {
                    randomPhrase = r.nextInt((max - min) + 1) + min;
                } while (randomPhrase == lastRandomPhrase);

                lastRandomPhrase = randomPhrase;

                speak("" + phrases[0]);

            }
        };

//        timerObj.schedule(timerTaskObj, 1000, 500000);
//
//        Timer verifySpeaking = new Timer();
//        TimerTask verifySpeakingObj = new TimerTask() {
//            public void run() {
//                if(myTTS.isSpeaking() == false) {
//                    SystemClock.sleep(5000);
//                    startSpeechToText();
//                }
//            }
//        };
//
//        verifySpeaking.schedule(verifySpeakingObj, 8000, 8000);


    }

    private TextToSpeech myTTS;
    private String textToSpeak;

    public void speak(String text) {
        textToSpeak = text;

        if (myTTS == null) {
            try {
                myTTS = new TextToSpeech(this, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sayText(textToSpeak);
    }

    private void sayText(String textToSpeak) {
        myTTS.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH,     null);


    }


    public void onInit(int initStatus) {
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.getDefault());
                }
            }


        });
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();


    }



    protected void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                R.string.action);
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), R.string.sorry,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    progress = ProgressDialog.show(MainActivity.this, "Carregando", "Aguarde alguns instantes...", true);

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);

                    Log.e("audio0000",  "" + text.toUpperCase());

                    if(payload == null)
                        payload = new Payload();

                    payload.setText(text);

                    interact();
                }
                break;
            }
        }
    }

    private APIInterface apiService;
    private Call<Payload> callBalance;

    private Payload payload;

    protected void interact() {

        apiService = APIClient.getService().create(APIInterface.class);
        callBalance = apiService.postVoice(payload);

        Log.e("INIT REQUEST", "" + payload.getText());

        try {
            getSSLSocketFactory();
        }catch (Exception e){
            Log.e("ERRO", "" + e);
        }
        Log.i("Request in API", "" + callBalance.request().url().toString());

        callBalance.enqueue(new Callback<Payload>() {
            @Override
            public void onResponse(Call<Payload> call, Response<Payload> response) {
                if (response.raw().code() == 200) {

                    Payload payloadResponse = response.body();

                    speak(payloadResponse.getOutput());

                    payload = payloadResponse;

                    Log.e("RESULT REQUEST", payload.getOutput());
                    progress.dismiss();
                }

                Log.e("RESULT REQUEST", "" + response.body());
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Payload> call, Throwable t) {
                Log.e("BALANCE", t.toString());
                progress.dismiss();
            }
        });
    }

    public SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = getResources().openRawResource(R.raw.cert); // your certificate file
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                }
        };
    }
}
