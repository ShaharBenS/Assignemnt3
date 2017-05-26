package SharedClasses;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by gal on 26/05/2017.
 */
public class MenuOP {

    private String message;
    private MenuOP father;
    private LinkedList<MenuOP> sones;
    private Runnable execute;

    public MenuOP (String messege){
        this.message=messege;
        this.sones=new LinkedList<MenuOP>();
        this.execute=null;
    }

    public MenuOP (String message, Runnable run){
        this.message=message;
        this.sones=null;
        this.execute=run;
    }

    public void addSon(MenuOP m){
        m.father=this;
        if (this.execute==null)
            this.sones.addLast(m);
    }

    public void execute(){
        if (this.execute==null){
            Scanner c = new Scanner(System.in);
            MenuOP m[] = (MenuOP[]) this.sones.toArray();
            while (true) {
                int i = 0;
                for (; i < m.length; i++) {
                    System.out.println((i + 1) + ". " + m[i].message);
                }
                if (this.father != null)
                    System.out.println((i + 1) + ". go back");
                else
                    System.out.println((i + 1) + ". logout");
                String s = c.nextLine();
                int e = Integer.parseInt(s);
                if ((e - 1) >= 0 && e <= m.length) {
                    try {
                        m[e].execute();
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                } else if (e == m.length + 1) {
                    return;
                }
                else{
                    System.out.println("ERROR: "+s+" is not a valid input, please try again");
                }
            }
        }
        else {
            try{
                this.execute.run();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                this.execute();
            }
        }
    }
}
