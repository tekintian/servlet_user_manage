package cn.tekin.domain;

public class User {
    private String name;
    private String pwd;
    private String id;
    private String email;
    private String grade;
    private String unknow;
    private String remark;

    public String getUnknow() {
        return unknow;
    }

    public void setUnknow(String unknow) {
        this.unknow = unknow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



}
