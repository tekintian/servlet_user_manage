package cn.tekin.servlet_demo.book;

//Serializable 序列化，便于在网络中传送 往集合中填放的时候可以区分
public class Book implements java.io.Serializable {
    private String id;
    private String name;
    private int num;
    private float price;
    private String writer;
    private String publishouse;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getNum() {return num;}

    public void setNum(int num) {this.num = num;}

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}
}

