package jovan0042.monuments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileHandler {

    public void saveToInternalStorage(Bitmap bitmap, String name) throws IOException {
        name += ".jpg";
        File file = new File(Environment.getExternalStorageDirectory(), name);
        if (!file.exists()) {
            file.createNewFile();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Bitmap getImage(String name) {
        name += ".jpg";


        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
            if (file.exists()) {
                return BitmapFactory.decodeFile(file.getAbsolutePath());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
