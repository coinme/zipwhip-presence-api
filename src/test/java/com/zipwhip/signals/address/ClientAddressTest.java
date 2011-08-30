package com.zipwhip.signals.address;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 8/29/11
 * Time: 12:29 PM
 */
public class ClientAddressTest {

    @Test
    public void testEquals() throws Exception {

        ClientAddress ca1 = new ClientAddress("123456");
        ClientAddress ca2 = new ClientAddress("654321");

        Assert.assertTrue(ca1.equals(ca1));
        Assert.assertFalse(ca1.equals(null));
        Assert.assertFalse(ca1.equals(ca2));
        Assert.assertFalse(ca2.equals(ca1));

        ca2.setClientId(ca1.getClientId());
        Assert.assertTrue(ca1.equals(ca2));
    }

}
