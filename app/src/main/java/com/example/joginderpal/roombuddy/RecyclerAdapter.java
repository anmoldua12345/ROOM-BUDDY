package com.example.joginderpal.roombuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joginderpal on 13-01-2017.
 */
public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context ctx;
    List<String> li1;
    List<String> li2;
    public RecyclerAdapter(Context ctx, List<String> li1, List<String> li2) {
        this.li1=li1;
        this.ctx=ctx;
        this.li2=li2;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tx;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            tx= (TextView) itemView.findViewById(R.id.text3);
            image= (ImageView) itemView.findViewById(R.id.imageview);

        }


    }



    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        RecyclerView.ViewHolder v= new ViewHolder(view);

        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

         holder.tx.setText(li1.get(position));
         holder.image.setImageBitmap(getBitmap(li2.get(position)));

    }

    @Override
    public int getItemCount() {
        return li1.size();
    }
    public Bitmap getBitmap(String s){
        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}