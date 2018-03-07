package com.example.vasupc.bvp_hackathon.Model;

/**
 * Created by VasuPC on 07-03-2018.
 */

public class Family {

    private String member1name ;
    private String member1number ;
    private String member2name ;
    private String member2number ;

    public Family(String member1name,String member1number,String member2name, String member2number){
        this.member1name = member1name;
        this.member1number = member1number;
        this.member2name = member2name;
        this.member2number = member2number;
    }

    public String getMember1name() {
        return member1name;
    }

    public void setMember1name(String member1name) {
        this.member1name = member1name;
    }

    public String getMember1number() {
        return member1number;
    }

    public void setMember1number(String member1number) {
        this.member1number = member1number;
    }

    public String getMember2name() {
        return member2name;
    }

    public void setMember2name(String member2name) {
        this.member2name = member2name;
    }

    public String getMember2number() {
        return member2number;
    }

    public void setMember2number(String member2number) {
        this.member2number = member2number;
    }

}
