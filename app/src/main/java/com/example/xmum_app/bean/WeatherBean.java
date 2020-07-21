package com.example.xmum_app.bean;

import java.util.List;

public class WeatherBean {

    /**
     * error : 0
     * status : success
     * date : 2020-07-01
     * results : [{"currentCity":"北京","pm25":"","index":[{"des":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。","tipt":"穿衣指数","title":"穿衣","zs":"炎热"},{"des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","tipt":"洗车指数","title":"洗车","zs":"不宜"},{"des":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","tipt":"感冒指数","title":"感冒","zs":"少发"},{"des":"有降水，推荐您在室内进行低强度运动；若坚持户外运动，须注意选择避雨防滑并携带雨具。","tipt":"运动指数","title":"运动","zs":"较不宜"},{"des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","tipt":"紫外线强度指数","title":"紫外线强度","zs":"最弱"}],"weather_data":[{"date":"周三 07月01日 (实时：33℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/leizhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/leizhenyu.png","weather":"雷阵雨","wind":"南风微风","temperature":"33 ~ 22℃"},{"date":"周四","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhongyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","weather":"中雨","wind":"东风微风","temperature":"29 ~ 21℃"},{"date":"周五","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/leizhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"雷阵雨转多云","wind":"东北风微风","temperature":"27 ~ 20℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/leizhenyu.png","weather":"多云转雷阵雨","wind":"西南风微风","temperature":"28 ~ 21℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    private List<ResultsBean> results;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * currentCity : 北京
         * pm25 :
         * index : [{"des":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。","tipt":"穿衣指数","title":"穿衣","zs":"炎热"},{"des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","tipt":"洗车指数","title":"洗车","zs":"不宜"},{"des":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","tipt":"感冒指数","title":"感冒","zs":"少发"},{"des":"有降水，推荐您在室内进行低强度运动；若坚持户外运动，须注意选择避雨防滑并携带雨具。","tipt":"运动指数","title":"运动","zs":"较不宜"},{"des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","tipt":"紫外线强度指数","title":"紫外线强度","zs":"最弱"}]
         * weather_data : [{"date":"周三 07月01日 (实时：33℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/leizhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/leizhenyu.png","weather":"雷阵雨","wind":"南风微风","temperature":"33 ~ 22℃"},{"date":"周四","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhongyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","weather":"中雨","wind":"东风微风","temperature":"29 ~ 21℃"},{"date":"周五","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/leizhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"雷阵雨转多云","wind":"东北风微风","temperature":"27 ~ 20℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/leizhenyu.png","weather":"多云转雷阵雨","wind":"西南风微风","temperature":"28 ~ 21℃"}]
         */

        private String currentCity;
        private String pm25;
        private List<IndexBean> index;
        private List<WeatherDataBean> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<WeatherDataBean> getWeather_data() {
            return weather_data;
        }

        public void setWeather_data(List<WeatherDataBean> weather_data) {
            this.weather_data = weather_data;
        }

        public static class IndexBean {
            /**
             * des : 天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。
             * tipt : 穿衣指数
             * title : 穿衣
             * zs : 炎热
             */

            private String des;
            private String tipt;
            private String title;
            private String zs;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getTipt() {
                return tipt;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }
        }

        public static class WeatherDataBean {
            /**
             * date : 周三 07月01日 (实时：33℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/leizhenyu.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/leizhenyu.png
             * weather : 雷阵雨
             * wind : 南风微风
             * temperature : 33 ~ 22℃
             */

            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
