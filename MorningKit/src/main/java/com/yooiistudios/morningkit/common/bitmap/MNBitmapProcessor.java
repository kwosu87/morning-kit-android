package com.yooiistudios.morningkit.common.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.testflightapp.lib.TestFlight;
import com.yooiistudios.morningkit.common.file.ExternalStorageManager;
import com.yooiistudios.morningkit.common.log.MNLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by StevenKim in MorningKit from Yooii Studios Co., LTD. on 2014. 2. 19.
 *
 * MNBitmapProcessor
 *  플리커에서 사용될 비트맵 처리기 - 그레이스케일, 라운딩, 크롭
 */
public class MNBitmapProcessor {
    private static final String TAG = "MNBitmapProcessor";

    private MNBitmapProcessor() { throw new AssertionError("You MUST not create this class!"); }

    public static Bitmap getCroppedBitmap(Bitmap bitmap, int targetWidth, int targetHeight) {
        if (bitmap != null) {

            Bitmap croppedBitmap;
            double frameRatio = (double) targetWidth / (double) targetHeight;

            if (bitmap.getWidth() >= bitmap.getHeight()) {
                // 이미지의 가로가 세로보다 같거나 김

                // frame.width : bitmap.width (a)와 frame.height : bitmap.height (b)를 비교
                double widthRatio;
                if (targetWidth > bitmap.getWidth()) {
                    widthRatio = (double) bitmap.getWidth() / (double) targetWidth;
                } else {
                    widthRatio = (double) targetWidth / (double) bitmap.getWidth();
                }

                double heightRatio;
                if (targetHeight > bitmap.getHeight()) {
                    heightRatio = (double) bitmap.getHeight() / (double) targetHeight;
                } else {
                    heightRatio = (double) targetHeight / (double) bitmap.getHeight();
                }

                // (a)와 (b) 중 작은 쪽으로 이미지를 줄인다
                if (widthRatio < heightRatio) {
                    // (a)가 작다면 bitmap의 height는 frame.height, width는 frame.height * ratio
                    Point newBitmapSize = new Point((int) (bitmap.getHeight() * frameRatio), bitmap.getHeight());

                    // 자를 위치는 bitmap.width/2 - frame.width/2 에서 frame.width 만큼 자름
                    if (bitmap.getWidth() / 2 - newBitmapSize.x / 2 <= 0) {
                        MNLog.e(TAG, "x <= 0");
                        MNLog.e(TAG, "bitmap.getWidth(): " + bitmap.getWidth());
                        MNLog.e(TAG, "bitmap.getHeight(): " + bitmap.getHeight());
                        MNLog.e(TAG, "newBitmapSize.x: " + newBitmapSize.x);
                        MNLog.e(TAG, "newBitmapSize.y: " + newBitmapSize.y);

                        TestFlight.log("x <= 0\n"
                                + "bitmap.getWidth(): " + bitmap.getWidth() + "\n"
                                + "bitmap.getHeight(): " + bitmap.getHeight() + "\n"
                                + "newBitmapSize.x: " + newBitmapSize.x + "\n"
                                + "newBitmapSize.y: " + newBitmapSize.y + "\n"
                        );
                    }
                    croppedBitmap = Bitmap.createBitmap(bitmap,
                            bitmap.getWidth() / 2 - newBitmapSize.x / 2, 0,
                            newBitmapSize.x, newBitmapSize.y);
                } else {
                    // (b)가 작다면 bitmap의 width는 frame.width, height는 frame.width / ratio
                    Point newBitmapSize = new Point(bitmap.getWidth(), (int) (bitmap.getWidth() / frameRatio));

                    // 자를 위치는 Image.height/2 - frame.height/2 에서 frame.height 만큼 자름
                    croppedBitmap = Bitmap.createBitmap(bitmap,
                            0, bitmap.getHeight() / 2 - newBitmapSize.y / 2,
                            newBitmapSize.x, newBitmapSize.y);
                }
            } else {
                // 이미지의 가로가 세로보다 짧음

                // 이미지 조절. 새 이미지의 width는 frame의 width로, 이미지와 frame width의 비율만큼
                // 이미지 height를 조절해서 이미지를 resize한다.
                Point newBitmapSize = new Point(bitmap.getWidth(), (int) (bitmap.getWidth() / frameRatio));

                // 위에서 15% 아래에서부터 자름(인물 사진이라 가정)
                double offset15PercentFromTop = bitmap.getHeight() * 0.15;
                croppedBitmap = Bitmap.createBitmap(bitmap, 0, (int) offset15PercentFromTop,
                        newBitmapSize.x, newBitmapSize.y);
            }
            return croppedBitmap;
        }
        return null;
    }

