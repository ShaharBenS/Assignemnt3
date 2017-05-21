package SharedClasses;

/**
 * Created by Shahar on 29/03/17.
 */
public class Category
{
    private int id;
    private String name;
    private int idFather;  // -1 represents no ID

    public Category(int id,String name)
    {
        this.id = id;
        this.name = name;
        idFather = -1;
    }
    public Category(int id, String name, int idFather)
    {
        this.id = id;
        this.name = name;
        this.idFather = idFather;
    }
    public Category()
    {
        id = 0;name = ""; idFather = 0;
    }

    public Category(String s)
    {
        String[] parts = s.split("\\s+");
        id = Integer.parseInt(parts[0]);
        name = parts[1];
        try{
            idFather = Integer.parseInt(parts[2]);
        }catch(Exception e){idFather = -1;}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdFather() {
        return idFather;
    }

    public void setIdFather(int idFather) {
        this.idFather = idFather;
    }

    public boolean equals(Category category)
    {
        return category.id == this.id && category.idFather == this.idFather
                && category.name.equals(this.name);
    }
}
