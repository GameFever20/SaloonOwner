package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import utils.FireBaseHandler;
import utils.PendingSaloonRequest;
import utils.Saloon;

public class SaloonImageActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{



    private static final int REQUEST_PROFILE_IMAGE = 20;
    private static final int REQUEST_EXTRA_IMAGE1 = 10;
    private static final int REQUEST_EXTRA_IMAGE2 = 11;
    private static final int REQUEST_EXTRA_IMAGE3 = 12;
    private static final int REQUEST_EXTRA_IMAGE4 = 13;
    private static final int REQUEST_EXTRA_IMAGE5 = 14;



    Uri profileImageUri, image1Uri, image2Uri, image3Uri ,image4Uri,image5Uri ;
    String saloonUID = LoginActivity.saloonUID;
    
    int statusProfileUpload ,statusImage1Upload ,statusImage2Upload , statusImage3Upload , statusImage4Upload,statusImage5Upload;
    private final int RC_CAMERA_AND_LOCATION=20;


    ProgressDialog progressDialog ;

    Intent intent;
     Saloon saloon = MainActivity.SALOON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uploadImageToFireBase();
            }
        });

        progressDialog =new ProgressDialog(this);

        final ImageView imageView = (ImageView) findViewById(R.id.saloonimage_showcase_imageView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "profile_image");

        if (saloon.getSaloonImageList() == null) {
            HashMap<String, String> imageList = new HashMap<String, String>();

            saloon.setSaloonImageList(imageList);

        }else {

            if (saloon.getSaloonImageList().containsKey("profile_image")) {
                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .thumbnail(0.5f)
                        .override(900, 400)
                        .crossFade(100)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView);
            }
            ImageView imageView1 ;
            if (saloon.getSaloonImageList().containsKey("image_1")) {

                imageView1 = (ImageView) findViewById(R.id.saloonimage_image1_imageView);
                storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_1");


                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .thumbnail(0.5f)
                        .override(900, 400)
                        .crossFade(100)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView1);
            }


            if (saloon.getSaloonImageList().containsKey("image_2")) {
                imageView1 = (ImageView) findViewById(R.id.saloonimage_image2_imageView);
                storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_2");


                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .thumbnail(0.5f)
                        .override(900, 400)
                        .crossFade(100)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView1);
            }

            if (saloon.getSaloonImageList().containsKey("image_3")) {

                imageView1 = (ImageView) findViewById(R.id.saloonimage_image3_imageView);
                storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_3");


                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .thumbnail(0.5f)
                        .override(900, 400)
                        .crossFade(100)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView1);

            }

            if (saloon.getSaloonImageList().containsKey("image_4")) {

                imageView1 = (ImageView) findViewById(R.id.saloonimage_image4_imageView);
                storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_4");


                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .thumbnail(0.5f)
                        .override(900, 400)
                        .crossFade(100)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView1);

            }

            if (saloon.getSaloonImageList().containsKey("image_5")) {

                imageView1 = (ImageView) findViewById(R.id.saloonimage_image5_imageView);
                storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_5");


                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .thumbnail(0.5f)
                        .override(900, 400)
                        .crossFade(100)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView1);

            }


        }

        methodRequiresTwoPermission();


    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
             if (requestCode == REQUEST_PROFILE_IMAGE) {

                List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                profileImageUri = mSelectedProfileImage.get(0);
                Log.d("Matisse", "mSelected: " + profileImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.saloonimage_showcase_imageView);
                imageView.setImageURI(profileImageUri);


            }else if (requestCode == REQUEST_EXTRA_IMAGE1) {
                 List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                 image1Uri = mSelectedProfileImage.get(0);
                 Log.d("Matisse", "mSelected: " + profileImageUri);
                 ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image1_imageView);
                 imageView.setImageURI(image1Uri);


            } else if(requestCode == REQUEST_EXTRA_IMAGE2){
                 List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                 image2Uri = mSelectedProfileImage.get(0);
                 Log.d("Matisse", "mSelected: " + profileImageUri);
                 ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image2_imageView);
                 imageView.setImageURI(image2Uri);

             }
            else if (requestCode == REQUEST_EXTRA_IMAGE3){
                 List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                 image3Uri = mSelectedProfileImage.get(0);
                 Log.d("Matisse", "mSelected: " + profileImageUri);
                 ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image3_imageView);
                 imageView.setImageURI(image3Uri);

             }
             else if (requestCode == REQUEST_EXTRA_IMAGE4){
                 List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                 image4Uri = mSelectedProfileImage.get(0);
                 Log.d("Matisse", "mSelected: " + profileImageUri);
                 ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image4_imageView);
                 imageView.setImageURI(image4Uri);

             }
             else if (requestCode == REQUEST_EXTRA_IMAGE5){
                 List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                 image5Uri = mSelectedProfileImage.get(0);
                 Log.d("Matisse", "mSelected: " + profileImageUri);
                 ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image5_imageView);
                 imageView.setImageURI(image5Uri);

             }

        }
    }



    public void selectImageFromGallery(View view) {



        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(3)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE1);

    }

    public void uploadImageToFireBase() {

        if(saloon.getSaloonPoint() == 0){
            Toast.makeText(this, "Not a registered saloon", Toast.LENGTH_SHORT).show();

            return;
        }









        final FireBaseHandler fireBaseHandler = new FireBaseHandler();


        if (profileImageUri != null ||image1Uri!=null || image2Uri!= null ||image3Uri!=null || image4Uri!=null ||image5Uri!=null){
            progressDialog.show();
        }else{
            Toast.makeText(this, "Images not selected", Toast.LENGTH_SHORT).show();
        }

        if (profileImageUri != null) {


            statusProfileUpload =1;

            fireBaseHandler.uploadSaloonShwcaseImage(profileImageUri, saloonUID, "profile_image", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, "Profile image uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("profile_image", downloadUri.toString());
                    statusProfileUpload=2 ;
                    checkForProgress(fireBaseHandler);

                }
            });

        }
        if (image1Uri != null) {


            statusImage1Upload =1;
            fireBaseHandler.uploadSaloonShwcaseImage(image1Uri, saloonUID, "image_1", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, "image 1 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_1", downloadUri.toString());
                    Log.d("path" , downloadUri.toString());
                    statusImage1Upload =2;
                    checkForProgress(fireBaseHandler);

                }
            });
        }
        if (image2Uri != null) {

            statusImage2Upload = 1;
            fireBaseHandler.uploadSaloonShwcaseImage(image2Uri, saloonUID, "image_2", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, " image 2 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_2", downloadUri.toString());
                    statusImage2Upload=2;
                    checkForProgress(fireBaseHandler);
                }
            });

        }
        if (image3Uri != null) {

            statusImage3Upload =1;
            fireBaseHandler.uploadSaloonShwcaseImage(image3Uri, saloonUID, "image_3", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, " image 3 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_3", downloadUri.toString());
                    statusImage3Upload =2;
                    checkForProgress(fireBaseHandler);
                }
            });

        }

        if (image4Uri != null) {

            statusImage4Upload =1;
            fireBaseHandler.uploadSaloonShwcaseImage(image4Uri, saloonUID, "image_4", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, " image 4 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_4", downloadUri.toString());
                    statusImage4Upload =2;
                    checkForProgress(fireBaseHandler);
                }
            });

        }

        if (image5Uri != null) {

            statusImage5Upload =1;
            fireBaseHandler.uploadSaloonShwcaseImage(image5Uri, saloonUID, "image_5", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, " image 5 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_5", downloadUri.toString());
                    statusImage5Upload =2;
                    checkForProgress(fireBaseHandler);
                }
            });

        }
      
        



    }

    private void checksaloonPoints() {


        if (saloon.getSaloonPoint() < -1 && saloon.getSaloonPoint()> -100){
            intent = new Intent(SaloonImageActivity.this , MainActivity.class);
            int i=-100;

            if(!saloon.checkSaloonImageUpdated()){
                i=i+10;

            }
            if(!saloon.isSaloonServiceUpdated()){
                i=i+10;
                intent = new Intent(SaloonImageActivity.this , ServiceTypeActivity.class);
            }
            if (!saloon.isSaloonUpdated()){
                i=i+10;
                intent = new Intent(SaloonImageActivity.this , SaloonProfile.class);

            }

            if (saloon.getSaloonPhoneNumber() == null ){
                i=i+10;
                intent = new Intent(SaloonImageActivity.this , PhoneNumerActivity.class);
            }else{
                if (saloon.getSaloonPhoneNumber().isEmpty()){
                    i=i+10;
                    intent = new Intent(SaloonImageActivity.this , PhoneNumerActivity.class);
                }
            }


            if(i==-100){
                //show pending approval screen and initialize intent with pending approval screen
                saloon.setSaloonPoint(i);
                if (MainActivity.SALOON.getSaloonPoint() ==-100){
                    PendingSaloonRequest pendingSaloonRequest =new PendingSaloonRequest(MainActivity.SALOON.getSaloonName() , MainActivity.SALOON.getSaloonUID() , MainActivity.SALOON.getSaloonAddress() ,true);
                    new FireBaseHandler().uploadPendingSaloonRequest(pendingSaloonRequest, new FireBaseHandler.OnPendingSaloonRequest() {
                        @Override
                        public void onSaloonRequest(boolean isSuccessful) {
                            if (isSuccessful){

                            }
                        }
                    });
                }
            }else if(i<0){
                saloon.setSaloonPoint(i);
            }

        }

        if (saloon.getSaloonPoint()==-1000){
            //blocked by admin
            Toast.makeText(this, "Blocked by admin", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void checkForProgress(FireBaseHandler fireBaseHandler) {
        if(!(statusProfileUpload == 1 || statusImage1Upload ==1 || statusImage2Upload==1 ||statusImage3Upload==1 ||statusImage4Upload==1||statusImage5Upload==1)){
            Toast.makeText(this, "Updating saloon details", Toast.LENGTH_SHORT).show();
            checksaloonPoints();

            fireBaseHandler.uploadSaloon(saloonUID, MainActivity.SALOON, new FireBaseHandler.OnSaloonDownload() {
                @Override
                public void onSaloon(Saloon saloon) {

                }

                @Override
                public void onSaloonValueUploaded(boolean isSucessful) {

                    Toast.makeText(SaloonImageActivity.this, "Uploaded images ", Toast.LENGTH_SHORT).show();



                    closeProgressDialog();
                    showExitDialogue();
                }
            });
        }
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {


    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {


    }

    public void selectProfileImage(View view) {
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_PROFILE_IMAGE);
    }


    public void selectImage1PhotoClick(View view) {

        // in onCreate or any event where your want the user to
        // select a file
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE1);

    }

    public void addImage2PhotoClick(View view) {
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE2);
    }

    public void addImage3PhotoClick(View view) {
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE3);
    }

    public void addImage4PhotoClick(View view) {
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE4);

    }

    public void addImage5PhotoClick(View view) {
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE5);

    }

    public void showProgressDialog(String title,String message){
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeProgressDialog(){
        progressDialog.dismiss();
    }


    private void showExitDialogue() {
        if(intent==null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle("Image Uploaded successfully ")
                    .setMessage("Press yes to exit")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }else{
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }


    public void hireUsForImageClick(){

        FireBaseHandler fireBaseHandler =new FireBaseHandler();
        fireBaseHandler.uploadSaloonInfo(MainActivity.SALOON.getSaloonUID(), "saloonHirePhotographer", true, new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

                if (isSucessful){

                    intent = new Intent(SaloonImageActivity.this ,ServiceTypeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_hire_photographer) {

            //replace it with hire photographer option
            showHireUsInfoDialog();
            return true;
        }
        if (id == R.id.action_post_image) {
            //upload images
            uploadImageToFireBase();
        }

        return super.onOptionsItemSelected(item);
    }


    public void showHireUsInfoDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hire us For Photograph")
                .setMessage("Hire us for top Quality photograph for 500rs")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       hireUsForImageClick();
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit
            }
        });

        builder.create();
        builder.show();
    }
}
