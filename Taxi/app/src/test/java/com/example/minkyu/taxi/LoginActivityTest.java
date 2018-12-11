package com.example.minkyu.taxi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import junit.framework.TestCase;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Random;

import javax.xml.transform.Result;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginActivityTest extends TestCase {

    LoginActivity aa = Mockito.mock(LoginActivity.class);
    private static final boolean RESULT = new Random().nextBoolean();

    /*
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }
    */

    public void testIsConnected() {
        final Context context = Mockito.mock(Context.class);
        final NetworkInfo networkInfo = Mockito.mock(NetworkInfo.class);
        final ConnectivityManager manager = Mockito.mock(ConnectivityManager.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
        Mockito.when(manager.getActiveNetworkInfo()).thenReturn(networkInfo);
        Mockito.when(networkInfo.isConnected()).thenReturn(RESULT);

        assertEquals(networkInfo.isConnected(),aa.isConnected(context));
    }
    public void testIsConnectedReturnsFalseWhenActiveNetworkInfoIsNull() {
        final Context context = Mockito.mock(Context.class);
        final ConnectivityManager manager = Mockito.mock(ConnectivityManager.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
        Mockito.when(manager.getActiveNetworkInfo()).thenReturn(null);

        assertEquals(false, aa.isConnected(context));
    }

}