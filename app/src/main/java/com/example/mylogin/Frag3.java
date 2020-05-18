package com.example.mylogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mylogin.Shop.ShopFrag;

public class Frag3 extends Fragment {

    private View view;
    private TextView tv_sample;
    private Button btn_shop;
    private String result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        tv_sample = view.findViewById(R.id.tv_sample);
        btn_shop = view.findViewById(R.id.btn_shop);

        if (getArguments() != null) {
            result = getArguments().getString("fromShopFrag");
            tv_sample.setText(result);
        }


        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fromFrag3","거래글 작성");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ShopFrag shopFrag = new ShopFrag();
                shopFrag.setArguments(bundle);
                transaction.replace(R.id.main_frame, shopFrag);
                transaction.commit();//저장
            }
        });

        return view;
    }
}
