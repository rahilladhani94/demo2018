package demo.demopjt.Database;

/**
 * Created by abc on 01-06-2017.
 */
public class Contact {

    //private variables
    int _id;
    String _name;
    String price;
    String catid;



    public Contact(int id, String name,String price,String catid){
        this._id = id;
        this._name = name;
        this.price = price;
        this.catid = catid;

    }
    // Empty constructor
    public Contact(){

    }

    // constructor
    public Contact( String name, String price,String catid){

        this._name = name;
        this.price = price;
        this.catid = catid;


    }


    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }
    public String getPrice() {
        return price;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
