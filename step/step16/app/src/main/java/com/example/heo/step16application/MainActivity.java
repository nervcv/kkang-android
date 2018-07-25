package com.example.heo.step16application;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 구글 기본 앱 연동
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button contactsBtn;
    Button cameraDataBtn;
    Button cameraFileBtn;
    Button speechBtn;
    Button mapBtn;
    Button browserBtn;
    Button callBtn;

    TextView resultView;
    ImageView resultImageView;

    File filePath;

    int reqWidth;
    int reqHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = (TextView) findViewById(R.id.resultView);
        contactsBtn = (Button) findViewById(R.id.btn_contacts);
        cameraDataBtn = (Button) findViewById(R.id.btn_camera_data);
        cameraFileBtn = (Button) findViewById(R.id.btn_camera_file);
        speechBtn = (Button) findViewById(R.id.btn_speech);
        mapBtn = (Button) findViewById(R.id.btn_map);
        browserBtn = (Button) findViewById(R.id.btn_browser);
        callBtn = (Button) findViewById(R.id.btn_call);
        resultImageView = (ImageView) findViewById(R.id.resultImageView);

        contactsBtn.setOnClickListener(this);
        cameraDataBtn.setOnClickListener(this);
        cameraFileBtn.setOnClickListener(this);
        speechBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        browserBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
        resultImageView.setOnClickListener(this);

        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);
    }


    /*
        기본앱이 설치되지 않았을 경우 처리
        PackageManager pm = getPackageManager();

        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if(activities.size() != 0){
            // 있을 때
        }else{
            // 없을 때
        }

     */

    @Override
    public void onClick(View v) {
        if (v == contactsBtn) {
            // 1) 주소록 앱 연동
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI); // 기본앱의 주소록의 목록 화면이 표출 (사람 이름과 전화번호)
            startActivityForResult(intent, 10);

            // ContactsContract.Contacts.CONTENT_URI // 사람 이름
            // ContactsContract.CommonDataKinds.Email.CONTENT_URI // 사람 이름과 이메일

            /*
                //  주소록 앱의 액티비티를 인텐트로 실행하여 한 사람에 대한 상세보기 화면을 호출할 수 있다.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_URI + "/" + 1066));
                startActivity(intent);
             */

        } else if (v == cameraDataBtn) {
            // 2) 카메라 앱 연동

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        } else if (v == cameraFileBtn) {
            // 3) 카메라 이미지 데이터 파일 공유
            // 카메라앱에서 사진 촬영 데이터를 파일에 쓴(Write) 다음, 성공 여부를 반환
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    // 일단 파일을 하나 만들고
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";
                    File dir = new File(dirPath);
                    if (!dir.exists())
                        dir.mkdir();
                    filePath = File.createTempFile("IMG", ".jpg", dir);
                    if (!filePath.exists())
                        filePath.createNewFile();

                    // 안드로이드 7.0 (api 24) 부터 file://URI의 노출을 금지
                    // 파일을 공유하려면, content://URI를 보내고, 이 URI에 대해 임시 엑세스 권한을 부여해야 합니다.
                    // 이를 쉽게 도와주는 SUPPORT 라이브러리 클래스가 FileProvider 입니다.
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", filePath);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, 40);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }

            // 4) 동영상 촬영
            /*
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);   // 촤열 시간
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024*1024*10); // 동영상 파일 크기
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI); // 동영상 파일도 파일 공유 방법을 이용하는 것이 위와 동일하다.
                startActivityForResult(intent, 20);
            */

        } else if (v == speechBtn) {
            // 6) 음성인식 앱 연동
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            // RecognizerIntent.LANGUAGE_MODEL_FREE_FORM : 일반적인 음성에 대한 문자열 변환 서비스
            // RecognizerIntent.LANGUAGE_MODEL_WEB_SERCH : 웹 검색 조건에 기반을 둔 언어 모델
            // 언어 모델 이외에 RecognizerIntent.EXTRA_LANGUAGE 를 통해 언어를 선택할 수 있는데, 만약 지정하지 않으면, 스마트폰의 기본언어로 설정됨.
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식 테스트"); // 음성인식 앱의 문자열 설정
            startActivityForResult(intent, 50);
        } else if (v == mapBtn) {
            // 7) 지도 앱 호출
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952,126.9779451"));
            startActivity(intent);
        } else if (v == browserBtn) {
            // 8) 브라우저 앱 호출
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"));
            startActivity(intent);
        } else if (v == callBtn) {
            // 9) 전화앱 호출
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"));
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        } else if (v == resultImageView) {
            // 5) 갤러리 앱 : 작은 사진을 크게 보여주기의 기본 내장앱을 띄우는 것
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", filePath);
            intent.setDataAndType(photoURI, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

            /* 갤러리 앱의 목록 액티비티를 띄우는 법
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);

                // 결과 수신
                String result = data.getDataString();
                resultView.setText(result)
            */
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 1) 주소록 연동 결과
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String result = data.getDataString();
            resultView.setText(result); // 이렇게 식별값(마지막 문자)를 얻은 후 구체적인 전화번호나 이메일 등의 값을 다시 가져와야 한다.
        // 2) 카메라 앱 결과
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
            // 그러나 이 방식은 카메라 앱으로 촬영한 사진이 파일로 저장되지 않으며, 사진 데이터 자체가 전달되는 구조
            // 그리고 이미지 크기가 너무 작게 전달된다. 섬네일 이미지 데이터만 전달되기 때문이다.
        } else if (requestCode == 40 && resultCode == RESULT_OK) {
            // 3) 카메라 이미지 데이터 파일 공유 결과
            // BitmapFactory.decodeByteArray() : byte[] 배열로 Bitmap 생성
            // BitmapFactory.decodeFile() : 파일 경로를 주면, FileInputStream을 만들어서 decodeStream 이용
            // BitmapFactory.decodeResource() : Resource 폴더에 저장된 파일
            // BitmapFactory.decodeStream() : InputStream으로 Bitmap 생성


            if (filePath != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                // options.inSampleSize = 10; // 10배 줄인 크기로
                options.inJustDecodeBounds = true; // 프로그램의 정적한 크기에 맞게 동적으로 처리
                try {
                    InputStream in = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(in, null, options); // decodeXXX으로 파일의 OutofMemory 방지
                    in.close();
                    in = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }


                final int height = options.outHeight; // 원본 크기 높이
                final int width = options.outWidth; // 원본 크기 넓이
                int inSampleSize = 1;

                if (height > reqHeight || width > reqWidth) {
                    final int heightRatio = Math.round((float) height / (float) reqHeight);
                    final int widthtRatio = Math.round((float) width / (float) reqWidth);

                    inSampleSize = heightRatio < widthtRatio ? heightRatio : widthtRatio;
                }

                BitmapFactory.Options imgOptions = new BitmapFactory.Options();
                imgOptions.inSampleSize = inSampleSize; // 적정한 비율 유지
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);
                resultImageView.setImageBitmap(bitmap);
            }
        } else if (requestCode == 50 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String result = results.get(0);
            resultView.setText(result);
        }
    }
}