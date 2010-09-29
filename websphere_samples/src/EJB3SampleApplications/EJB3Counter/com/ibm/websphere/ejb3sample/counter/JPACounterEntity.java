// This program may be used, executed, copied, modified and distributed
// without royalty for the purpose of developing, using, marketing, or distributing.

package com.ibm.websphere.ejb3sample.counter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="EJB3COUNTERTABLE")

public class JPACounterEntity {

    @Id
    private String primarykey = "PRIMARYKEY";

    private int value = 0;

    public void setValue( int newValue )
    {
        System.out.println ("JPACounterEntity:setValue = " + newValue);
        value = newValue;
    }

    public int getValue()
    {
        System.out.println ("JPACounterEntity:getValue = " + value);
        return value;
    }

    public void setPrimaryKey( String newKey )
    {
        System.out.println ("JPACounterEntity:setPrimaryKey = '" + newKey + "'");
        primarykey = newKey;
    }

    public String getPrimaryKey()
    {
        System.out.println ("JPACounterEntity:getPrimaryKey = '" + primarykey + "'");
        return primarykey;
    }
}
