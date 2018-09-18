package com.seoul.ddroad.board;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.seoul.ddroad.R;

public class BoardFragment extends Fragment {
    private WebView mWebView;
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_board, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWebView = (WebView) getView().findViewById(R.id.webView);

        WebSettings settings=mWebView.getSettings();
        //웹뷰 셋팅
        settings.setJavaScriptEnabled(true);                         //자바스크립트 허용
        settings.setSupportZoom(true);                           //줌 관련
        settings.setBuiltInZoomControls(true);                    //줌 관련;
        settings.setDisplayZoomControls(false);                   //줌 관련
        settings.setJavaScriptCanOpenWindowsAutomatically(true);     //window.open() 동작하려면 필요
        //settings.setSupportMultipleWindows(true);

        settings.setLoadsImagesAutomatically(true);                 // 웹뷰가 앱에 등록되어 있는 이미지 리로스를 자동으로 로드 하는속성

        settings.setUseWideViewPort(true);                           //html 컨텐츠가 웹뷰에 맞게 나타남
        settings.setLoadWithOverviewMode(true);

        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /*LOAD_CACHE_ELSE_NETWORK 기간이 만료돼 캐시를 사용할 수 없을 경우 네트워크를 사용합니다.
        LOAD_CACHE_ONLY 네트워크를 사용하지 않고 캐시를 불러옵니다.
        LOAD_DEFAULT 기본적인 모드로 캐시를 사용하고 만료된 경우 네트워크를 사용해 로드합니다.
        LOAD_NORMAL 기본적인 모드로 캐시를 사용합니다.
        LOAD_NO_CACHE 캐시모드를 사용하지 않고 네트워크를 통해서만 호출합니다.*/

        settings.setAppCacheEnabled(false);     //앱 내부 캐시 사용여부
        //settings.setDomStorageEnabled(true);    //하루동안 보지않기 기능에 사용
        settings.setAllowFileAccess(true);     //웹뷰 내에서 파일 액세스 활성화

        //settings.setGeolocationEnabled(true); // GeoLocation를 사용하도록 설정

        mWebView.loadUrl("http://guitarhyo.freehongs.net"); // 접속 URL

        mWebView.addJavascriptInterface(new BoardFragment.JavascriptTest(), "android");
        mWebView.setWebViewClient(new BoardFragment.MyWebClient());
        mWebView.setWebChromeClient(new BoardFragment.MyWebChrome());

    }

    class JavascriptTest {
        @JavascriptInterface
        public String getChartData(){
            StringBuffer buffer=new StringBuffer();
            buffer.append("[");
            for(int i=0; i<14; i++){
                buffer.append("["+i+","+Math.sin(i)+"]");
                if(i<13) buffer.append(",");
            }
            buffer.append("]");
            return buffer.toString();
        }
    }

    class MyWebClient extends WebViewClient {
        // 로딩이 시작될 때
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        // 리소스를 로드하는 중 여러번 호출
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        // 방문 내역을 히스토리에 업데이트 할 때
        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        // 로딩이 완료됬을 때 한번 호출
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        // 오류가 났을 경우, 오류는 복수할 수 없음
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            switch (errorCode) {
                case ERROR_AUTHENTICATION:
                    break;               // 서버에서 사용자 인증 실패
                case ERROR_BAD_URL:
                    break;                           // 잘못된 URL
                case ERROR_CONNECT:
                    break;                          // 서버로 연결 실패
                case ERROR_FAILED_SSL_HANDSHAKE:
                    break;    // SSL handshake 수행 실패
                case ERROR_FILE:
                    break;                                  // 일반 파일 오류
                case ERROR_FILE_NOT_FOUND:
                    break;               // 파일을 찾을 수 없습니다
                case ERROR_HOST_LOOKUP:
                    break;           // 서버 또는 프록시 호스트 이름 조회 실패
                case ERROR_IO:
                    break;                              // 서버에서 읽거나 서버로 쓰기 실패
                case ERROR_PROXY_AUTHENTICATION:
                    break;   // 프록시에서 사용자 인증 실패
                case ERROR_REDIRECT_LOOP:
                    break;               // 너무 많은 리디렉션
                case ERROR_TIMEOUT:
                    break;                          // 연결 시간 초과
                case ERROR_TOO_MANY_REQUESTS:
                    break;     // 페이지 로드중 너무 많은 요청 발생
                case ERROR_UNKNOWN:
                    break;                        // 일반 오류
                case ERROR_UNSUPPORTED_AUTH_SCHEME:
                    break; // 지원되지 않는 인증 체계
                case ERROR_UNSUPPORTED_SCHEME:
                    break;          // URI가 지원되지 않는 방식
            }

        }
        // http 인증 요청이 있는 경우, 기본 동작은 요청 취소
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        // 확대나 크기 등의 변화가 있는 경우
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }
        // 잘못된 키 입력이 있는 경우
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //  Toast t=Toast.makeText(BoardActivity.this, url, Toast.LENGTH_SHORT) ;
            //  t.show();
            Log.d("ddroad","msg: "+url);
            view.loadUrl(url);
            return true;
        }
    }

    class MyWebChrome extends WebChromeClient {


        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

            //Toast t=Toast.makeText(BoardActivity.this, message, Toast.LENGTH_SHORT);
            //t.show();
            //result.confirm();

            new AlertDialog.Builder(view.getContext())
                    .setTitle("AlertDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();

            return true;
        }
    }
}
