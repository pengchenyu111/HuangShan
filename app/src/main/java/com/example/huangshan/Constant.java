package com.example.huangshan;

/**
 * 常量类
 */
public class Constant {

//    服务器的IP地址
    public static final String URL = "http://192.168.43.232:8080/HuangShanServer/";

//    服务器保存用户头像的地址
    public static final String URL_HEADICONS = "http://192.168.43.232:8080/HuangShan/UserHeadIcons/";

//    服务器保存notificationManage轮播图的地址
    public static final String[] URL_BANNER = {
        "http://192.168.43.232:8080/HuangShan/admin/notificationManageBanner/banner1.jpg",
        "http://192.168.43.232:8080/HuangShan/admin/notificationManageBanner/banner2.jpg",
        "http://192.168.43.232:8080/HuangShan/admin/notificationManageBanner/banner3.jpg",
        "http://192.168.43.232:8080/HuangShan/admin/notificationManageBanner/banner4.jpg",
        "http://192.168.43.232:8080/HuangShan/admin/notificationManageBanner/banner5.jpg"
    };

//    必应每日一图，用来当天气的背景
    public static final String BING_PIC = "http://guolin.tech/api/bing_pic";

//    和风天气,账户、key
    public static final String HEFENGWEATHER_ID = "HE1909230909401643";
    public static final String HEFENGWEATHER_key = "49a1bbbcf777441fa9ada1fab001b29c";
//    和风天气插件key
    public static final String HEFENGWEATHER_PLUGIN_kEY = "e2d65c927f734e32acd18f6ac86de7fc";
//    和风天气黄山风景区光明顶location
    public static final String HEFENGWEATHER_LOCATION_HUANGSHAN_GUANGMINGDING = "CN101221008";
//    和风天气黄山风景区光明顶H5
    public static final String HEFENGWEATHER_H5 = "https://widget-page.heweather.net/h5/index.html?bg=1&md=0123456&lc=CN101221008&key=290dbd407f0045189b4825c916379d3b";


//     黄山的地址描述,adcode,经度,纬度
    public static final String HUANGSHAN_NAME = "中国安徽省黄山区";
    public static final String HUANGSHAN_ADCODE = "341003";
    public static final double HUANGSHAN_LATITUDE = 30.140751;
    public static final double HUANGSHAN_LONGITITUDE = 118.160515;

//    POI检索表
//    风景名胜/风景名胜/风景名胜
    public static final String POI_SCENERY = "110200";
//    风景名胜/风景名胜相关/旅游景点
    public static final String POI_TOURIST_ATTRACTION = "110000";
//    风景名胜/风景名胜相关/世界遗产
    public static final String POI_WORLD_HERITAGE = "110201";
//    风景名胜/风景名胜相关/国家级景点
    public static final String POI_NATIONAL_VIEW= "110202";
//    风景名胜/风景名胜相关/省级景点
    public static final String POI_PROVINCE_VIEW= "110203";
//    风景名胜/风景名胜相关/观景点
    public static final String POI_VIEW_POINT= "110209";
}
