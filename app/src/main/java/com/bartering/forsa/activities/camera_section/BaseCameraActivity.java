package com.bartering.forsa.activities.camera_section;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.widget.Filters;
import com.bartering.forsa.activities.camera_section.widget.SampleGLView;
import com.bartering.forsa.camera_utilities.CameraRecordListener;
import com.bartering.forsa.camera_utilities.CameraRecorder;
import com.bartering.forsa.camera_utilities.CameraRecorderBuilder;
import com.bartering.forsa.camera_utilities.LensFacing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sudamasayuki2 on 2018/07/02.
 */

public class BaseCameraActivity extends AppCompactActivity {

    protected CameraRecorder cameraRecorder;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    ClickListener clickListener;
    MediaData_HolderModel mediaData_holderModel;
    boolean isRecordingVideoRecorderStarted = false;
    ImageView doActiveCameraViewId, doActiveVideoViewId, imageFromGalleyImageViewId, btn_switch_camera;
    Button btn_record, btn_image_capture, stopVideo_image_capture;
    Chronometer videoChronometer;
    LinearLayout bottomMainLinLayoutId, upperLinLayoutId, captureedImageRectclerviewId, bottomRightViewLinLayouyId;
    LinearLayout.LayoutParams weightOptimizer;
    private SampleGLView sampleGLView;
    private String filepath;
    private TextView recordBtn;
    private AlertDialog filterDialog;
    private boolean toggleClick = false;

