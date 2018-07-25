package com.example.heo.step10application;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView spanView = (TextView) findViewById(R.id.spanView);
        spanView.setMovementMethod(new ScrollingMovementMethod());

        // img 단어에 해당 이미치를 출력
        String data = "복수초 \n img \n 이른봄 설산에서 만나는 복수초는 모든 야생화 찍사들의 로망이 아닐까 싶다.";

        SpannableStringBuilder builder = new SpannableStringBuilder(data);

        int start = data.indexOf("img");

        if (start > -1) {
            // img 문자열의 끝 위치
            int end = start + "img".length();

            // 이미지 획득
            Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.img1, null);

            // 이미지의 화면 출력정보 설정
            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());

            // ImageSpan 준비
            ImageSpan span = new ImageSpan(dr);

            // ImageSpan 적용
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // SPAN_EXCLUSIVE_EXCLUSIVE : 왼쪽 제거, 오른쪽 제거
            // SPAN_EXCLUSIVE_EXCLUSIVE : 왼쪽 제거, 오른쪽 포함
            // SPAN_EXCLUSIVE_EXCLUSIVE : 왼쪽 포함, 오른쪽 제거
            // SPAN_EXCLUSIVE_EXCLUSIVE : 왼쪽 포함, 오른쪽 포함
        }

        // 복수초를 크게 하기
        // 문자열 시작위치 획득
        start = data.indexOf("복수초");

        if (start > -1) {
            // 문자열 끝 위치 획득
            int end = start + "복수초".length();

            // BOLD 타입으로 StyleSpan 준비
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);

            // URLSpan      : URL 링크 모양과 클릭 이벤트 적용
            // StyleSpan    : 스타일 적용
            // RelativeSizeSpan : 크기 적용
            // ImageSpan : 이미지 데이터 적용
            // AbsoluteSizeSpan : 크기 변경 적용
            // ClickableSpan : 문자열 클릭 이벤트 적용
            // UnderlineSpan : 밑줄 적용
            // BackgroundColorSpan : 배경 색상값 적용
            // ForegroundColorSpan : 전경 색상값 적용

            // 기본 크기보다 2배 크게 표현하는 Span 준비
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(2.0f);

            // Span 적용
            builder.setSpan(styleSpan, start, end + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(sizeSpan, start, end + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        spanView.setText(builder);

        TextView htmlView = (TextView) findViewById(R.id.htmlView);
        String html = "<font color='RED'>얼레지</font><br/><img src='img1'/><br/>곰배령에서 만난 봄꽃";
        htmlView.setText(Html.fromHtml(html, new MyImageGetter(), null));
    }

    class MyImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            if (source.equals("img1")) {
                // 이미지 획득
                Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.img2, null);
                // 이미지 화면 출력정보 설정
                dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
                // 리턴시킨 Drawable 객체가 img 위치에 출력
                return dr;
            }
            return null;
        }
    }
}
