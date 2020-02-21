package com.e.alfood.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    public String  Id;
    public String Name;
    public int Amount;
    public int Quantity;
    public String Img;


    public Item(String Id, String Name, int mAmount, String img) {
        this.Id = Id;
        this.Name = Name;
        this.Amount = mAmount;
        this.Img = img;
        this.Quantity = 1;

    }

    public int getTotal() {
        return Amount * Quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.Name);
        dest.writeInt(this.Amount);
        dest.writeInt(this.Quantity);
        dest.writeString(this.Img);
    }

    protected Item(Parcel in) {
        this.Id = in.readString();
        this.Name = in.readString();
        this.Amount = in.readInt();
        this.Quantity = in.readInt();
        this.Img = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public void addToQuantity(){
        this.Quantity += 1;
    }

    public void removeFromQuantity(){
        if(this.Quantity > 1){
            this.Quantity -= 1;
        }
    }
}