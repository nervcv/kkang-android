package com.example.heo.step19application;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OneFragment  extends ListFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] datas={"박찬호","류현진","김현수","오승환"};
        ArrayAdapter<String> aa=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datas);
        setListAdapter(aa);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast toat=Toast.makeText(getActivity(), (String)l.getAdapter().getItem(position), Toast.LENGTH_SHORT);
        toat.show();
    }
}
