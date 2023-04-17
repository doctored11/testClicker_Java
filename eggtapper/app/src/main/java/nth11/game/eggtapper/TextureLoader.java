package nth11.game.eggtapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class TextureLoader {
    public static void loadTexture(Context context, int resourceId, ViewModel.OnBitmapReadyListener listener) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        BitmapEditor.changeWhiteToBlueAsync(bitmap, new BitmapEditor.OnCompleteListener() {
            @Override
            public void onComplete(Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);
//                imageView.setScaleX(1);
//                imageView.setScaleY(1);
                listener.onBitmapReady(bitmap);
            }
        });
    }

}
