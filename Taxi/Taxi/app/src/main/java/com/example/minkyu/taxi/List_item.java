package com.example.minkyu.taxi;

public class List_item extends Main2Activity {
    private String des;
    private  String dep;
    public String getdes(){
        return  des;
    }
    public String getdep(){
        return dep;
    }

    public List_item(String dep, String des){
        this.dep = dep;
        this.des = des;
    }

}

