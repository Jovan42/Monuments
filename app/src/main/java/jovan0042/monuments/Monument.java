package jovan0042.monuments;

/**
 * Created by Jovan on 5/16/2016.
 */
public class Monument {
    private String name;
    private String user;
    private String type;
    private String description;


    public Monument(String name, String user, String type, String description) {
        this.name = name;
        this.user = user;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {return description;}

}
