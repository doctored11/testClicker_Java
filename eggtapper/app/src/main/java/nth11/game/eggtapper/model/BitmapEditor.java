package nth11.game.eggtapper.model;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Random;

public class BitmapEditor {


    public static void changeWhiteToRandomAsync(Bitmap bitmap, OnCompleteListener listener) {
        new ChangeWhiteToRandomAsyncTask(listener).execute(bitmap);
    }

    private static class ChangeWhiteToRandomAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {
       private int randomColor = generateRandomColor();


        private OnCompleteListener listener;

        public ChangeWhiteToRandomAsyncTask(OnCompleteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap = bitmaps[0];
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Log.e("!!!!","перекрас лампас");
            for (int x = 0; x < bitmap.getWidth(); x++) {
                for (int y = 0; y < bitmap.getHeight(); y++) {
                    int pixel = bitmap.getPixel(x, y);

                    if (pixel == Color.WHITE) {
                        pixel = randomColor;
                    }

                    newBitmap.setPixel(x, y, pixel);
                }
            }

            return newBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            listener.onComplete(result);
        }
    }

    public interface OnCompleteListener {
        void onComplete(Bitmap bitmap);
    }
    public static int generateRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
