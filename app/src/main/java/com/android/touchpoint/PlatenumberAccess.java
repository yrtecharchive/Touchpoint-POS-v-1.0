package com.android.touchpoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class PlatenumberAccess extends AppCompatActivity implements Billing.BillingListener {

    private EditText parkingCodePlate;
    private Button btnProceedPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_platenumber_access);

        parkingCodePlate = findViewById(R.id.parkingCodePlate);
        btnProceedPlate = findViewById(R.id.btnProceedPlate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnProceedPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plateCode = parkingCodePlate.getText().toString();
                if (!plateCode.isEmpty()) {
                    Billing billing = new Billing(PlatenumberAccess.this);
                    billing.fetchBillingInfo(plateCode);
                } else {
                    Toast.makeText(PlatenumberAccess.this, "Please enter a plate number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBillingInfoReceived(String result) {
        Map<String, String> parsedData = DecodejsonResponse.parseResponse(result);

        if (parsedData.containsKey("error")) {
            Toast.makeText(this, parsedData.get("error"), Toast.LENGTH_LONG).show();
        } else {
            // Create an intent to start BillingInformationScreen
            Intent intent = new Intent(this, BillingInformationScreen.class);

            // Pass each piece of parsed data as an extra
            for (Map.Entry<String, String> entry : parsedData.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }

            // Start BillingInformationScreen activity
            startActivity(intent);
        }
    }
}
