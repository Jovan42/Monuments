package jovan0042.monuments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

//Array adapter for listView in Login Activity
public class MonumentArrayAdapter extends ArrayAdapter<Monument>{
    Context context;
    List<Monument> monuments;

    public MonumentArrayAdapter(Context context, int resource, List<Monument> objects) {
        super(context, resource, objects);
        this.context = context;
        this.monuments = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Monument monument = monuments.get(position);
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        View view  = inflater.inflate(R.layout.monument_item, null);

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvType = (TextView) view.findViewById(R.id.tvType);
        TextView tvUser = (TextView) view.findViewById(R.id.tvUser);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        tvName.setText(monument.getName());
        tvType.setText(monument.getType());
        tvUser.setText(monument.getUser());

        ImageFileHandler imf = new ImageFileHandler();
        File file = new File(Environment.getExternalStorageDirectory(), monument.getName()+ ".jpg");
        if(file.exists()) {
            Bitmap bitmap = imf.getImage(monument.getName());
            imageView.setImageBitmap(bitmap);
        }

        return view;
    }
}
