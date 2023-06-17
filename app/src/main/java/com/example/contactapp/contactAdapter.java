package com.example.contactapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*You can populate an AdapterView such as ListView or GridView by binding
 the AdapterView instance to an Adapter, which retrieves data from
 an external source and creates a
 View that represents each data entry*/
public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ContactViewHolder>{
    private Context context;


    private String phoneNumber;

    private ArrayList<ModelContact> contactlist;
    private DbHelper dbHelper;


    public contactAdapter(Context context, ArrayList<ModelContact> contactlist) {
        this.context = context;
        this.contactlist = contactlist;

        dbHelper=new DbHelper(context);

    }

    @NonNull
    @Override

    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_element_item, parent, false);
        applyZoomInAnimation(view);
        return new ContactViewHolder(view);
    }

    private void applyZoomInAnimation(View view) {
        view.setTranslationY(800); // Initial translation of the view outside the screen bounds
        view.animate()
                .translationY(0) // Animate the view to its original position (0 translation)
                .setDuration(900) // Animation duration in milliseconds
                .setInterpolator(new DecelerateInterpolator()) // Optional: Set an interpolator for the animation
                .start();
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        ModelContact ModelContact =contactlist.get(position);
        String id= ModelContact.getId();
        String name = ModelContact.getName();
        String email= ModelContact.getEmail();
        String phone= ModelContact.getPhone();
        String addedTime = ModelContact.getAddedTime();
        String updatedTime = ModelContact.getUpdatedTime();

        holder.contact_name.setText(name);
        //handle click listener
        holder.contact_number_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.contact_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,Activity_Contact_details.class);
                intent.putExtra("contactId",id);
                context.startActivity(intent);

            }
        });

        holder.contactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteContact(id);

                //refresh data by calling resume state of MainActivity

                ((MainActivity)context).onResume();
            }
        });

        holder.contact_number_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String phoneNumber = contactlist.get(position).getPhone();

                    if (!phoneNumber.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        ((Activity) context).startActivityForResult(intent, 1);
                    } else {
                        Toast.makeText(context, "Phone number not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.contactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent to move AddEditActivity to update data
                Intent intent = new Intent(context,AddEditContact.class);
                //pass the value of current position
                intent.putExtra("ID",id);
                intent.putExtra("NAME",name);
                intent.putExtra("PHONE",phone);
                intent.putExtra("EMAIL",email);

                intent.putExtra("ADDEDTIME",addedTime);
                intent.putExtra("UPDATEDTIME",updatedTime);


                // pass a boolean data to define it is for edit purpose
                intent.putExtra("isEditMode",true);

                //start intent
                context.startActivity(intent);


            }
        });






    }
    // handle editBtn click

    //handle item click listener




    @Override
    public int getItemCount() {
        return contactlist.size();
    }



    class ContactViewHolder extends RecyclerView.ViewHolder
    {

       // RelativeLayout relativeLayout;

        ImageView contact_image, contact_number_dial,contactDelete,contactEdit;
        TextView contact_name;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_image=itemView.findViewById(R.id.contact_image);
            contact_number_dial=itemView.findViewById(R.id.contact_number_dial);
            contact_name=itemView.findViewById(R.id.contact_name);
            contactDelete=itemView.findViewById(R.id.contactDelete);
            contact_number_dial=itemView.findViewById(R.id.contact_number_dial);
            contactEdit=itemView.findViewById(R.id.contactEdit);
        }
    }
}
