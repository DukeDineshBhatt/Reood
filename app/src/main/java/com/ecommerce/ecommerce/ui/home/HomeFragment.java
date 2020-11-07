package com.ecommerce.ecommerce.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.ecommerce.Activities.SearchActivity;

import com.ecommerce.ecommerce.Adapters.BannerAdapter;
import com.ecommerce.ecommerce.Adapters.CuisineAdapter;
import com.ecommerce.ecommerce.Adapters.ThumbnailAdapter;
import com.ecommerce.ecommerce.AllApiIneterface;
import com.ecommerce.ecommerce.Bean;
import com.ecommerce.ecommerce.CategoryPOJO.CategoryBean;
import com.ecommerce.ecommerce.CategoryPOJO.Datum;
import com.ecommerce.ecommerce.DataStructure.Cuisine;
import com.ecommerce.ecommerce.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {

    RecyclerView banner, cuisine, thumbnails;
    TextView searchbox;
    Button pickup,delivery;
    CategoryAdapter adapter;
    List<Datum> list;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        banner = root.findViewById(R.id.banner_recycler);
        cuisine = root.findViewById(R.id.cuisine_recycler);
        thumbnails = root.findViewById(R.id.thumbnail_recycler);
        searchbox = root.findViewById(R.id.search_box_main);
        pickup = root.findViewById(R.id.pickup);
        delivery = root.findViewById(R.id.delivery);
        progressBar = root.findViewById(R.id.progressbar);

        searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(
                        new Intent(
                                getActivity(),
                                SearchActivity.class
                        )
                );
            }
        });

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new HomePickUpFragment());
                fragmentTransaction.commit();

            }
        });

        list = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(), list);
        cuisine.setAdapter(adapter);
        cuisine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<String> bannerList = new ArrayList<>();
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        BannerAdapter bannerAdapter = new BannerAdapter(getActivity(), bannerList);
        banner.setAdapter(bannerAdapter);

       /* ArrayList<Cuisine> cuisineList = new ArrayList<>();
        cuisineList.add(new Cuisine("Indian", getResources().getDrawable(R.drawable.ic_indian)));
        cuisineList.add(new Cuisine("Chinese", getResources().getDrawable(R.drawable.ic_chinese)));
        cuisineList.add(new Cuisine("Continental", getResources().getDrawable(R.drawable.ic_continental)));
        cuisineList.add(new Cuisine("European", getResources().getDrawable(R.drawable.ic_european)));
        cuisineList.add(new Cuisine("Italian", getResources().getDrawable(R.drawable.ic_italian)));
        cuisineList.add(new Cuisine("Mexican", getResources().getDrawable(R.drawable.ic_mexican)));
        CuisineAdapter cuisineAdapter = new CuisineAdapter(getActivity(), cuisineList);
        cuisine.setAdapter(cuisineAdapter);*/

        ArrayList<String> dishList = new ArrayList<>();
        dishList.add("Noodles");
        dishList.add("Veg Soup");
        dishList.add("Grilled Chicken");
        dishList.add("Fried Rice");
        dishList.add("Chicken Keema");
        dishList.add("Chicken Manchurian");
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(getActivity(), dishList);
        thumbnails.setAdapter(thumbnailAdapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.HEADERS);
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<CategoryBean> call = cr.category();
        call.enqueue(new Callback<CategoryBean>() {
            @Override
            public void onResponse(Call<CategoryBean> call, Response<CategoryBean> response) {

                boolean a =true;
                if (response.body().getStatus().equals(a)) {

                    adapter.setData(response.body().getData());
                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CategoryBean> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();

        public CategoryAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cuisine_thumbnail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getCategoryImageUrl(), holder.image, options);

            holder.name.setText(item.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* Intent intent = new Intent(context, SubCat.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("title", item.getName());
                    intent.putExtra("image", item.getImage());
                    context.startActivity(intent);*/

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView name;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.cuisine_name);
                image = itemView.findViewById(R.id.cuisine_icon);


            }
        }
    }
}