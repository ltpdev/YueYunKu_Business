package com.gdcp.yueyunku_business.ui.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.database.City;
import com.gdcp.yueyunku_business.database.County;
import com.gdcp.yueyunku_business.database.Province;
import com.gdcp.yueyunku_business.utils.HttpUtil;
import com.gdcp.yueyunku_business.utils.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by asus- on 2017/2/15.
 */

public class ChooseAreaFragment extends BaseFragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.back_button)
    Button backButton;
    @BindView(R.id.listview)
    ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;
    private String provinceName = null;
    private String cityName = null;
    private String countyName = null;

    @Override
    protected int getLayoutResId() {
        return R.layout.chose_area;
    }

    @Override
    protected void init() {
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    provinceName = selectedProvince.getProvinceName();
                    queryCiteis();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    cityName = selectedCity.getCityName();
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    countyName = countyList.get(position).getCountyName();
                    Intent intent = new Intent();
                    intent.putExtra("address", provinceName + "-" + cityName + "-" + countyName);
                    getActivity().setResult(7001, intent);
                    getActivity().finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCiteis();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else {
                    getActivity().finish();
                }
            }
        });
        queryProvinces();
        super.onActivityCreated(savedInstanceState);
    }

    private void queryProvinces() {
        titleText.setText(getString(R.string.china));
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
                Log.d("dd", province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
            Log.d("dd", "ggg");
        }
    }

    private void queryFromServer(String address, final String type) {
        showProgress(getString(R.string.loading));
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                        Toast.makeText(getContext(), getString(R.string.failed_load), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responeText = response.body().string();

                boolean result = false;
                if (type.equals("province")) {
                    result = Utility.handleProvinceResponse(responeText);
                } else if (type.equals("city")) {
                    result = Utility.handleCityResponse(responeText, selectedProvince.getId());
                } else if (type.equals("county")) {
                    result = Utility.handleCountyResponse(responeText, selectedCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideProgress();
                            if (type.equals("province")) {
                                queryProvinces();
                            } else if (type.equals("city")) {
                                queryCiteis();
                            } else if (type.equals("county")) {
                                queryCounties();
                            }
                        }
                    });
                }
            }


        });
    }



    private void queryCiteis() {
        titleText.setText(selectedProvince.getProvinceName());
        cityList = DataSupport.where("provinceId=?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        countyList = DataSupport.where("cityid=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }


}
