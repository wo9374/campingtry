package com.example.mylogin.SEARCH;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.R;

import java.util.ArrayList;

public class Frag2 extends Fragment {

    private View view;
    private Context ct;

    private Spinner spinner1,spinner2,spinner3;

    private String[] data;
    private ArrayAdapter<String> adapter;

    private ImageButton btn_search;

    private String[] tema = {"오토캠핑","글램핑","카라반","펜션","계곡","피크닉"};
    private int[] temaImgs= new int[6]; //테마 선택 그림 넣을 배열변수
    private EditText keyword;

    private String keyword_txt; //키워드를 쿼리문으로 보낼 스트링 변수
    private String mAdd, sAdd; //스피너 값에 따른 주소찾을 쿼리문으로 보낼 스트링 변수

    public static boolean chk[] = new boolean[6];
    private String tema_chk;

    RecyclerView mRecyclerView = null ;
    SearchAdapter mAdapter = null;
    ArrayList<SearchRecycleItem> mList =  new ArrayList<SearchRecycleItem>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);
        ct = container.getContext();
        int j;
        for(j=0; j<chk.length;j++){
            chk[j]= false;
        }

        keyword = view.findViewById(R.id.keyword); //키워드 창

        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //전체 도 및 광역시 스피너
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    data = getResources().getStringArray(R.array.시군);
                }else{
                    switch (position){ //도 및 광역시 스피너에 따라 행정구역 시/군 변경
                        case 1:
                            data = getResources().getStringArray(R.array.서울시);
                            break;
                        case 2:
                            data = getResources().getStringArray(R.array.부산시);
                            break;
                        case 3:
                            data = getResources().getStringArray(R.array.대구시);
                            break;
                        case 4:
                            data = getResources().getStringArray(R.array.인천시);
                            break;
                        case 5:
                            data = getResources().getStringArray(R.array.광주시);
                            break;
                        case 6:
                            data = getResources().getStringArray(R.array.대전시);
                            break;
                        case 7:
                            data = getResources().getStringArray(R.array.울산시);
                            break;
                        case 8:
                            data = getResources().getStringArray(R.array.세종시);
                            break;
                        case 9:
                            data = getResources().getStringArray(R.array.경기도);
                            break;
                        case 10:
                            data = getResources().getStringArray(R.array.강원도);
                            break;
                        case 11:
                            data = getResources().getStringArray(R.array.충청북도);
                            break;
                        case 12:
                            data = getResources().getStringArray(R.array.충청남도);
                            break;
                        case 13:
                            data = getResources().getStringArray(R.array.전라북도);
                            break;
                        case 14:
                            data = getResources().getStringArray(R.array.전라남도);
                            break;
                        case 15:
                            data = getResources().getStringArray(R.array.경상북도);
                            break;
                        case 16:
                            data = getResources().getStringArray(R.array.경상남도);
                            break;
                        case 17:
                            data = getResources().getStringArray(R.array.제주도);
                            break;
                    }
                }
                adapter = new ArrayAdapter<String>(ct, R.layout.support_simple_spinner_dropdown_item,data);
                spinner2.setAdapter(adapter);
            } //spinner1 selected end

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });//spinner1 selected listener end



        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 시/군 선택부분
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });//spinner2 selected end


        for (int i=0; i<temaImgs.length;i++){
            temaImgs[i] = getResources().getIdentifier("tema_"+i,"drawable","com.example.mylogin");
            //테마선택 스피너에 넣을 이미지
        }
        final AdapterTemaSpinner adapterTemaSpinner = new AdapterTemaSpinner(tema,temaImgs,ct);
        spinner3.setAdapter(adapterTemaSpinner);
        // 어뎁터 써서 만들어둔 spinner_tema에 사진과 텍스트 체크박스를 넣고 갯수만큼 뿌려줌

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_search = view.findViewById(R.id.btn_search); //검색 돋보기 버튼
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mList.clear(); // 검색 버튼 누를때마다 초기화

                if (spinner1.getSelectedItemPosition()==0){ //지역 선택안할시 알림창
                    Toast.makeText(ct,"지역을 선택해 주세요.",Toast.LENGTH_LONG).show();
                }else{ //지역선택
                    if(spinner2.getSelectedItemPosition()==0){ //시군 선택안하면 선택한 도/시로 전체검색
                        mAdd = spinner1.getSelectedItem().toString();
                        keyword_txt = keyword.getText().toString();
                        CheckTema();
                        System.out.println(tema_chk); //구해진 tema_chk 스트링으로 테마 참아주삼 테마 체크한거 없으면 모든테마로 쿼리 ㄱㄱ


                        int img = R.drawable.tema_5;
                        addItem(img,"테스트 제목","테스트 설명",mAdd);
                        //임시로 하드코딩

                        mAdapter.notifyDataSetChanged(); //새로고침
                    }else{ //시군 선택했을때

                        mAdd = spinner1.getSelectedItem().toString();
                        sAdd = spinner2.getSelectedItem().toString();
                        keyword_txt = keyword.getText().toString();
                        CheckTema();
                        System.out.println(tema_chk); //구해진 tema_chk 스트링으로 테마 참아주삼 테마 체크한거 없으면 모든테마로 쿼리 ㄱㄱ

                        int img = R.drawable.tema_5;
                        addItem(img,"테스트 제목","테스트 설명",mAdd + " " + sAdd);
                        //임시로 하드코딩

                        mAdapter.notifyDataSetChanged(); //새로고침
                    }
                } //지역 선택 else 끝
            }
        });

        mRecyclerView = view.findViewById(R.id.search_Recycle);
        mAdapter = new SearchAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));


        return view;
    }//onCreateView end.

    void CheckTema(){
        tema_chk = null;
        for (int y=0; y<chk.length;y++){
            if(chk[y]){
                if(tema_chk == null){
                    tema_chk= tema[y];
                }else{
                    tema_chk = tema_chk+","+tema[y];
                }
            }
        }
    }

    void addItem(int image, String title,String content,String address){
        SearchRecycleItem item = new SearchRecycleItem();

        item.setImage(image);
        item.setTitle(title);
        item.setContent(content);
        item.setAddress(address);

        mList.add(item);
    }
}
