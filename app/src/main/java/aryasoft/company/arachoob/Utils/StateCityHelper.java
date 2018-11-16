package aryasoft.company.arachoob.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import aryasoft.company.arachoob.Models.CityModel;
import aryasoft.company.arachoob.Models.StateCityModel;

public class StateCityHelper
{
    public static ArrayList<StateCityModel> GetStateCityList(Context Context)
    {
        ArrayList<StateCityModel> StateList = new ArrayList<>();
        JSONArray CityArray;
        String StatesJson = readJsonFromAssets(Context);
        try
        {
            CityArray = new JSONArray(StatesJson);

            for (int i = 0; i < CityArray.length(); ++i)
            {

                JSONObject StateCityJson = CityArray.getJSONObject(i);
                StateCityModel StateCityObj = new StateCityModel();
                StateCityObj.StateCode = StateCityJson.getInt("StateCode");
                StateCityObj.StateName = StateCityJson.getString("StateName");
                JSONArray ArrayCity = StateCityJson.getJSONArray("City");
                StateCityObj.CityCollection = new ArrayList<>();
                for (int k = 0; k < ArrayCity.length(); ++k)
                {
                    JSONObject CityJson = ArrayCity.getJSONObject(k);
                    CityModel City = new CityModel();
                    City.CityCode = CityJson.getInt("CityCode");
                    City.CityName = CityJson.getString("CityName");

                    StateCityObj.CityCollection.add(City);
                }
                StateList.add(StateCityObj);
            }
        } catch (Exception exp)
        {
        }
        //----------------------
        return StateList;
    }

    public static String readJsonFromAssets(Context context)
    {
        String json = null;
        try
        {
            InputStream is = context.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<CityModel> getCities(Context context)
    {
        ArrayList<CityModel> CityList = new ArrayList<>();
        String json = "";
        JSONArray CityArray = null;
        try
        {
            InputStream is = context.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            //--------------------
            CityArray = new JSONArray(json);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        for (int i = 0; i < CityArray.length(); ++i)
        {
            try
            {
                JSONObject jsonObject = CityArray.getJSONObject(i);
                JSONArray JACity = jsonObject.getJSONArray("City");
                for (int j = 0; j < JACity.length(); ++j)
                {
                    JSONObject jsonObjectCity = JACity.getJSONObject(j);
                    CityModel city = new CityModel();
                    city.CityCode = jsonObjectCity.getInt("CityCode");
                    city.CityName = jsonObjectCity.getString("CityName");
                    CityList.add(city);

                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return CityList;
        //-------------------------
    }
}
