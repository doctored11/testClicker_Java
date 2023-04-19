package nth11.game.eggtapper.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nth11.game.eggtapper.viewModel.ViewModel;

public class TextureLoader {
    public static void loadTexture(Context context, int resourceId, ViewModel.OnBitmapReadyListener listener) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        BitmapEditor.changeWhiteToRandomAsync(bitmap, new BitmapEditor.OnCompleteListener() {
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
