package com.example.spidertask3_v2;

public class HistoryDataClass {
    String word, ety;

    public HistoryDataClass()
    {                                                           //overload
        word="";
        ety="";
    }

    public HistoryDataClass(String Head, String etym)
    {
        word=Head;
        ety=etym;
    }

    public String getEty() {
        return ety;
    }

    public String getWord() {
        return word;
    }

}
