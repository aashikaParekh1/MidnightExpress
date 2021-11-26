package com.example.midnightexpress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ImageView image;
    TextView name;
    TextView cartList;
    TextView totalAmount;
    ImageView all;
    TextView money;
    CheckBox checker;
    ListView listView;
    Button view;


    String PREFS_NAME = "AASH";
    int selected = 0;
    int start = 0;
    int amount = 0;
    int random;

    public static final String starting = "start";
    public static final String theKey = "saved";
    public static final String theKey2 = "saved";
    public static final String select = "select";



    ArrayList<Menu> list;
    ArrayList<Menu> checkout;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(theKey, list);
        outState.putSerializable(theKey2, checkout);
        outState.putInt(select, selected);
        outState.putInt(starting, start);


    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        list = new ArrayList<>();
        checkout = new ArrayList<>();
        //Intent intent = new Intent(CartActivity.this, ViewCartActivity.class);
        //intent.putExtra("FILES_TO_SEND", checkout);
        //startActivity(intent);

        image = findViewById(R.id.id_adapter_img);
        name = findViewById(R.id.id_adapter_text);
        all = findViewById(R.id.id_adapter_allergy);
        money = findViewById(R.id.id_adapter_money);
        checker = findViewById(R.id.id_adapter_checkbox);
        listView = findViewById(R.id.id_listView);//list view
        view = findViewById(R.id.id_viewCart);
        cartList = findViewById(R.id.id_orderList);
        totalAmount = findViewById(R.id.id_total);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, FinalActivity.class));

            }
        });


        Menu PretzelBites = new Menu((R.drawable.pretzel), "Pretzel Bites", "$3",(R.drawable.gluten), false, 3);
        Menu PastaSalad = new Menu((R.drawable.pasta), "Pasta Caprese Salad", "$5", (R.drawable.vegan), false, 5);
        Menu WaffleFries = new Menu((R.drawable.fries), "Waffle Fries", "$4", (R.drawable.gluten), false, 4);
        Menu SushiRolls = new Menu((R.drawable.sushi), "Sushi Rolls", "$6", (R.drawable.vegan), false, 6);
        Menu GrilledCheese = new Menu((R.drawable.sandwich), "Grilled Caprese", "$6", (R.drawable.jain), false, 6);
        Menu CinnamonRolls = new Menu((R.drawable.rolls), "Cinnamon Rolls", "$4", (R.drawable.gluten), false, 4);
        Menu Donuts = new Menu((R.drawable.donuts), "Donuts", "$2", (R.drawable.jain), false, 2);
        Menu Cheesecake = new Menu((R.drawable.cheesecake), "Cheesecake", "$5", (R.drawable.jain), false, 5);
        Menu Smoothies = new Menu((R.drawable.smooth), "Fresh Smoothies", "$5", (R.drawable.vegan), false, 5);
        Menu Coffee = new Menu((R.drawable.coffee), "Fresh Brew Coffee", "$3", (R.drawable.vegan), false, 3);

        if (savedInstanceState != null) {
            list = (ArrayList<Menu>) savedInstanceState.getSerializable(theKey);
            checkout = (ArrayList<Menu>) savedInstanceState.getSerializable(theKey2);
            selected = savedInstanceState.getInt(select);

        }
        else {

            list.add(PretzelBites);
            list.add(PastaSalad);
            list.add(WaffleFries);
            list.add(SushiRolls);
            list.add(GrilledCheese);
            list.add(CinnamonRolls);
            list.add(Donuts);
            list.add(Cheesecake);
            list.add(Smoothies);
            list.add(Coffee);

            Log.d("MENU LIST: ", String.valueOf(list));
        }


        CustomAdapter adapter = new CustomAdapter(this, R.layout.adapter_cart, list);
        listView.setAdapter(adapter);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Confirmation!");
                builder.setMessage("Once you hit confirm, you won't be able to go back and change you order.");

                builder.setNegativeButton("Change order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Confirm order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(CartActivity.this, FinalActivity.class);
                        intent.putExtra("key_int", amount);
                        startActivity(intent);

                    }
                });
                builder.show();

            }
        });

    }

    public class CustomAdapter extends ArrayAdapter<Menu>
    {
        Context myContext;
        int xml;
        ArrayList<Menu> list;
        //ArrayList<Menu> checkout;
        ArrayList<CheckingOut> checkout = new ArrayList<CheckingOut>();


        public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Menu> objects) {
            super(context, resource, objects);

            this.myContext = context;
            this.xml = resource;
            this.list = objects;
            //this.checkout = objects;

        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = inflater.inflate(xml, null);

            Log.d("MENU","WORKING?");
            TextView textView = adapterView.findViewById(R.id.id_adapter_text);
            TextView textView2 = adapterView.findViewById(R.id.id_adapter_money);
            ImageView img = adapterView.findViewById(R.id.id_adapter_img);
            ImageView img2 = adapterView.findViewById(R.id.id_adapter_allergy);
            CheckBox box = adapterView.findViewById(R.id.id_adapter_checkbox);
            textView.setText(list.get(position).getTitle());
            textView2.setText(list.get(position).getCost());
            img.setImageResource(list.get(position).getImage());
            img2.setImageResource(list.get(position).getSymb());




            Menu item = getItem(position);
            //textView.setText(item.title);
            box.setChecked(item.checked);

            final CheckingOut fullList = new CheckingOut((getItem(position).getTitle()), (getItem(position).getCost()));
            Log.d("OUTSIDE", String.valueOf(checkout.toString()));


            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean isChecked)
                {
                    Menu item = getItem(position);


                    if(item.checked = isChecked) {

                        Log.d("TAG", String.valueOf(fullList));
                        checkout.add(fullList);
                        Log.d("ISCHECKED", String.valueOf(checkout.toString()));

                        //cartList.setText(checkout.toString());
                        cartList.setText(checkout.toString().replace("[","").replace("]","").replace(",","\n"));
                        amount += (getItem(position).getCostAmount());
                        totalAmount.setText("Total: $" + amount);


                    }
                    else
                    {
                        Log.d("NOTCHECKED", item.getTitle());
                        for(int x = 0; x < checkout.size(); x++)
                        {
                            if(checkout.get(x).getTitle() == item.getTitle()){
                                checkout.remove(x);
                            }
                        }

                        //checkout.remove(getItem(position));
                        //Log.d("NOTCHECKED", (checkout.toString()));

                        cartList.setText(checkout.toString().replace("[", "").replace("]", "").replace(",", "\n"));
                        amount -= (getItem(position).getCostAmount());
                        totalAmount.setText("Total: $" + amount);
                    }

                }
            });


            Log.d("LIST: ", String.valueOf(list));
            return adapterView;
        }

    }


}

