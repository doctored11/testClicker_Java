package nth11.game.eggtapper;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;

public class BitmapEditor {

    public static void changeWhiteToBlueAsync(Bitmap bitmap, OnCompleteListener listener) {
        new ChangeWhiteToBlueAsyncTask(listener).execute(bitmap);
    }

    private static class ChangeWhiteToBlueAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private OnCompleteListener listener;

        public ChangeWhiteToBlueAsyncTask(OnCompleteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap = bitmaps[0];
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

            for (int x = 0; x < bitmap.getWidth(); x++) {
                for (int y = 0; y < bitmap.getHeight(); y++) {
                    int pixel = bitmap.getPixel(x, y);

                    if (pixel == Color.WHITE) {
                        pixel = Color.BLUE;
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
}
