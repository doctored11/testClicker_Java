package nth11.game.eggtapper.model.resourcehandlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import nth11.game.eggtapper.viewModel.ViewModel;

public class TextureLoader {
    public static void loadTexture(Context context, ViewModel.OnBitmapReadyListener listener) {
        try {


            Bitmap resultBitmap = getRndSkin(context);
            BitmapEditor.changeWhiteToRandomAsync(resultBitmap, new BitmapEditor.OnCompleteListener() {
                @Override
                public void onComplete(Bitmap bitmap) {
                    // Вызываем колбэк, передавая готовый битмап
                    listener.onBitmapReady(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Bitmap overlayBitmaps(Bitmap bitmap1, Bitmap bitmap2) {//!gh
        //Складываем 2 битмапа

        // создаем новый битмап
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());

        // Создаем канвас на  результирующем битмапе
        Canvas canvas = new Canvas(resultBitmap);
        // рисуем битмапы на канвасе
        canvas.drawBitmap(bitmap1, 0, 0, null);
        canvas.drawBitmap(bitmap2, 0, 0, null);

        return resultBitmap;
    }

    public static Bitmap getBitmap(Context context, String name, int count) throws IOException {
        Random random = new Random();
        int n = random.nextInt(count);
        InputStream inputStream = context.getAssets().open("animal/" + name + "/" + name + "_" + n + ".png");
        Log.i("!  ГЕНЕРАЦИЯ", "animal/" + name + "/" + name + "_" + n + ".png" + "____________!");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;

    }

    public static Bitmap getDefaultBeak(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/beak/beak_0.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getDefaultEye(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/eye/eye_0.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getDefaultHat(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/head/head_0.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getDefaultWing(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/wing/wing_0.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getDefaultDress(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/dress/dress_0.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getDefaultBody(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/body/body_0.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getDefaultDuck(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("animal/body/body_default.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static Bitmap getRndSkin(Context context) throws IOException {

        Bitmap bitmapBody, bitmapBeak, bitmapHat, bitmapWing, bitmapDress, bitmapEye;
        Bitmap buffer;
        Random random = new Random();
        int n = random.nextInt(101);
        if (n < 8) {

//

            bitmapBody = getDefaultDuck(context);
            Bitmap resultBitmap = bitmapBody;
            return resultBitmap;
        } else {



            bitmapBody = getBitmap(context, "body", 1);

            if( random.nextInt(101) > 90 ){
                bitmapDress = getBitmap(context, "dress", 8);
            }else {
                bitmapDress = getDefaultDress(context);
            }
            buffer = overlayBitmaps(bitmapBody, bitmapDress);

            if( random.nextInt(101) > 70 ){
                bitmapWing = getBitmap(context, "wing", 12);
            }else {
                bitmapWing = getDefaultWing(context);
            }
            buffer = overlayBitmaps(buffer, bitmapWing);

            if( random.nextInt(101) > 55 ){
                bitmapEye = getBitmap(context, "eye", 17);
            }else {
                bitmapEye = getDefaultEye(context);
            }
            buffer = overlayBitmaps(buffer, bitmapEye);

            if( random.nextInt(101) > 35 ){
                bitmapHat = getBitmap(context, "head", 33);
            }else {
                bitmapHat = getDefaultHat(context);
            }
            buffer = overlayBitmaps(buffer, bitmapHat);

            if( random.nextInt(101) > 40 ){
                bitmapBeak = getBitmap(context, "beak", 20);
            }else {
                bitmapBeak = getDefaultBeak(context);
            }
            buffer = overlayBitmaps(buffer, bitmapBeak);

            Bitmap resultBitmap = buffer;
            return resultBitmap;

        }



    }

}
