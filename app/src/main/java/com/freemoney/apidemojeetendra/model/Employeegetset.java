package com.freemoney.apidemojeetendra.model;

public class Employeegetset {
private String Empid;

    public String getEmpage() {
        return Empage;
    }

    public void setEmpage(String empage) {
        Empage = empage;
    }

    private String Empage;

    public String getEmpgender() {
        return Empgender;
    }

    public void setEmpgender(String empgender) {
        Empgender = empgender;
    }

    private String Empgender;

    public String getEmpaddress() {
        return Empaddress;
    }

    public void setEmpaddress(String empaddress) {
        Empaddress = empaddress;
    }

    public String getEmplocation() {
        return Emplocation;
    }

    public void setEmplocation(String emplocation) {
        Emplocation = emplocation;
    }

    private String Empaddress;
private String Emplocation;

    public Employeegetset(String empid, String empname, String empemail, String empmobile,String empaddress,String emplocation,String empgender,String empage) {
        Empid = empid;
        Empname = empname;
        Empemail = empemail;
        Empmobile = empmobile;
        Empaddress=empaddress;
        Emplocation=emplocation;
        Empgender=empgender;
        Empage=empage;
    }

    public String getEmpid() {
        return Empid;
    }

    public void setEmpid(String empid) {
        Empid = empid;
    }

    public String getEmpname() {
        return Empname;
    }

    public void setEmpname(String empname) {
        Empname = empname;
    }

    public String getEmpemail() {
        return Empemail;
    }

    public void setEmpemail(String empemail) {
        Empemail = empemail;
    }

    public String getEmpmobile() {
        return Empmobile;
    }

    public void setEmpmobile(String empmobile) {
        Empmobile = empmobile;
    }

    private String Empname;
private String Empemail;
private String Empmobile;
}
