package com.example.sauna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String inputDialogText1 = "";
    private String inputDialogText2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView title = findViewById(R.id.listTitle);
        Context context = this;
        TextView listView = findViewById(R.id.opt1_listSauna);
        listView.setTextSize(18);
        switch (item.getItemId()) {
            case R.id.opt1:  // Display List
                TextView saunaListView = findViewById(R.id.opt1_listSauna);
                if (SaunaList.getInstance().size() == 0) {
                    Toast.makeText(this, "There is no data to display.", Toast.LENGTH_SHORT).show();
                } else {
                    title.setText("List of all saunas");
                    title.setVisibility(View.VISIBLE);
                    saunaListView.setText(SaunaList.toPrintString());
                }
                return true;

            case R.id.opt2: // Load Data From UrL
                inputDialog D = new inputDialog("Enter File URL", this, "Load",
                      new callback() {
                          @Override
                          public void callback() {
                              try {
                                  if (SaunaList.loadFromFile(inputDialogText1))
                                      Toast.makeText(context, "Finished loading list from URL.", Toast.LENGTH_SHORT).show();
                                  else
                                      Toast.makeText(context, "Error loading list from URL.", Toast.LENGTH_SHORT).show();

                              } catch (Exception e) {
                                  Toast.makeText(context, "Error loading list from URL.", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                D.addInput(this, "URL", InputType.TYPE_CLASS_TEXT, 1);
                D.show();

                return true;

            case R.id.opt3: // Add New Sauna
                Intent intent = new Intent(this, AddSaunaActivity.class);
                startActivity(intent);
                return true;

            case R.id.opt4: // Show Details of a Sauna by product code
                intent = new Intent(this, DisplaySaunaActivity.class);

                D = new inputDialog("Show details of a sauna", this, "Show",
                      new callback() {
                          @Override
                          public void callback() {
                              String code = inputDialogText1;
                              if (code.length() == 0) return;
                              if (SaunaList.getInstance().contains(new Sauna(code))) {
                                  intent.putExtra("code", code);
                                  startActivity(intent);
                              } else {
                                  Toast.makeText(context, "Product code doesn't exist.", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                D.addInput(this, "Product Code", InputType.TYPE_CLASS_TEXT, 1);
                D.show();
                return true;

            case R.id.opt5: // Remove a Sauna
                D = new inputDialog("Remove a sauna", this, "Remove",
                      new callback() {
                          @Override
                          public void callback() {
                              String code = inputDialogText1;
                              if (SaunaList.getInstance().contains(new Sauna(code))) {
                                  SaunaList.removeSauna(new Sauna(code));
                                  Toast.makeText(context, "Sauna was just removed.", Toast.LENGTH_SHORT).show();
                              } else
                                  Toast.makeText(context, "Product code doesn't exist.", Toast.LENGTH_SHORT).show();
                          }
                      });
                D.addInput(this, "Product Code", InputType.TYPE_CLASS_TEXT, 1);
                D.show();

                return true;

            case R.id.opt6: // Adjust price by %
                if (SaunaList.getInstance().size() == 0) {
                    Toast.makeText(context, "There is no data yet", Toast.LENGTH_SHORT).show();
                } else {
                    D = new inputDialog("Adjust price of sauna (by a %)", this, "Adjust",
                          new callback() {
                              @Override
                              public void callback() {
                                  if (inputDialogText1.length() == 0 || inputDialogText2.length() == 0) {
                                      Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show();
                                      return;
                                  }
                                  String code = inputDialogText1;
                                  double percent = Double.parseDouble(inputDialogText2);
                                  Sauna S = SaunaList.getSauna(code);
                                  if (SaunaList.getInstance().contains(S)) {
                                      double newPrice = S.getPrice() + S.getPrice() * percent / 100;
                                      S.setPrice(newPrice);
                                      Toast.makeText(context, "Price adjusted by " + percent + "%", Toast.LENGTH_SHORT).show();
                                  } else {
                                      Toast.makeText(context, "Sauna doesn't exist", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });
                    D.addInput(this, "Product Code: ", InputType.TYPE_CLASS_TEXT, 1);
                    D.addInput(this, "Percentage: %", InputType.TYPE_NUMBER_FLAG_SIGNED, 2);
                    D.show();
                }


                return true;

            case R.id.opt7: // List Saunas by a specific capacity
                listView = findViewById(R.id.opt1_listSauna);
                TextView finalListView = listView;
                D = new inputDialog("Show all saunas with a given capacity", this, "Show",
                      new callback() {
                          @Override
                          public void callback() {
                              int capacity = Integer.parseInt(inputDialogText1);
                              ArrayList<Sauna> filteredList = new ArrayList<>();
                              String textList = "";
                              for (Sauna S : SaunaList.getInstance()) {
                                  if (S.getCapacity() == capacity)
                                      textList += S.toPrintString() + "\n";
                              }
                              if (textList.length() > 0) {
                                  TextView title = findViewById(R.id.listTitle);
                                  title.setText("List of Saunas holding\nup to " + capacity + " persons");
                                  finalListView.setText(textList);
                              } else {
                                  Toast.makeText(context, "There are no saunas with the\n" +
                                        " given capacity of " + capacity + " persons", Toast.LENGTH_LONG).show();
                              }
                          }
                      });
                D.addInput(this, "Capacity", InputType.TYPE_CLASS_NUMBER, 1);
                D.show();

                return true;

            case R.id.opt8: // Display Median Price
                if (SaunaList.getInstance().size() == 0) {
                    Toast.makeText(context, "There is no data.", Toast.LENGTH_SHORT).show();
                } else {
                    title = findViewById(R.id.listTitle);
                    title.setText("Median Price of Saunas");
                    saunaListView = findViewById(R.id.opt1_listSauna);
                    saunaListView.setTextSize(50);
                    saunaListView.setText("$" + SaunaList.getMedianPrice());
                }

                return true;


            case R.id.opt9: // Save Data to File
                if (SaunaList.getInstance().size() == 0) {
                    Toast.makeText(context, "There is no data to save.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
                        SaunaList.saveToFile(path);
                        Toast.makeText(context, "Saved sauna list to file to:\n" + path, Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        Toast.makeText(context, "Error saving list to file.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private class inputDialog {
        AlertDialog.Builder builder;
        LinearLayout layout;
        EditText input1;
        EditText input2;

        inputDialog(String title, Context context, String OK, callback cb) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            input1 = new EditText(context);
            input2 = new EditText(context);

            builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputDialogText1 = input1.getText().toString();
                    inputDialogText2 = input2.getText().toString();
                    cb.callback();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        void addInput(Context context, String label, int type, int n) {
            EditText input = n == 1 ? input1 : input2;
            LinearLayout layout1 = new LinearLayout(context);
            final TextView labelV = new TextView(context);
            labelV.setText(label);
            layout1.setGravity(Gravity.LEFT);
            layout1.setPaddingRelative(50, 0, 0, 0);
            layout1.addView(labelV);
            layout1.addView(input);
            input.setInputType(type);
            input.setMinimumWidth(200);
            input.setWidth(400);
            layout.addView(layout1);
        }

        void show() {
            builder.setView(layout);
            builder.show();
        }

    }

    private interface callback {
        public void callback();
    }

}