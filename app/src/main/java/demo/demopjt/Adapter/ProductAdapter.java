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
import demo.demopjt.ModelClass.CategoryList;
import demo.demopjt.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
 
    private List<CategoryList> moviesList;
    Context mcontext;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView iv;
 
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt1);
            iv = (ImageView) view.findViewById(R.id.iv);

        }
    }
 
 
    public ProductAdapter(Context mcontext,List<CategoryList> moviesList) {
        this.mcontext = mcontext;
        this.moviesList = moviesList;

    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryList movie = moviesList.get(position);
        holder.title.setText(""+movie.getSName());
        Glide.with(mcontext)
                .load("data:image/png;base64,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")
                .into(holder.iv);

    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}