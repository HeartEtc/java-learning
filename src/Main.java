/*
概念1:不能,自己定义了构造方法之后默认的不会生效,会报错
概念2:方法重载
概念3:报错,若写在后面会导致该变量未被初始化就被赋值
*/

public class Main {
    public static void main(String[] args) {
        book b1 = new book("算法导论");
        book b2 = new book("Java核心技术", "Horstmann");
        book b3 = new book("CSAPP", "Bryant", 139.0);
        System.out.println(b1);   // 期望 author=未知, price=0.0
        System.out.println(b2);
        System.out.println(b3);
    }
}

class book {
    private String title;
    private String author;
    private double price;

    public book(String title) {
        this(title,"未知", 0.0);
    }

    public book(String title, String author) {
        this(title,author, 0.0);
    }

    public book(String title, String author, double price){
        this.title = title;
        this.author = author;
        this.price = price;
    }


    public String toString(){
        return "Book{title='" + title + "', author='" + author + "', price=" + price + "}";
    }
}
