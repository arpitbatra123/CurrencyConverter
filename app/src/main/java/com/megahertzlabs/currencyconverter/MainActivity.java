package com.megahertzlabs.currencyconverter;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.megahertzlabs.currencyconverter.service.CurrencyConverterService;
import com.megahertzlabs.currencyconverter.service.CurrencyConverterServiceImpl;

import org.json.JSONObject;

import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button n0;
    Button n1;
    Button n2;
    Button n3;
    Button n4;
    Button n5;
    Button n6;
    Button n7;
    Button n8;
    Button n9;
    Button ndec;
    Button nac;
    Button ngo;
    Button from;
    Button to;
    Button ndel;
    TextView fromt;
    Double exrate = 0.0;
    Double converted;
    String m;

    private CurrencyConverterService currencyConverterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupListeners();
        currencyConverterService = new CurrencyConverterServiceImpl().getCurrencyConverterService();
    }

    private void setupListeners() {
        nac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toset = "0.000";
                fromt.setText("");
                fromt.setHint(toset);

            }
        });

        ndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(fromt.getText().toString().isEmpty())) {
                    String toset = fromt.getText().toString().substring(0, fromt.getText().toString().length() - 1);
                    fromt.setText(toset);

                } else {
                    fromt.setText("0.000");
                }
            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, from);
                popupMenu.getMenuInflater().inflate(R.menu.menufrom, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        from.setText(menuItem.getTitle().toString().substring(menuItem.getTitle().toString().length() - 3));
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, to);
                popupMenu.getMenuInflater().inflate(R.menu.menufrom, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        to.setText(menuItem.getTitle().toString().substring(menuItem.getTitle().toString().length() - 3));
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });
    }

    private void convert() {
        String fromtext = from.getText().toString();
        final String totext = to.getText().toString();

        if (fromtext.equals(totext)) {
            Toast.makeText(MainActivity.this, "From and to Values are Same", Toast.LENGTH_SHORT).show();
            return;
        } else if (fromt.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please Enter a Value", Toast.LENGTH_SHORT).show();
            return;

        }

        if (!isConnected()) {
            Toast.makeText(MainActivity.this, "No Internet Connection or Host Unreachable", Toast.LENGTH_SHORT).show();
        } else {

            currencyConverterService.getCurrency(fromtext).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String jsonString = response.body().string();

                        //No need for two JSONObject's
                        JSONObject conversionResult = (JSONObject) new JSONObject(jsonString).get("rates");

                        m = conversionResult.getString(totext);
                        //Toast.makeText(MainActivity.this,"Exchange Rate = "+m,Toast.LENGTH_SHORT).show();
                        exrate = Double.parseDouble(m);
                        String fromvals = fromt.getText().toString();
                        if (fromvals.equals("."))
                            fromvals = "0.0";
                        Double fromval = Double.parseDouble(fromvals);
                        converted = exrate * fromval;
                        //Toast.makeText(MainActivity.this,Double.toString(converted),Toast.LENGTH_SHORT).show();
                        fromt.setText("");
                        fromt.setHintTextColor(Color.parseColor("#5c007a"));
                        //fromt.setLines(1);
                        if ((String.format(Locale.getDefault(), "%.4f", converted).length()) > 13) {
                            fromt.setTextSize(28f);
                        }
                        fromt.setHint("= " + String.format(Locale.getDefault(), "%.4f", converted));


                    } catch (Exception e) {
                        //Additional error handling
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Connection error handling
                }
            });

        }
    }

    private void setupViews() {
        n0 = (Button) findViewById(R.id.button0);
        n1 = (Button) findViewById(R.id.button1);
        n2 = (Button) findViewById(R.id.button2);
        n3 = (Button) findViewById(R.id.button3);
        n4 = (Button) findViewById(R.id.button4);
        n5 = (Button) findViewById(R.id.button5);
        n6 = (Button) findViewById(R.id.button6);
        n7 = (Button) findViewById(R.id.button7);
        n8 = (Button) findViewById(R.id.button8);
        n9 = (Button) findViewById(R.id.button9);
        ndec = (Button) findViewById(R.id.buttondec);
        nac = (Button) findViewById(R.id.buttonac);
        ngo = (Button) findViewById(R.id.buttonGO);
        from = (Button) findViewById(R.id.buttonfrom);
        to = (Button) findViewById(R.id.buttonto);
        fromt = (TextView) findViewById(R.id.textViewfrom);
        ndel = (Button) findViewById(R.id.imageButton);
    }

    public boolean isConnected() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED || connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public void input(View view) {
        Button btn = (Button) view;
        String currentText = fromt.getText().toString();

        if (currentText.indexOf('.') == -1) {
            fromt.append(btn.getText().toString());
        } else if (currentText.indexOf('.') >= 0) {
            if (btn.getText().toString().equals(".")) {
                Toast.makeText(MainActivity.this, "Multiple Decimal Points Cannot Exist", Toast.LENGTH_SHORT).show();
            } else
                fromt.append(btn.getText().toString());
        }
    }
}
