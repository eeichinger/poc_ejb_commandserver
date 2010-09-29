// This program may be used, executed, copied, modified and distributed
// without royalty for the purpose of developing, using, marketing, or distributing.

package com.ibm.websphere.ejb3sample.counter;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Interceptors ( Audit.class )

public class StatelessCounterBean implements LocalCounter, RemoteCounter {

    private static final String CounterDBKey = "PRIMARYKEY";

    // Use container managed persistence - inject the EntityManager
    @PersistenceContext (unitName="Counter")
    private EntityManager em;
    
    public int increment()
    {
        int result = 0;
        
        try {

            JPACounterEntity counter = em.find(JPACounterEntity.class, CounterDBKey);

            if ( counter == null ) {
                counter = new JPACounterEntity();
                counter.setPrimaryKey(CounterDBKey);
                em.persist( counter );
            }

            counter.setValue( counter.getValue() + 1 );
            em.flush();
            em.clear();

            result = counter.getValue();

        } catch (Throwable t) {            
            System.out.println("StatelessCounterBean:increment - caught unexpected exception: " + t);
            t.printStackTrace();
        }

        return result;
    }


    public int getTheValue()
    {
        int result = 0;

        try {
            JPACounterEntity counter = em.find(JPACounterEntity.class, CounterDBKey);

            if ( counter == null ) {
                counter = new JPACounterEntity();
                em.persist( counter );
                em.flush();
            }

            em.clear();

            result = counter.getValue();
        } catch (Throwable t) {
            System.out.println("StatelessCounterBean:increment - caught unexpected exception: " + t);
            t.printStackTrace();
        }

        return result;
    }
}
