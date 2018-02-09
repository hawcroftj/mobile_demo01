package rrc.bit.demo01_check_connection;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grab buttons and assign onClickListeners
        Button connection = findViewById(R.id.btnConnection);
        connection.setOnClickListener(this);

        Button connectionType = findViewById(R.id.btnType);
        connectionType.setOnClickListener(this);

        Button location = findViewById(R.id.btnGPS);
        location.setOnClickListener(this);

        // initialize ConnectivityManager and get the CONNECTIVITY_SERVICE from context
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public void onClick(View view) {
        // handle button clicks
        switch(view.getId()) {
            case R.id.btnConnection:
                checkConnection();
                break;
            case R.id.btnType:
                checkConnectionType();
                break;
            case R.id.btnGPS:
                checkLocationConnection();
                break;
        }
    }

    private void checkConnection(){
        // initialize NetworkInfo and assign ActiveNetworkInfo
        // scope is important here, because we want to get the current connection
        // state when the onClick method is called
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        // if activeNetwork is not null, and a network connection is detected,
        // isConnected is true
        boolean isConnected = activeNetwork != null &&
                              activeNetwork.isConnectedOrConnecting();

        // prepare output to indicate connection status
        String message = isConnected ? "Connection Detected!" : "No Connection Detected!";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void checkConnectionType() {
        // initialize networkInfo and assign ActiveNetworkInfo
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        String message = "";

        if(activeNetwork != null) {
            // output message is assigned based on the detected connection type
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    message = "WiFi is connected!";
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    message = "Mobile Network is connected!";
                    break;
            }
        } else {
            message = "No connection detected!";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void checkLocationConnection() {
        // initialize LocationManager and get LOCATION_SERVICE from context
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String message = "";

        // prepare output message to indicate whether location services are enabled
        try {
            message = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ?
                                                        "Location Services are enabled!" :
                                                        "Location Services are not enabled!";
        } catch(Exception e) { e.printStackTrace(); }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
