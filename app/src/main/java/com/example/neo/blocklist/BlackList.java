package com.example.neo.blocklist;

public class BlackList {
public long id;
public String phoneNumber;
public BlackList(){}
public BlackList(final String phoneMumber){
    this.phoneNumber=phoneMumber;
}

    @Override
    public boolean equals(final Object obj) {
    if (obj.getClass().isInstance(new BlackList())){
        final BlackList bl=(BlackList)obj;
        if (bl.phoneNumber.equalsIgnoreCase(this.phoneNumber))
            return true;
    }
        return false;
    }
}
