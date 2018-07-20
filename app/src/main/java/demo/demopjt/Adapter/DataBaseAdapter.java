package demo.demopjt.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import demo.demopjt.Database.Contact;
import demo.demopjt.ModelClass.CategoryList;
import demo.demopjt.R;
import demo.demopjt.listeners.OnDownloadPdf;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.MyViewHolder> {

    private List<Contact> moviesList;
    Context mcontext;
    private OnDownloadPdf onDownloadPdf;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price,catid;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt1);
            price = (TextView) view.findViewById(R.id.txt2);
            catid = (TextView) view.findViewById(R.id.txt3);

        }
    }


    public DataBaseAdapter(Context mcontext, List<Contact> moviesList, OnDownloadPdf onDownloadPdf) {
        this.mcontext = mcontext;
        this.moviesList = moviesList;
        this.onDownloadPdf = onDownloadPdf;

    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowdatabase, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact movie = moviesList.get(position);
        holder.title.setText(""+movie.getName());
        holder.price.setText(""+movie.getPrice());
        holder.catid.setText(""+movie.getCatid());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDownloadPdf.onDownloadPdf("ddd");

            }
        });
    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}