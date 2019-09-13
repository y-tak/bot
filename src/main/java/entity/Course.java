package entity;


import java.util.Date;


public class Course {

    private int id;

    private String title;
    private String newBody;


    private Date dateNews=new Date();

    private String organiz;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewBody() {
        return newBody;
    }

    public void setNewBody(String newBody) {
        this.newBody = newBody;
    }

    public Date getDateNews() {
        return dateNews;
    }

    public void setDateNews(Date dateNews) {
        this.dateNews = dateNews;
    }

    public String getOrganiz() {
        return organiz;
    }

    public void setOrganiz(String organiz) {
        this.organiz = organiz;
    }
}
