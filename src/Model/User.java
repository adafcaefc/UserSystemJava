package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User
{
    int id, idCategory;
    String name, email, password, photo;

    public User(int id, int idCategory, String name, String email, String password, String photo)
    {
        this.id = id;
        this.idCategory = idCategory;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
    }

    public HashMap<String, String> getData()
    {
        HashMap<String, String> map = new HashMap<>();
        //map.put("id", String.valueOf(this.id));
        map.put("idCategory", String.valueOf(this.idCategory));
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("password", this.password);
        map.put("photo", this.photo);
        return map;
    }

    public User(ResultSet rs) throws SQLException
    {
        this.id = rs.getInt("id");
        this.idCategory = rs.getInt("idCategory");
        this.name = rs.getString("name");
        this.email = rs.getString("email");
        this.password = rs.getString("password");
        this.photo = rs.getString("photo");
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdCategory()
    {
        return idCategory;
    }

    public void setIdCategory(int idCategory)
    {
        this.idCategory = idCategory;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }
}
