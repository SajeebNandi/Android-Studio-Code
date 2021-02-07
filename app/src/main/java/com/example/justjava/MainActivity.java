/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava.
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

 package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity>=100){
            Toast.makeText(this,"You can't have more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }else
            quantity=quantity+1;
        displayquantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity<=1){
            Toast.makeText(this,"You can't have less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }else
            quantity=quantity-1;
        displayquantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        If Whipped Cream is wanted to be added or not.
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
//        If Chocolate is wanted to be added or not.
        CheckBox ChocolateCheckbox=(CheckBox)findViewById(R.id.Chocolate_checkbox);
        Boolean hasChocolate=ChocolateCheckbox.isChecked();
//        The name of the coffee owner.
        EditText nameField =(EditText) findViewById(R.id.name_field);
        String name=nameField.getText().toString();
//
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject)+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream is whether or not the user wants the topping.
     * @param addChocolate is whether or not the user wants the topping.
     * @return the total price.
     */
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate) {
        //unit price of the coffee.
        int baseprice=5;
        //add $1 if Whipped Cream is added.
        if (addWhippedCream){
            baseprice=baseprice+1;
        }
        //add $2 if Chocolate is added.
        if (addChocolate){
            baseprice=baseprice+2;
        }
        //calculating the price multiplying by quantity.
        int price = quantity * baseprice;
        return price;
    }

    /**
     * Creates summary of the order.
     * @param price of the order.
     * @param addWhippedCream is whether or not the user wants the topping.
     * @param addChocolate is whether or not the user wants the topping.
     * @param name  of the coffee owner.
     * @return text summary.
     */
    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage=getString(R.string.java_name)+name;
        priceMessage=priceMessage+"\n"+getString(R.string.add_whipped_cream)+ addWhippedCream;
        priceMessage=priceMessage+"\n"+getString(R.string.add_chocolate)+addChocolate;
        priceMessage=priceMessage+"\n"+getString(R.string.java_quantity)+quantity;
        priceMessage=priceMessage+"\n"+getString(R.string.total)+price+"\n"+getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayquantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text__view);
        quantityTextView.setText("" + number);
    }


}