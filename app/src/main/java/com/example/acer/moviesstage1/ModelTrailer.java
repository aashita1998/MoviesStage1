package com.example.acer.moviesstage1;

class ModelTrailer {
    String mname,mkey,mt;

    public ModelTrailer(String n, String k, String t) {
        this.mname=n;
        this.mkey=k;
        this.mt=t;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }
}
