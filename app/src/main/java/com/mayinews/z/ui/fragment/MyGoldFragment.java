package com.mayinews.z.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mayinews.z.R;
import com.mayinews.z.domain.MyGoldBean;
import com.mayinews.z.ui.adapter.MyGoldAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by h on 2018/2/26 0026.
 */

public class MyGoldFragment extends Fragment {


    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    private List<MyGoldBean> datas=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.mygold_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        //设置listView
         for(int i=0;i<3;i++){
             MyGoldBean goldBean = new MyGoldBean("徒弟阅读进攻", "徒弟阅读进攻给您的金币", "+20 金币", "2018-02-26 09:52:06");
             datas.add(goldBean);
         }
        MyGoldAdapter adapter = new MyGoldAdapter(getActivity());
        listView.setAdapter(adapter);
        adapter.addData(datas);
        adapter.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