    public static void exportMp4ToGallery(Context context, String filePath) {
        final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + filePath)));
    }

    public static String getVideoFilePath() {
        return getAndroidMoviesFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "cameraRecorder.mp4";
    }

    public static File getAndroidMoviesFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    private static void exportPngToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static String getImageFilePath() {
        return getAndroidImageFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "cameraRecorder.png";
    }

    public static File getAndroidImageFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    protected void onCreateActivity(ClickListener clickListener) {
        //getSupportActionBar().hide();
        this.clickListener = clickListener;

        recordBtn = findViewById(R.id.btn_record);
        btn_image_capture = findViewById(R.id.btn_image_capture);

        try {
            upperLinLayoutId = findViewById(R.id.videoLinLayoutId);
            bottomMainLinLayoutId = findViewById(R.id.bottomMainLinLayoutId);
            captureedImageRectclerviewId = findViewById(R.id.captureedImageRectclerviewId);
            bottomRightViewLinLayouyId = findViewById(R.id.bottomRightViewLinLayouyId);

            videoChronometer = findViewById(R.id.videoChronometerId);
            doActiveCameraViewId = findViewById(R.id.doActiveCameraViewId);
            doActiveVideoViewId = findViewById(R.id.doActiveVideoViewId);
            stopVideo_image_capture = findViewById(R.id.stopVideo_image_capture);
            imageFromGalleyImageViewId = findViewById(R.id.imageFromGalleyImageViewId);
            btn_switch_camera = findViewById(R.id.btn_switch_camera);

            weightOptimizer = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


            doActiveCameraViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopVideo_image_capture.setVisibility(View.GONE);
                    btn_image_capture.setVisibility(View.VISIBLE);
                    recordBtn.setVisibility(View.GONE);
                    doActiveCameraViewId.setVisibility(View.GONE);
                    doActiveVideoViewId.setVisibility(View.VISIBLE);

                }
            });

            doActiveVideoViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopVideo_image_capture.setVisibility(View.GONE);
                    btn_image_capture.setVisibility(View.GONE);
                    recordBtn.setVisibility(View.VISIBLE);
                    doActiveCameraViewId.setVisibility(View.VISIBLE);
                    doActiveVideoViewId.setVisibility(View.GONE);
                }
            });

            stopVideo_image_capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weightOptimizer.weight = (float) 1.10;
                    upperLinLayoutId.setLayoutParams(weightOptimizer);

                    weightOptimizer.weight = (float) 0.90;
                    bottomMainLinLayoutId.setLayoutParams(weightOptimizer);
                    bottomRightViewLinLayouyId.setVisibility(View.VISIBLE);


                    stopVideo_image_capture.setVisibility(View.GONE);

                    recordBtn.setVisibility(View.VISIBLE);
                    imageFromGalleyImageViewId.setVisibility(View.VISIBLE);
                    captureedImageRectclerviewId.setVisibility(View.VISIBLE);

                    videoChronometer.setVisibility(View.GONE);
                    videoChronometer.stop();
                    isRecordingVideoRecorderStarted = false;
                    cameraRecorder.stop();
                    mediaData_holderModel = new MediaData_HolderModel("VIDEO", filepath, null, false);
                    clickListener.onClick(0, mediaData_holderModel, "VIDEO_DONE");
                }
            });
            recordBtn.setOnClickListener(v -> {
                weightOptimizer.weight = (float) 1.50;
                upperLinLayoutId.setLayoutParams(weightOptimizer);

                weightOptimizer.weight = (float) 0.50;
                bottomMainLinLayoutId.setLayoutParams(weightOptimizer);
                bottomRightViewLinLayouyId.setVisibility(View.GONE);

                stopVideo_image_capture.setVisibility(View.VISIBLE);

                recordBtn.setVisibility(View.GONE);
                imageFromGalleyImageViewId.setVisibility(View.GONE);
                captureedImageRectclerviewId.setVisibility(View.GONE);

                videoChronometer.setBase(SystemClock.elapsedRealtime());
                videoChronometer.setVisibility(View.VISIBLE);
                videoChronometer.start();
                isRecordingVideoRecorderStarted = true;
                filepath = getVideoFilePath();
                cameraRecorder.start(filepath);

            /*if (!isRecordingVideoRecorderStarted) {

                stopVideo_image_capture.setVisibility(View.VISIBLE);

                recordBtn.setVisibility(View.GONE);
                imageFromGalleyImageViewId.setVisibility(View.GONE);
                imageFromGalleyImageViewId.setVisibility(View.GONE);
                doActiveVideoViewId.setVisibility(View.GONE);
                doActiveCameraViewId.setVisibility(View.GONE);
                btn_switch_camera.setVisibility(View.GONE);

                videoChronometer.setBase(SystemClock.elapsedRealtime());
                videoChronometer.setVisibility(View.VISIBLE);
                videoChronometer.start();
                isRecordingVideoRecorderStarted = true;
                filepath = getVideoFilePath();
                cameraRecorder.start(filepath);
            } else {
                stopVideo_image_capture.setVisibility(View.GONE);

                recordBtn.setVisibility(View.VISIBLE);
                imageFromGalleyImageViewId.setVisibility(View.VISIBLE);
                imageFromGalleyImageViewId.setVisibility(View.VISIBLE);
                doActiveVideoViewId.setVisibility(View.VISIBLE);
                doActiveCameraViewId.setVisibility(View.VISIBLE);
                btn_switch_camera.setVisibility(View.VISIBLE);

                videoChronometer.setVisibility(View.GONE);
                videoChronometer.stop();
                isRecordingVideoRecorderStarted = false;
                cameraRecorder.stop();
                mediaData_holderModel = new MediaData_HolderModel("VIDEO", filepath, null, false);
                clickListener.onClick(0, mediaData_holderModel, "VIDEO_DONE");
            }
*/
            /*if (recordBtn.getText().equals(getString(R.string.app_record))) {
                filepath = getVideoFilePath();
                cameraRecorder.start(filepath);
                recordBtn.setText("Stop");

            } else {
                cameraRecorder.stop();
                recordBtn.setText(getString(R.string.app_record));
                mediaData_holderModel = new MediaData_HolderModel("VIDEO",filepath,null,false);
                clickListener.onClick(0,mediaData_holderModel,"VIDEO_DONE");
            }*/

            });

            findViewById(R.id.btn_flash).setOnClickListener(v -> {
                if (cameraRecorder != null && cameraRecorder.isFlashSupport()) {
                    cameraRecorder.switchFlashMode();
                    cameraRecorder.changeAutoFocus();
                }
            });

            btn_switch_camera.setOnClickListener(v -> {
                releaseCamera();
                if (lensFacing == LensFacing.BACK) {
                    lensFacing = LensFacing.FRONT;
                } else {
                    lensFacing = LensFacing.BACK;
                }
                toggleClick = true;
            });

            findViewById(R.id.btn_filter).setOnClickListener(v -> {
                if (filterDialog == null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Choose a filter");
                    builder.setOnDismissListener(dialog -> {
                        filterDialog = null;
                    });

                    final Filters[] filters = Filters.values();
                    CharSequence[] charList = new CharSequence[filters.length];
                    for (int i = 0, n = filters.length; i < n; i++) {
                        charList[i] = filters[i].name();
                    }
                    builder.setItems(charList, (dialog, item) -> {
                        changeFilter(filters[item]);
                    });
                    filterDialog = builder.show();
                } else {
                    filterDialog.dismiss();
                }

            });

            btn_image_capture.setOnClickListener(v -> {
                captureBitmap(bitmap -> {
                    new Handler().post(() -> {
                        String imagePath = getImageFilePath();
                        saveAsPngImage(bitmap, imagePath);
                        exportPngToGallery(getApplicationContext(), imagePath);
                        mediaData_holderModel = new MediaData_HolderModel("IMAGE", imagePath, bitmap, false);
                        clickListener.onClick(0, mediaData_holderModel, "IMAGE_DONE");
                    });
                });
            });


        } catch (Exception EE) {

        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (cameraRecorder != null) {
            cameraRecorder.stop();
            cameraRecorder.release();
            cameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.wrap_view)).removeView(sampleGLView);
            sampleGLView = null;
        }
    }

    private void setUpCameraView() {
        runOnUiThread(() -> {
            FrameLayout frameLayout = findViewById(R.id.wrap_view);
            frameLayout.removeAllViews();
            sampleGLView = null;
            sampleGLView = new SampleGLView(getApplicationContext());
            sampleGLView.setTouchListener((event, width, height) -> {
                if (cameraRecorder == null) return;
                cameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
            });
            frameLayout.addView(sampleGLView);
        });
    }

    private void setUpCamera() {
        setUpCameraView();

        cameraRecorder = new CameraRecorderBuilder(this, sampleGLView)
                //.recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
                        runOnUiThread(() -> {
                            findViewById(R.id.btn_flash).setEnabled(flashSupport);
                        });
                    }

                    @Override
                    public void onRecordComplete() {
                        exportMp4ToGallery(getApplicationContext(), filepath);
                    }

                    @Override
                    public void onRecordStart() {

                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("CameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {
                        if (toggleClick) {
                            runOnUiThread(() -> {
                                setUpCamera();
                            });
                        }
                        toggleClick = false;
                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .build();


    }

    private void changeFilter(Filters filters) {
        cameraRecorder.setFilter(Filters.getFilterInstance(filters, getApplicationContext()));
    }

    private void captureBitmap(final BitmapReadyCallbacks bitmapReadyCallbacks) {
        sampleGLView.queueEvent(() -> {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
            Bitmap snapshotBitmap = createBitmapFromGLSurface(sampleGLView.getMeasuredWidth(), sampleGLView.getMeasuredHeight(), gl);

            runOnUiThread(() -> {
                bitmapReadyCallbacks.onBitmapReady(snapshotBitmap);
            });
        });
    }

    private Bitmap createBitmapFromGLSurface(int w, int h, GL10 gl) {

        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2, texturePixel, blue, red, pixel;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    texturePixel = bitmapBuffer[offset1 + j];
                    blue = (texturePixel >> 16) & 0xff;
                    red = (texturePixel << 16) & 0x00ff0000;
                    pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("CreateBitmap", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }

    public void saveAsPngImage(Bitmap bitmap, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private interface BitmapReadyCallbacks {
        void onBitmapReady(Bitmap bitmap);
    }

}