    // 크롭 이후 패널 크기에 맞게 축소 & 가공을 진행
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int targetWidth, int targetHeight,
                                                boolean isGrayScale, int radius) {
        if(bitmap != null) {
            // 프레임에 맞게 비트맵 scaling
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);

            Bitmap outputBitmap = Bitmap.createBitmap(scaledBitmap.getWidth(), scaledBitmap.getHeight(), Bitmap.Config.ARGB_8888);

            // outputBitmap 에 캔버스를 생성에 scaleBitmap의 가공 내용을 draw
            Canvas canvas = new Canvas(outputBitmap);

            final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            final Rect rect = new Rect(0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());
            final RectF rectF = new RectF(rect);

            // gray scale
            if (isGrayScale) {
                ColorMatrix cm = new ColorMatrix();
                cm.setSaturation(0);
                ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
                paint.setColorFilter(f);
            }

            // round
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, radius, radius, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(scaledBitmap, rect, rect, paint);
            return outputBitmap;
        }
        return null;
    }

    public static Bitmap getGrayScaledBitmap(Bitmap originalBitmap) {
        if(originalBitmap != null) {
            Bitmap grayScaledBitmap = Bitmap.createBitmap(originalBitmap.getWidth(),
                    originalBitmap.getHeight(), Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(grayScaledBitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            paint.setColorFilter(f);
            canvas.drawBitmap(originalBitmap, 0, 0, paint);
            return grayScaledBitmap;
        }
        return null;
    }

    // 비트맵을 JSON으로 전달. 속도가 느려서 취소함
//    public static String getStringFromBitmap(Bitmap bitmap) {
//        /*
//        * This functions converts Bitmap picture to a string which can be
//        * JSONified.
//        */
//        final int COMPRESSION_QUALITY = 100;
//        String encodedImage;
//        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
//                byteArrayBitmapStream);
//        byte[] b = byteArrayBitmapStream.toByteArray();
//        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//        return encodedImage;
//    }
//
//    public static Bitmap getBitmapFromString(String jsonString) {
//        /*
//        * This Function converts the String back to Bitmap
//        */
//        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//    }

    /**
     * 비트맵을 로컬에 저장
     */
    public static String saveBitmapToDirectory(Context context, Bitmap image,
                                               String fileName, String directory) throws IOException {
        // Create an image file name - 동현 코드 사용해서 래핑
        File storageDir = ExternalStorageManager.createExternalDirectory(context, directory);

        // Save a file
        String imagePath = storageDir + "/" + fileName + ".jpg";

        FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
        image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

        // 이미지 저장
        fileOutputStream.flush();
        fileOutputStream.close();

        return imagePath;
    }

    public static Bitmap loadBitmapFromDirectory(Context context, String fileName, String directory) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        option.inPurgeable = true;
        option.inDither = true;

        File storageDir = ExternalStorageManager.createExternalDirectory(context, directory);
        return BitmapFactory.decodeFile(storageDir + "/" + fileName + ".jpg", option);
    }

    /*
    // 라운딩과 통합
    public static Bitmap getGrayScaledBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            int width, height;
            height = bitmap.getHeight();
            width = bitmap.getWidth();

            Bitmap grayScaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(grayScaledBitmap);
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            paint.setColorFilter(f);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return grayScaledBitmap;
        }
        return null;
    }
    */
}
