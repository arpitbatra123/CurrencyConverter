package com.megahertzlabs.currencyconverter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button n0,n1,n2,n3,n4,n5,n6,n7,n8,n9,ndec,nac,ngo,from,to;
    TextView fromt;
    Double exrate=0.0,converted;
    String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n0=(Button)findViewById(R.id.button0);
        n1=(Button)findViewById(R.id.button1);
        n2=(Button)findViewById(R.id.button2);
        n3=(Button)findViewById(R.id.button3);
        n4=(Button)findViewById(R.id.button4);
        n5=(Button)findViewById(R.id.button5);
        n6=(Button)findViewById(R.id.button6);
        n7=(Button)findViewById(R.id.button7);
        n8=(Button)findViewById(R.id.button8);
        n9=(Button)findViewById(R.id.button9);
        ndec=(Button)findViewById(R.id.buttondec);
        nac=(Button)findViewById(R.id.buttonac);
        ngo=(Button)findViewById(R.id.buttonGO);
        from=(Button)findViewById(R.id.buttonfrom);
        to=(Button)findViewById(R.id.buttonto);
        fromt=(TextView)findViewById(R.id.textViewfrom);
        nac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toset="0.000";
                fromt.setText("");
                fromt.setHint(toset);

            }
        });



        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,from);
                popupMenu.getMenuInflater().inflate(R.menu.menufrom,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        from.setText(menuItem.getTitle().toString().substring(menuItem.getTitle().toString().length()-3));
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,to);
                popupMenu.getMenuInflater().inflate(R.menu.menufrom,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        to.setText(menuItem.getTitle().toString().substring(menuItem.getTitle().toString().length()-3));
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fromtext=from.getText().toString();
                String totext=to.getText().toString();

                if(fromtext.equals(totext))
                {
                    Toast.makeText(MainActivity.this, "From and to Values are Same", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(fromt.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter a Value", Toast.LENGTH_SHORT).show();
                    return;

                }


                 if (!isConnected())
                {
                    Toast.makeText(MainActivity.this,"No Internet Connection or Host Unreachable",Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    JSONFetch obj = new JSONFetch();

                    obj.recieve(fromtext,totext);
                    obj.execute();










                }


            }
        });









    }


//    private boolean isNetwork() {
//        final InetAddress address;
//        try {
//            address = InetAddress.getByName("http://api.fixer.io");
//            return !address.equals("");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//
//
//
//
//    }

    public boolean isConnected() {
        String command="ping -c 1 google.com";
        try {
            return (Runtime.getRuntime().exec(command).waitFor()==0);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }


        return false;
    }



    public void input(View view)
    {
        Button btn = (Button) view;
        String currentText=fromt.getText().toString();


            if (currentText.indexOf('.') == -1 )
            {
                fromt.append(btn.getText().toString());
            }

            else if(currentText.indexOf('.') >=0 )
            {
                if(btn.getText().toString().equals("."))
                {
                    Toast.makeText(MainActivity.this,"Multiple Decimal Points Cannot Exist",Toast.LENGTH_SHORT).show();


                }

                else
                    fromt.append(btn.getText().toString());




            }


    }

    class JSONFetch extends AsyncTask<Void,Void,Void>
    {
           String f,t;
           String get="";Double a;
           double fv,tov;
           JSONObject jsonObject,jsonObject1;
           ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        public void recieve(String fromthis,String tothat)
        {
            f=fromthis;
            //Toast.makeText(MainActivity.this,f,Toast.LENGTH_SHORT).show();
            t=tothat;
            //Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();









        }
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url=new URL("http://api.fixer.io/latest?base="+f);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String data="";
                while((data=bufferedReader.readLine())!=null)
                {
                    get+=data;

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;


        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Calculating");
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            String ratesjson = null;
            try {
                jsonObject=new JSONObject(get);
                ratesjson = jsonObject.getString("rates");
                jsonObject1 =new JSONObject(ratesjson);
                 m=jsonObject1.getString(t);
                //Toast.makeText(MainActivity.this,"Exchange Rate = "+m,Toast.LENGTH_SHORT).show();
                exrate=Double.parseDouble(m);
                String fromvals=fromt.getText().toString();
                if(fromvals.equals("."))
                    fromvals="0.0";
                Double fromval=Double.parseDouble(fromvals);
                converted=exrate*fromval;
                //Toast.makeText(MainActivity.this,Double.toString(converted),Toast.LENGTH_SHORT).show();
                fromt.setText("");
                fromt.setHintTextColor(Color.parseColor("#5c007a"));
                //fromt.setLines(1);
                if((String.format(Locale.getDefault(),"%.4f",converted).length())>13)
                {
                    fromt.setTextSize(28f);
                }
                fromt.setHint("= "+String.format(Locale.getDefault(),"%.4f",converted));


            } catch (JSONException e) {
                e.printStackTrace();
            }



            super.onPostExecute(aVoid);
        }


    }






}
