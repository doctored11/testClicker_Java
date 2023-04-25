package nth11.game.eggtapper.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.viewModel.ViewModel;

public class TextureLoader {
    public static void loadTexture(Context context,  ViewModel.OnBitmapReadyListener listener) {
        int  resourceId = R.drawable.egg_stage_2;
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
