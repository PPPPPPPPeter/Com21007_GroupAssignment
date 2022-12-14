package com.example.trackingsensor

class Weather {
    var main:Main?=null;
    var weather:List<WeatherInfo>?=null;
}
class  Main{
    var temp:Float?=null;
    var pressure:Float?=null;
}
class  WeatherInfo{
    var main:String?=null;
    var description:String?=null;
    var icon:String?=null;
}