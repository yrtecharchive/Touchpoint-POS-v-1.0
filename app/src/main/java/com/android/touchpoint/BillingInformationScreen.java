package com.android.touchpoint;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BillingInformationScreen extends AppCompatActivity {
    private EditText etvclass, etetime, etparkingid, etamount, etstatus, etptime, etpaytime, access, etcode, etgate;
    private TextView tvvehicle, tvcode, tvetime, tvgate, tvpaytime, tvamount;
    private String vehiclecat;
    private RadioButton radioCash, radioGcash, radioPaymaya, radioComplimentary;
    private RadioGroup radioGroup;
    private Button btnproceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_billing_information_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeWidgets();
        initializeWidgetIds();

    }

    private void initializeWidgets() {
        tvvehicle = findViewById(R.id.tvVehicle);
        tvcode = findViewById(R.id.tvCode);
        tvetime = findViewById(R.id.tvEntryTime);
        tvgate = findViewById(R.id.tvGate);
        tvpaytime = findViewById(R.id.tvPaymentTime);
        tvamount = findViewById(R.id.tvAmountDue);

        // Initialize Radio Buttons
        radioCash = findViewById(R.id.radioCash);
        radioGcash = findViewById(R.id.radioGcash);
        radioPaymaya = findViewById(R.id.radioPaymaya);
        radioComplimentary = findViewById(R.id.radioComplimentary);

        radioGroup = findViewById(R.id.radioGroup);
//        Button
        btnproceed = findViewById(R.id.btnProceedPayment);
    }

    private void initializeWidgetIds() {
        vehiclecat = getIntent().getStringExtra("vclass");
        String parkingcode = getIntent().getStringExtra("code");
        String etime = getIntent().getStringExtra("entry_time");
        String gate = getIntent().getStringExtra("gate");
        String paytime = getIntent().getStringExtra("paymenttime");
        String amount = getIntent().getStringExtra("bill");
        String vehicle;

        if (vehiclecat != null) {
            if (vehiclecat.equals("1")) {
                vehicle = "Motorcycle";
            } else if (vehiclecat.equals("2")) {
                vehicle = "Car";
            } else if (vehiclecat.equals("3")) {
                vehicle = "BUS/Truck";
            } else {
                vehicle = "Unknown";
            }
        } else {
            vehicle = "Unknown";
        }

        tvvehicle.setText(vehicle);
        tvcode.setText(parkingcode);
        tvetime.setText(etime);
        tvgate.setText(gate);
        tvpaytime.setText(paytime);
        tvamount.setText(amount);

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int terminalid = 1;
                String gateId = getIntent().getStringExtra("gate");
                String access = getIntent().getStringExtra("access_type");
                String code = getIntent().getStringExtra("code");
                String vclass = getIntent().getStringExtra("vclass");
                String vrate = getIntent().getStringExtra("vehicleRate");
                String unixEtime = getIntent().getStringExtra("unixEntryTime");
                String unixPayTime = getIntent().getStringExtra("paymenttime");

                int selectedId = radioGroup.getCheckedRadioButtonId(); // Get selected radio button ID
                String selectedPaymentMethod = "Not selected";

                // Determine which button was clicked
                if (selectedId != -1) { // Check if any radio button is selected
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    selectedPaymentMethod = selectedRadioButton.getText().toString();
                }

                Toast.makeText(BillingInformationScreen.this, "Selected: " + selectedPaymentMethod, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
