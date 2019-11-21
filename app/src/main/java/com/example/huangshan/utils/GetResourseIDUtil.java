package com.example.huangshan.utils;

import com.example.huangshan.R;

import java.lang.reflect.Field;

public class GetResourseIDUtil {

    public static int  getWeatherIconID(String imageName){
        int imageId;
        switch (imageName) {
            case "weather_icon_100.png":
                imageId = R.mipmap.weather_icon_100;
                break;
            case "weather_icon_100n.png":
                imageId = R.mipmap.weather_icon_100n;
                break;
            case "weather_icon_101.png":
                imageId = R.mipmap.weather_icon_101;
                break;
            case "weather_icon_102.png":
                imageId = R.mipmap.weather_icon_102;
                break;
            case "weather_icon_103.png":
                imageId = R.mipmap.weather_icon_103;
                break;
            case "weather_icon_103n.png":
                imageId = R.mipmap.weather_icon_103n;
                break;
            case "weather_icon_104.png":
                imageId = R.mipmap.weather_icon_104;
                break;
            case "weather_icon_104n.png":
                imageId = R.mipmap.weather_icon_104n;
                break;
            case "weather_icon_200.png":
                imageId = R.mipmap.weather_icon_200;
                break;
            case "weather_icon_201.png":
                imageId = R.mipmap.weather_icon_201;
                break;
            case "weather_icon_202.png":
                imageId = R.mipmap.weather_icon_202;
                break;
            case "weather_icon_203.png":
                imageId = R.mipmap.weather_icon_203;
                break;
            case "weather_icon_204.png":
                imageId = R.mipmap.weather_icon_204;
                break;
            case "weather_icon_205.png":
                imageId = R.mipmap.weather_icon_205;
                break;
            case "weather_icon_206.png":
                imageId = R.mipmap.weather_icon_206;
                break;
            case "weather_icon_207.png":
                imageId = R.mipmap.weather_icon_207;
                break;
            case "weather_icon_208.png":
                imageId = R.mipmap.weather_icon_208;
                break;
            case "weather_icon_209.png":
                imageId = R.mipmap.weather_icon_209;
                break;
            case "weather_icon_210.png":
                imageId = R.mipmap.weather_icon_210;
                break;
            case "weather_icon_211.png":
                imageId = R.mipmap.weather_icon_211;
                break;
            case "weather_icon_212.png":
                imageId = R.mipmap.weather_icon_212;
                break;
            case "weather_icon_213.png":
                imageId = R.mipmap.weather_icon_213;
                break;
            case "weather_icon_300.png":
                imageId = R.mipmap.weather_icon_300;
                break;
            case "weather_icon_300n.png":
                imageId = R.mipmap.weather_icon_300n;
                break;
            case "weather_icon_301.png":
                imageId = R.mipmap.weather_icon_301;
                break;
            case "weather_icon_301n.png":
                imageId = R.mipmap.weather_icon_301n;
                break;
            case "weather_icon_302.png":
                imageId = R.mipmap.weather_icon_302;
                break;
            case "weather_icon_303.png":
                imageId = R.mipmap.weather_icon_303;
                break;
            case "weather_icon_304.png":
                imageId = R.mipmap.weather_icon_304;
                break;
            case "weather_icon_305.png":
                imageId = R.mipmap.weather_icon_305;
                break;
            case "weather_icon_306.png":
                imageId = R.mipmap.weather_icon_306;
                break;
            case "weather_icon_307.png":
                imageId = R.mipmap.weather_icon_307;
                break;
            case "weather_icon_309.png":
                imageId = R.mipmap.weather_icon_309;
                break;
            case "weather_icon_310.png":
                imageId = R.mipmap.weather_icon_310;
                break;
            case "weather_icon_311.png":
                imageId = R.mipmap.weather_icon_311;
                break;
            case "weather_icon_312.png":
                imageId = R.mipmap.weather_icon_312;
                break;
            case "weather_icon_313.png":
                imageId = R.mipmap.weather_icon_313;
                break;
            case "weather_icon_314.png":
                imageId = R.mipmap.weather_icon_314;
                break;
            case "weather_icon_315.png":
                imageId = R.mipmap.weather_icon_315;
                break;
            case "weather_icon_316.png":
                imageId = R.mipmap.weather_icon_316;
                break;
            case "weather_icon_317.png":
                imageId = R.mipmap.weather_icon_317;
                break;
            case "weather_icon_318.png":
                imageId = R.mipmap.weather_icon_318;
                break;
            case "weather_icon_399.png":
                imageId = R.mipmap.weather_icon_399;
                break;
            case "weather_icon_400.png":
                imageId = R.mipmap.weather_icon_400;
                break;
            case "weather_icon_401.png":
                imageId = R.mipmap.weather_icon_401;
                break;
            case "weather_icon_402.png":
                imageId = R.mipmap.weather_icon_402;
                break;
            case "weather_icon_403.png":
                imageId = R.mipmap.weather_icon_403;
                break;
            case "weather_icon_404.png":
                imageId = R.mipmap.weather_icon_404;
                break;
            case "weather_icon_405.png":
                imageId = R.mipmap.weather_icon_405;
                break;
            case "weather_icon_406.png":
                imageId = R.mipmap.weather_icon_406;
                break;
            case "weather_icon_406n.png":
                imageId = R.mipmap.weather_icon_406;
                break;
            case "weather_icon_407.png":
                imageId = R.mipmap.weather_icon_407;
                break;
            case "weather_icon_407n.png":
                imageId = R.mipmap.weather_icon_407n;
                break;
            case "weather_icon_408.png":
                imageId = R.mipmap.weather_icon_408;
                break;
            case "weather_icon_409.png":
                imageId = R.mipmap.weather_icon_409;
                break;
            case "weather_icon_410.png":
                imageId = R.mipmap.weather_icon_410;
                break;
            case "weather_icon_499.png":
                imageId = R.mipmap.weather_icon_499;
                break;
            case "weather_icon_500.png":
                imageId = R.mipmap.weather_icon_500;
                break;
            case "weather_icon_501.png":
                imageId = R.mipmap.weather_icon_501;
                break;
            case "weather_icon_502.png":
                imageId = R.mipmap.weather_icon_502;
                break;
            case "weather_icon_503.png":
                imageId = R.mipmap.weather_icon_503;
                break;
            case "weather_icon_504.png":
                imageId = R.mipmap.weather_icon_504;
                break;
            case "weather_icon_507.png":
                imageId = R.mipmap.weather_icon_507;
                break;
            case "weather_icon_508.png":
                imageId = R.mipmap.weather_icon_508;
                break;
            case "weather_icon_509.png":
                imageId = R.mipmap.weather_icon_509;
                break;
            case "weather_icon_510.png":
                imageId = R.mipmap.weather_icon_510;
                break;
            case "weather_icon_511.png":
                imageId = R.mipmap.weather_icon_511;
                break;
            case "weather_icon_512.png":
                imageId = R.mipmap.weather_icon_512;
                break;
            case "weather_icon_513.png":
                imageId = R.mipmap.weather_icon_513;
                break;
            case "weather_icon_514.png":
                imageId = R.mipmap.weather_icon_514;
                break;
            case "weather_icon_515.png":
                imageId = R.mipmap.weather_icon_515;
                break;
            case "weather_icon_900.png":
                imageId = R.mipmap.weather_icon_900;
                break;
            case "weather_icon_901.png":
                imageId = R.mipmap.weather_icon_901;
                break;
            case "weather_icon_999.png":
                imageId = R.mipmap.weather_icon_999;
                break;
            default:
                imageId = R.mipmap.weather_icon_100;
                break;

        }
        return imageId;
    }
}