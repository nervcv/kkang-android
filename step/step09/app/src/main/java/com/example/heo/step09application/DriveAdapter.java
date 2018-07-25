package com.example.heo.step09application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// 어답터 상속
public class DriveAdapter extends ArrayAdapter<DriveVO> {
    Context context;
    int resId;
    ArrayList<DriveVO> datas;

    public DriveAdapter(Context context, int resId, ArrayList<DriveVO> datas) {
        super(context, resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }

    // getCount / getView 는 자동 호출
    @Override
    public int getCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // 항목 layout 초기화, null 아니면 호출된적이 있다는 뜻.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null); // resId ==  R.layout.custom_item

            DriveHolder holder = new DriveHolder(convertView);
            convertView.setTag(holder);
        }

        DriveHolder holder = (DriveHolder) convertView.getTag();

        // 뷰 획득, findViewById() 함수로 최초 실행 후, 이후에는 해당 함수 호출 없이 저장된 뷰를 그대로 이용하게 해주는 구성이다.
        // 그래서, Holder 라는 findViewById 대상이 되는 뷰들을 묶는 클래스를 하나 정의합니다.
        // Holder 클래스의 객체가 메모리에 지속하여야 하는데, 그 부분은 Adapter 쪽에서 해주어야 합니다.
        ImageView typeImageView = holder.typeImageView;
        TextView titleView = holder.titleView;
        TextView dateView = holder.dateView;
        ImageView menuImageView = holder.menuImageView;

        // 항목 데이터 획득
        final DriveVO vo = datas.get(position);

        // 뷰에 데이터 설정
        titleView.setText(vo.title);
        dateView.setText(vo.date);

        if (vo.type.equals("doc")) {
            typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_doc, null));
        } else if (vo.type.equals("file")) {
            typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_file, null));
        } else if (vo.type.equals("img")) {
            typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.ic_type_image, null));
        }

        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, vo.title + " menu click", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return convertView;
    }
}
