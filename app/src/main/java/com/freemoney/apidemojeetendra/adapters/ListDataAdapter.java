package com.freemoney.apidemojeetendra.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.freemoney.apidemojeetendra.EmployeeDetailsActivity;
import com.freemoney.apidemojeetendra.R;
import com.freemoney.apidemojeetendra.model.Employeegetset;
import com.freemoney.apidemojeetendra.utility.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {

    Context ctx;
    String gender;
    ArrayList<Employeegetset> arrayList;
    DeleteupdateCommunicate mydeleteinterface;

    public ListDataAdapter(Context ctx, ArrayList<Employeegetset> arrayList) {

        this.ctx = ctx;
        this.arrayList = arrayList;

    }

    public interface DeleteupdateCommunicate {
        public void ondelete(String s);

        // public void onupdate(String name,String emailid,String mobileno,String address,String location,String gender,String age,String empid);
        public void onupdate(String data);

    }

    @Override
    public ListDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_user, null);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ListDataAdapter.ViewHolder holder, final int position) {
        //arrayList = response.body().getUserRoles();
        final Employeegetset employeegetset = arrayList.get(position);
        // EmployeeModel product = data.get(position);
        holder.tv_name.setText((CharSequence) employeegetset.getEmpname());
        holder.tv_mobile.setText(employeegetset.getEmpmobile());

        holder.tv_email.setText(employeegetset.getEmpemail());
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Title...");

                final EditText fullname = (EditText) dialog.findViewById(R.id.etname);
                final EditText txemail = (EditText) dialog.findViewById(R.id.etemail);
                final EditText txnumber = (EditText) dialog.findViewById(R.id.etNumber);
                final EditText txtaddress = (EditText) dialog.findViewById(R.id.etaddress);
                final EditText txtage = (EditText) dialog.findViewById(R.id.etage);
                Button Add = (Button) dialog.findViewById(R.id.save);

                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg);
                RadioButton rbmale = (RadioButton) dialog.findViewById(R.id.rbmale);
                RadioButton rbfemale = (RadioButton) dialog.findViewById(R.id.rbfemale);
                if (arrayList.get(position).getEmpgender().equalsIgnoreCase("Male")) {
                    rbmale.setChecked(true);
                    gender = "male";
                    rbfemale.setChecked(false);
                } else if (arrayList.get(position).getEmpgender().equalsIgnoreCase("female")) {
                    rbfemale.setChecked(true);
                    gender = "female";
                    rbmale.setChecked(false);
                }
                fullname.setText(arrayList.get(position).getEmpname());
                txemail.setText(arrayList.get(position).getEmpemail());
                txnumber.setText(arrayList.get(position).getEmpmobile());
                txtaddress.setText(arrayList.get(position).getEmpaddress());
                txtage.setText(arrayList.get(position).getEmpage());
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(checkedId);
                        if (null != rb && checkedId > -1) {
                            gender = "male" + rb.getText().toString();
                            //  Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!Validation.isValid(Validation.BLANK_CHECK, fullname.getText().toString())) {
                            Toast.makeText(ctx, "Please enter name", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txemail.getText().toString())) {
                            Toast.makeText(ctx, "Please enter email", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.EMAIL, txemail.getText().toString())) {
                            Toast.makeText(ctx, "Please enter valid email", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txnumber.getText().toString())) {
                            Toast.makeText(ctx, "Please enter Mobile Number", Toast.LENGTH_LONG).show();
                        } else if (!Validation.isValid(Validation.MOBILE, txnumber.getText().toString())) {
                            Toast.makeText(ctx, "Please enter valid mobile number", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txtaddress.getText().toString())) {
                            Toast.makeText(ctx, "Please enter address", Toast.LENGTH_LONG).show();

                        } else if (!Validation.isValid(Validation.BLANK_CHECK, txtage.getText().toString())) {
                            Toast.makeText(ctx, "Please enter age", Toast.LENGTH_LONG).show();

                        } else if (gender == null && gender.equals("")) {
                            Toast.makeText(ctx, "Please select gender", Toast.LENGTH_LONG).show();

                        } else {
                            JSONObject jo = new JSONObject();
                            try {
                                jo.put("action", "Update_Emp");
                                jo.put("Name", fullname.getText().toString());
                                jo.put("Email_id", txemail.getText().toString());
                                jo.put("Mobile_no", txnumber.getText().toString());
                                jo.put("Address", txtaddress.getText().toString());
                                jo.put("Location", arrayList.get(position).getEmplocation());
                                jo.put("Gender", gender);
                                jo.put("Age", txtage.getText().toString());
                                jo.put("Emp_id", arrayList.get(position).getEmpid());

                                mydeleteinterface = (DeleteupdateCommunicate) ctx;
                                mydeleteinterface.onupdate(jo.toString());
                                dialog.dismiss();


                            } catch (JSONException e) {

                            }

                        }

                    }
                });
                // if button is clicked, close the custom dialog

                dialog.show();

            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ctx,,Toast.LENGTH_SHORT).show();
                mydeleteinterface = (DeleteupdateCommunicate) ctx;
                mydeleteinterface.ondelete(arrayList.get(position).getEmpid());
            }
        });
        holder.llcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, EmployeeDetailsActivity.class);
                intent.putExtra("Name", employeegetset.getEmpname());
                intent.putExtra("Email", employeegetset.getEmpemail());
                intent.putExtra("Location", employeegetset.getEmplocation());
                intent.putExtra("Phone", employeegetset.getEmpmobile());
                intent.putExtra("Address", employeegetset.getEmpaddress());
                ctx.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_email, tv_mobile;


        ImageView btndelete, btnupdate;
        LinearLayout llcard;

        public ViewHolder(View itemView) {
            super(itemView);
            llcard = itemView.findViewById(R.id.llcard);
            tv_name = itemView.findViewById(R.id.tvname);
            btndelete = itemView.findViewById(R.id.btndelete);
            tv_email = itemView.findViewById(R.id.tvemail);
            tv_mobile = itemView.findViewById(R.id.tvmobile);
            btnupdate = itemView.findViewById(R.id.btnupdate);
        }
    }
}
