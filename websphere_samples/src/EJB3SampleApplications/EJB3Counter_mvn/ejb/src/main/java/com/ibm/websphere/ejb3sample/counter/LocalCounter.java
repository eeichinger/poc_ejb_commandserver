// This program may be used, executed, copied, modified and distributed
// without royalty for the purpose of developing, using, marketing, or distributing.

package com.ibm.websphere.ejb3sample.counter;

import javax.ejb.Local;

@Local
public interface LocalCounter {
    public int increment();
    public int getTheValue();
}
