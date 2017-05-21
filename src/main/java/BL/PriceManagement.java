package BL;

import DAL.Prices;

public class PriceManagement
{
   private Prices PRICES;

   public PriceManagement(Prices PRICES)
   {
       this.PRICES = PRICES;
   }


   public boolean updateSellPrice(String line)
   {
       
       String[] prop = line.split("\\s+");
       if(prop.length != 2) return false;
       try{
           int id = Integer.parseInt(prop[0]);
           double newPrice = Double.parseDouble(prop[1]);
           return PRICES.updateSellPrice(id, newPrice);
       } catch(Exception e){ return  false; }
   }

   public boolean addDiscount(String line)
   {
       String[] prop = line.split("\\s+");
       if(prop.length != 4) return false;
       try{
           int id = Integer.parseInt(prop[0]);
           if(prop[0].length() == 6)
           {
               int disc = Integer.parseInt(prop[1]);
               String[] start = prop[2].split("\\.");
               String[] end = prop[3].split("\\.");
               if(start.length != 3 || end.length != 3) return false;
               SharedClasses.Date Dstart = new SharedClasses.Date(Integer.parseInt(start[2]),Integer.parseInt(start[1]),Integer.parseInt(start[0]));
               SharedClasses.Date Dend = new SharedClasses.Date(Integer.parseInt(end[2]),Integer.parseInt(end[1]),Integer.parseInt(end[0]));
               return (PRICES.updatePercentage(id, disc) && PRICES.updateDate(true,id,Dstart) && PRICES.updateDate(false,id, Dend));
           }
           else if(prop[0].length() == 3)
           {
               int disc = Integer.parseInt(prop[1]);
               String[] start = prop[2].split("\\.");
               String[] end = prop[3].split("\\.");
               if(start.length != 3 || end.length != 3) return false;
               SharedClasses.Date Dstart = new SharedClasses.Date(Integer.parseInt(start[2]),Integer.parseInt(start[1]),Integer.parseInt(start[0]));
               SharedClasses.Date Dend = new SharedClasses.Date(Integer.parseInt(end[2]),Integer.parseInt(end[1]),Integer.parseInt(end[0]));
               return (PRICES.updateCategoryDiscount(id, disc ,Dstart,Dend));
           }
           else return false;
       } catch(Exception e){ return  false; }
   }
}
