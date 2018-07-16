package demo.demopjt.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import demo.demopjt.Adapter.ProductAdapter;
import demo.demopjt.BitmapLoader;
import demo.demopjt.BuildConfig;
import demo.demopjt.ModelClass.CategoryMain;
import demo.demopjt.ModelClass.SimpleMessageStatusResponse;
import demo.demopjt.R;
import demo.demopjt.UIUtil;
import demo.demopjt.retrofit.APIClient;
import demo.demopjt.retrofit.APIInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UploadImage extends AppCompatActivity {

    APIInterface apiInterface;
    Button uploadBtn;
    ImageView ivImage;
    private String selectedImagePath="";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RequestPermissionCode = 7;
    RelativeLayout rlUpload;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimage);


        apiInterface = APIClient.getClient().create(APIInterface.class);


        init();


    }

    private void init() {

        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        rlUpload = (RelativeLayout) findViewById(R.id.rlUpload);

        rlUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckingPermissionIsEnabledOrNot())
                {
                    dispatchTakePictureIntent() ;
                }


                else {


                    RequestMultiplePermission();

                }


            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(UploadImage.this);
                progressBar.setCancelable(true);
                progressBar.setMessage("Loading ...");
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                uploadFile();
            }
        });

    }


    private void dispatchTakePictureIntent() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = null;
                try {
                    photoURI = FileProvider.getUriForFile(UploadImage.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            createImageFile());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        selectedImagePath = "file:" + image.getAbsolutePath();
        return image;
    }
    private boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // This device has a camera
            return true;
        } else {
            // no camera on this device
            Toast.makeText(context, "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(selectedImagePath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                //ivpic.setImageBitmap(BitmapFactory.decodeStream(ims));

                setFullImageFromFilePath((ImageView)this.findViewById(R.id.ivImage), imageUri.getPath());

            } catch (FileNotFoundException e) {
                return;
            }

            // ScanFile so it will be appeared on Gallery

        }
    }
    private Bitmap setFullImageFromFilePath(ImageView imageView, String imageSelectedPath) {
        selectedImagePath=imageSelectedPath;
        Bitmap bitmap = BitmapLoader.downSampleBitmap(selectedImagePath, imageView);
        int imageAngle = UIUtil.getImageAngle(selectedImagePath);
        Bitmap rotateBitMap = UIUtil.rotateImage(bitmap, imageAngle);
        imageView.setImageBitmap(rotateBitMap);

        return bitmap;
    }
    //Permission function starts from here
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(UploadImage.this, new String[]
                {
                        Manifest.permission.CAMERA,
                        WRITE_EXTERNAL_STORAGE
                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && WritePermission ) {
                        dispatchTakePictureIntent() ;

                    }
                    else {
                        Toast.makeText(UploadImage.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    // Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int sPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                sPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }

    private void uploadFile() {


//        File file = new File(selectedImagePath);
//
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("sImage", file.getName(), reqFile);
//
//

        File file = new File(selectedImagePath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("sImage", file.getName(), requestFile);



        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "3");

        Call<SimpleMessageStatusResponse> call = apiInterface.addPhoto(name,body);
        call.enqueue(new Callback<SimpleMessageStatusResponse>() {
            @Override
            public void onResponse(Call<SimpleMessageStatusResponse> call, Response<SimpleMessageStatusResponse> response) {
                progressBar.cancel();


                Log.e("TAG", response.code() + "");

                if (response.isSuccessful()) {

                    SimpleMessageStatusResponse apiresponse = response.body();


                    if (apiresponse.getStatus() == 1) {


                        Toast.makeText(UploadImage.this,"sucess",Toast.LENGTH_SHORT).show();

                    }
                }

                Log.e("ss", "ss");


            }

            @Override
            public void onFailure(Call<SimpleMessageStatusResponse> call, Throwable t) {
                call.cancel();
                progressBar.cancel();
                Toast.makeText(UploadImage.this, "error", Toast.LENGTH_SHORT).show();

            }
        });






    }


}
